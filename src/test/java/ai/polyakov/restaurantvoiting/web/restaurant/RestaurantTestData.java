package ai.polyakov.restaurantvoiting.web.restaurant;

import ai.polyakov.restaurantvoiting.model.Restaurant;
import ai.polyakov.restaurantvoiting.to.RestaurantTo;
import ai.polyakov.restaurantvoiting.util.RestaurantUtil;
import ai.polyakov.restaurantvoiting.web.MatcherFactory;

import java.util.List;
import java.util.stream.Stream;


public class RestaurantTestData {
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int RESTAURANT_1 = 1;
    public static final int RESTAURANT_2 = RESTAURANT_1 + 1;
    public static final int RESTAURANT_NOT_FOUND = RESTAURANT_1 + 10;

    public static Restaurant restaurant_1 = new Restaurant(RESTAURANT_1, "Koza");
    public static Restaurant restaurant_2 = new Restaurant(RESTAURANT_2, "PizzaHut");


    public static Restaurant getNew() {
        return new Restaurant(null, "Bistro");
    }

    public static RestaurantTo getRestaurantTo_1() {
        return new RestaurantTo(restaurant_1.id(), restaurant_1.getName());
    }
    public static List<RestaurantTo> getAll() {
        return Stream.of(restaurant_1, restaurant_2).map(RestaurantUtil::createRestaurantTo).toList();
    }

}
