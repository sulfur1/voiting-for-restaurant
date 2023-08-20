package ai.polyakov.restaurant_voiting.util;

import ai.polyakov.restaurant_voiting.model.Dish;
import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.to.DishTo;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class RestaurantUtil {

    public static LocalTime CONTROL_TIME = LocalTime.of(11, 00, 00);
    public static RestaurantTo createRestaurantTo(Restaurant restaurant) {
        return createRestaurantWithDishesTo(restaurant, Collections.emptyList());
    }

    public static RestaurantTo createRestaurantWithDishesTo(Restaurant restaurant, List<DishTo> dishTos) {
            return  new RestaurantTo(restaurant.id(), restaurant.getName(), dishTos);
    }

    public static DishTo createDishTo(Dish dish) {
        return new DishTo(dish.getName(), dish.getPrice(), dish.getDateDish());
    }
}
