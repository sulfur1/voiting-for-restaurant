package ai.polyakov.restaurant_voiting.repository;

import ai.polyakov.restaurant_voiting.model.Dish;
import ai.polyakov.restaurant_voiting.model.Restaurant;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DishRepository extends BaseRepository<Dish> {

    @Transactional
    default List<Dish> createOrUpdate(Restaurant restaurant, List<Dish> dishes) {
        dishes.forEach(dish -> {
            dish.setRestaurant(restaurant);

        });
        return saveAll(dishes);
    }
}
