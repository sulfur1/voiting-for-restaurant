package ai.polyakov.restaurantvoiting.web.restaurant.user;

import ai.polyakov.restaurantvoiting.error.NotFoundException;
import ai.polyakov.restaurantvoiting.repository.DishRepository;
import ai.polyakov.restaurantvoiting.to.DishTo;
import ai.polyakov.restaurantvoiting.util.RestaurantUtil;
import ai.polyakov.restaurantvoiting.web.restaurant.admin.AdminDishController;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileDishController.REST_PROFILE_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileDishController {

    public static final String REST_PROFILE_DISH_URL = "/api/profile/restaurants/{rest_id}/dishes";

    private static final Logger log = LoggerFactory.getLogger(AdminDishController.class);

    @Autowired
    private DishRepository dishRepository;

    @Operation(summary = "Get all dishes today")
    @GetMapping
    public List<DishTo> getAllToday(@PathVariable(name = "rest_id") int restId) {
        log.info("Get dishes today");
        return RestaurantUtil.dishTos(dishRepository.findAllTodayByRestaurantId(restId, LocalDate.now()));
    }
    @Operation(summary = "Get dish by id", description = "Specify parameters by dish id")
    @GetMapping("/{dish_id}")
    public DishTo getDishById(@PathVariable(value = "rest_id") int restId, @PathVariable(value = "dish_id") int dishId) {
        log.info("Get dish by restaurant id: {} and dish id: {}", restId, dishId);
        return RestaurantUtil.createDishTo(dishRepository.findByIdAndRestaurantId(dishId, restId).orElseThrow(() -> new NotFoundException("Dish with id " + dishId + " not found!")));
    }
}
