package ai.polyakov.restaurantvoiting.web.restaurant.admin;

import ai.polyakov.restaurantvoiting.error.NotFoundException;
import ai.polyakov.restaurantvoiting.model.Dish;
import ai.polyakov.restaurantvoiting.model.Restaurant;
import ai.polyakov.restaurantvoiting.repository.DishRepository;
import ai.polyakov.restaurantvoiting.repository.RestaurantRepository;
import ai.polyakov.restaurantvoiting.to.DishTo;
import ai.polyakov.restaurantvoiting.util.RestaurantUtil;
import ai.polyakov.restaurantvoiting.util.validation.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminDishController.REST_ADMIN_DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    public static final String REST_ADMIN_DISH_URL = "/api/admin/restaurants/{rest_id}/dishes";

    private static final Logger log = LoggerFactory.getLogger(AdminDishController.class);

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private DishRepository dishRepository;

    @Operation(summary = "Get dishes by all time")
    @GetMapping
    public List<DishTo> getAll(@PathVariable(name = "rest_id") int restId) {
        log.info("Get dishes by all time");
        return RestaurantUtil.dishTos(dishRepository.findAllByRestaurant_Id(restId));
    }

    @Operation(summary = "Create a new dish", description = "Returns a product created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create successfully")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Dish> create(@PathVariable(name = "rest_id") int restId, @RequestBody @Valid Dish dish) {
        ValidationUtil.checkNew(dish);
        log.info("create {}", dish);
        Restaurant restaurantRef = restaurantRepository.getReferenceById(restId);
        dish.setRestaurant(restaurantRef);
        Dish created = dishRepository.save(dish);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_ADMIN_DISH_URL + "/{id}")
                .buildAndExpand(restId, dish.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @Operation(summary = "Update dish", description = "Specify parameters - dish id")
    @PutMapping(value = "/{dish_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable(name = "rest_id") int restId, @PathVariable(name = "dish_id") int dishId, @RequestBody @Valid Dish dish) {
        Restaurant restaurant = restaurantRepository.findById(restId).orElseThrow(() -> new NotFoundException("Restaurant not found"));
        ValidationUtil.assureIdConsistent(dish, dishId);
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
    }

    @Operation(summary = "Delete dish", description = "Specify parameters - dish id")
    @DeleteMapping(value = "/{dish_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable(name = "rest_id") int restId, @PathVariable(name = "dish_id") int dishId) {
        restaurantRepository.findById(restId).orElseThrow(() -> new NotFoundException("Restaurant not found"));
        dishRepository.deleteExisted(dishId);
    }
}
