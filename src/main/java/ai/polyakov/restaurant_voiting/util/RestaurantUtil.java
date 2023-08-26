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
        return createRestaurantToWithDishesTo(restaurant, Collections.emptyList());
    }

    public static RestaurantTo createRestaurantToWithDishesTo(Restaurant restaurant, List<DishTo> dishTos) {
            return  new RestaurantTo(restaurant.id(), restaurant.getName(), dishTos);
    }

    public static Restaurant createRestaurantFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), dishListFromTo(restaurantTo.getDishes()), Collections.emptyList());
    }

    public static DishTo createDishTo(Dish dish) {
        return new DishTo(dish.getName(), (dish.getPrice() / 100.0), dish.getDateDish());
    }

    public static Dish dishFromTo(DishTo dishTo) {
        return new Dish(dishTo.getName(), (long)(dishTo.getPrice() * 100), dishTo.getDateDish());
    }

    public static List<Dish> dishListFromTo(List<DishTo> dishTos) {
        return dishTos.stream().map(RestaurantUtil::dishFromTo).toList();
    }
}
