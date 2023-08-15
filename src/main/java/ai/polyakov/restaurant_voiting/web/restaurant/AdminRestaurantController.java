package ai.polyakov.restaurant_voiting.web.restaurant;

import ai.polyakov.restaurant_voiting.error.NotFoundException;
import ai.polyakov.restaurant_voiting.model.Dish;
import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.repository.DishRepository;
import ai.polyakov.restaurant_voiting.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ai.polyakov.restaurant_voiting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantController {
    static final String REST_URL = "/api/admin/restaurants";
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private DishRepository dishRepository;

    @GetMapping
    public List<Restaurant> getAllRestaurantWithDishes() {
        List<Restaurant> allRestaurants = restaurantRepository.getAllWithDishes().orElse(Collections.emptyList());
        return allRestaurants.isEmpty() ?
                allRestaurants :
                allRestaurants.stream()
                        .filter(restaurant -> restaurant.getDishes().stream().anyMatch(dish -> dish.getDateDish().isEqual(LocalDate.now())))
                        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));
    }

    @PutMapping(value = "/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDishes(@RequestBody List<Dish> dishes, @PathVariable int id) {
        Restaurant restaurant = restaurantRepository.getReferenceById(id);
        dishRepository.update(restaurant, dishes);
    }

    @GetMapping("/{id}/dishes")
    public List<Dish> getRestaurantWithDishes(@PathVariable int id) {
        Restaurant restaurant = restaurantRepository.getRestaurantByIdWithDishes(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));

        return restaurant.getDishes();
    }
}
