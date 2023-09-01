package ai.polyakov.restaurant_voiting.web.restaurant;

import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.model.User;
import ai.polyakov.restaurant_voiting.model.Vote;
import ai.polyakov.restaurant_voiting.repository.UserRepository;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import ai.polyakov.restaurant_voiting.util.RestaurantUtil;
import ai.polyakov.restaurant_voiting.util.validation.ValidationUtil;
import ai.polyakov.restaurant_voiting.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = ProfileRestaurantController.PROFILE_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController extends AbstractRestaurantController {
    static final String PROFILE_REST_URL = "/api/profile/restaurants";

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Get all restaurants with dishes", description = "The parameters specify the restaurant id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    @GetMapping
    public List<RestaurantTo> getAllRestaurantWithDishes() {
        return super.getAllRestaurantWithDishes();
    }

    @Operation(summary = "Get restaurant with dishes by id", description = "The parameters specify the restaurant id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not found by id", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}")
    public RestaurantTo getRestaurantWithDishes(@PathVariable int id) {return super.getRestaurantWithDishes(id);}

    @Operation(summary = "Vote by restaurant", description = "The parameters specify the restaurant id and the datetime(ISO)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created vote"),
            @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/vote")
    @Transactional
    public ResponseEntity<Vote> vote(@RequestParam int id,
                                     @RequestParam(name = "datetime") LocalDateTime dateTime,
                                     @AuthenticationPrincipal AuthUser authUser) throws AccessDeniedException {
        Restaurant restaurant = restaurantRepository.getExisted(id);
        ValidationUtil.assureIdConsistent(restaurant, id);

        User user = userRepository.getReferenceById(authUser.id());
        URI uri;

        Vote vote = voteRepository.getVoteUserByDateTime(authUser.id(), dateTime.toLocalDate()).orElse(null);
        if (vote == null) {
            uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(PROFILE_REST_URL + "/{id}")
                    .buildAndExpand(authUser.id()).toUri();

            Vote created = new Vote();
            created.setDate(dateTime.toLocalDate());

            return ResponseEntity.created(uri).body(voteRepository.create(created, restaurant, user));
        } else if (dateTime.toLocalTime().isBefore(RestaurantUtil.CONTROL_TIME)){
            voteRepository.update(restaurant, vote);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            throw new AccessDeniedException("Your vote can't to be accepted");
        }

    }
}
