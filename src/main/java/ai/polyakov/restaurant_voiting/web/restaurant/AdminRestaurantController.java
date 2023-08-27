package ai.polyakov.restaurant_voiting.web.restaurant;

import ai.polyakov.restaurant_voiting.model.Dish;
import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.repository.DishRepository;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import ai.polyakov.restaurant_voiting.util.validation.ValidationUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(value = AdminRestaurantController.ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantController extends AbstractRestaurantController {
    static final String ADMIN_REST_URL = "/api/admin/restaurants";
    @Autowired
    private DishRepository dishRepository;

    @GetMapping
    public List<RestaurantTo> getAllRestaurantWithDishes() {
        return super.getAllRestaurantWithDishes();
    }


    @GetMapping("/{id}")
    public RestaurantTo getRestaurantWithDishes(@PathVariable int id) {return super.getRestaurantWithDishes(id);}

    @PostMapping
    @Transactional
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        log.info("create {}", restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        List<Dish> createdDishes = dishRepository.createOrUpdate(created, restaurant.getDishes());
        created.setDishes(createdDishes);
        created.setVotes(Collections.emptyList());
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_REST_URL + "/{id}")
                .buildAndExpand(restaurant.id()).toUri();

        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDishes(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        Restaurant restaurantRef = restaurantRepository.getReferenceById(id);
        List<Dish> dishes = restaurant.getDishes();
        dishRepository.createOrUpdate(restaurantRef, dishes);
    }

}
