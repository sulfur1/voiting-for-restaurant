package ai.polyakov.restaurantvoiting.web.restaurant.user;

import ai.polyakov.restaurantvoiting.to.RestaurantTo;
import ai.polyakov.restaurantvoiting.web.restaurant.AbstractRestaurantController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ProfileRestaurantController.PROFILE_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController extends AbstractRestaurantController {
    public static final String PROFILE_REST_URL = "/api/profile/restaurants";

    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok")
    })
    @Cacheable("restaurants")
    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll restaurants");
        return super.getAllRestaurant();
    }

    @Operation(summary = "Get restaurant by id", description = "The parameters specify the restaurant id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not found by id", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id) {
        log.info("get restaurant by id: {}", id);
        return super.getRestaurantById(id);
    }

}
