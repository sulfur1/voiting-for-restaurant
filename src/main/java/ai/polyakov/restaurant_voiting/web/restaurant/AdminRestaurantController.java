package ai.polyakov.restaurant_voiting.web.restaurant;

import ai.polyakov.restaurant_voiting.error.NotFoundException;
import ai.polyakov.restaurant_voiting.model.Dish;
import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.repository.DishRepository;
import ai.polyakov.restaurant_voiting.to.DishTo;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import ai.polyakov.restaurant_voiting.util.RestaurantUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/admin/restaurants";
    @Autowired
    private DishRepository dishRepository;

    @GetMapping
    public List<RestaurantTo> getAllRestaurantWithDishes() {
        return super.getAllRestaurantWithDishes();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));
    }

    @PutMapping(value = "/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDishes(@RequestBody @Valid List<DishTo> dishesTo, @PathVariable int id, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + "-" + fieldError.getDefaultMessage())
                    .collect(Collectors.joining("\n"));
            return ResponseEntity.badRequest().body(errors);
        }

        Restaurant restaurant = restaurantRepository.getReferenceById(id);
        List<Dish> dishes = dishesTo.stream().map(RestaurantUtil::dishFromTo).toList();
        dishRepository.update(restaurant, dishes);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/dishes")
    public List<Dish> getRestaurantWithDishes(@PathVariable int id) {
        Restaurant restaurant = restaurantRepository.getRestaurantByIdWithDishes(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));

        return restaurant.getDishes();
    }
}
