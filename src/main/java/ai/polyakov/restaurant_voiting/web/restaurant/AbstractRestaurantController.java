package ai.polyakov.restaurant_voiting.web.restaurant;

import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.repository.DishRepository;
import ai.polyakov.restaurant_voiting.repository.RestaurantRepository;
import ai.polyakov.restaurant_voiting.to.DishTo;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import ai.polyakov.restaurant_voiting.util.RestaurantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static org.slf4j.LoggerFactory.*;

public abstract class AbstractRestaurantController {
    protected final Logger log = getLogger(this.getClass());

    @Autowired
    protected RestaurantRepository restaurantRepository;


    protected List<RestaurantTo> getAllRestaurantWithDishes() {
        List<Restaurant> allRestaurants = restaurantRepository.getAllWithDishes().orElse(Collections.emptyList());
        List<RestaurantTo> restaurantTos = new ArrayList<>();

        ListIterator<Restaurant> restIterator = allRestaurants.listIterator();
        while (restIterator.hasNext()) {
            Restaurant restaurant = restIterator.next();
            List<DishTo> dishesTo = restaurant.getDishes().stream()
                    .filter(dish -> dish.getDateDish().isEqual(LocalDate.now()))
                    .map(RestaurantUtil::createDishTo)
                    .toList();

            RestaurantTo restaurantTo = RestaurantUtil.createRestaurantWithDishesTo(restaurant, dishesTo);
            restaurantTos.add(restaurantTo);
        }

        return restaurantTos;
    }
}
