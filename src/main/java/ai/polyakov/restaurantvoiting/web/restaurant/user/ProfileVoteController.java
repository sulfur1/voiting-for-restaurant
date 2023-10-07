package ai.polyakov.restaurantvoiting.web.restaurant.user;

import ai.polyakov.restaurantvoiting.error.AccessVoteException;
import ai.polyakov.restaurantvoiting.error.NotFoundException;
import ai.polyakov.restaurantvoiting.model.Restaurant;
import ai.polyakov.restaurantvoiting.model.User;
import ai.polyakov.restaurantvoiting.model.Vote;
import ai.polyakov.restaurantvoiting.repository.RestaurantRepository;
import ai.polyakov.restaurantvoiting.repository.UserRepository;
import ai.polyakov.restaurantvoiting.repository.VoteRepository;
import ai.polyakov.restaurantvoiting.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping(value = ProfileVoteController.PROFILE_REST_VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {
    public static final String PROFILE_REST_VOTE_URL = "/api/profile/vote";

    public static final LocalTime CONTROL_TIME = LocalTime.of(11, 0, 0);

    private static final Logger log = LoggerFactory.getLogger(ProfileVoteController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping
    public Vote getVoteToday(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote today if exist");
        LocalDate dateNow = LocalDate.now();
        return getVote(authUser.id(), dateNow).orElseThrow(() -> new NotFoundException("Vote doesnt find today"));
    }

    @Operation(summary = "Create vote", description = "The path variable specify the restaurant id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created vote")
    })
    @PostMapping("/{rest_id}")
    @Transactional
    public ResponseEntity<Vote> create(@PathVariable(value = "rest_id") int restId,
                                       @AuthenticationPrincipal AuthUser authUser) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        User userRef = userRepository.getReferenceById(authUser.id());
        Vote vote = getVote(authUser.id(), dateTimeNow.toLocalDate()).orElse(null);
        if (vote == null) {
            log.info("create vote");
            Restaurant restaurantRef = restaurantRepository.getReferenceById(restId);
            Vote created = new Vote(dateTimeNow);
            created.setRestaurant(restaurantRef);
            created.setUser(userRef);

            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(PROFILE_REST_VOTE_URL + "/{rest_id}")
                    .buildAndExpand(restId).toUri();

            return ResponseEntity.created(uri).body(voteRepository.save(created));
        } else {
            throw new AccessVoteException("Vote also exist");
        }
    }

    @Operation(summary = "Update vote", description = "The path variable specify the restaurant id")
    @PutMapping("/{rest_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable(value = "rest_id") int restId,
                       @AuthenticationPrincipal AuthUser authUser) {
        Vote vote = getVoteToday(authUser);
        LocalDateTime dateTimeNow = LocalDateTime.now();

        if (dateTimeNow.toLocalTime().isBefore(CONTROL_TIME)) {
            log.info("update vote");
            Restaurant restaurantRef = restaurantRepository.getReferenceById(restId);
            vote.setRestaurant(restaurantRef);
            vote.setDateTime(dateTimeNow);
            voteRepository.save(vote);
        } else {
            throw new AccessVoteException("Access forbidden. The time for re-voting has been exceeded");
        }

    }

    private Optional<Vote> getVote(int userId, LocalDate now) {
        LocalDateTime start = now.atStartOfDay();
        LocalDateTime end = LocalTime.MAX.atDate(now);

        return voteRepository.getVoteUserByDateTime(userId, start, end);
    }


}
