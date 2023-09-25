package ai.polyakov.restaurantvoiting.util;

import ai.polyakov.restaurantvoiting.model.Dish;
import ai.polyakov.restaurantvoiting.model.Restaurant;
import ai.polyakov.restaurantvoiting.to.DishTo;
import ai.polyakov.restaurantvoiting.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RestaurantUtil {

    public static RestaurantTo createRestaurantTo(Restaurant restaurant) {
            return  new RestaurantTo(restaurant.id(), restaurant.getName());
    }

    /*public static Restaurant createRestaurantFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), dishListFromTo(restaurantTo.getDishes()), Collections.emptyList());
    }*/

    public static DishTo createDishTo(Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice(), dish.getDateDish());
    }
    public static List<DishTo> dishTos(List<Dish> dishes) {
        return dishes.stream().map(RestaurantUtil::createDishTo).toList();
    }

    public static Dish dishFromTo(DishTo dishTo) {
        return new Dish(dishTo.getName(), (long)(dishTo.getPrice() * 100), dishTo.getDateDish());
    }

    public static List<Dish> dishListFromTo(List<DishTo> dishTos) {
        return dishTos.stream().map(RestaurantUtil::dishFromTo).toList();
    }
}
