package ai.polyakov.restaurantvoiting.web.restaurant;

import ai.polyakov.restaurantvoiting.model.Dish;
import ai.polyakov.restaurantvoiting.model.Restaurant;
import ai.polyakov.restaurantvoiting.to.DishTo;
import ai.polyakov.restaurantvoiting.to.RestaurantTo;
import ai.polyakov.restaurantvoiting.util.RestaurantUtil;
import ai.polyakov.restaurantvoiting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class RestaurantTestData {
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int RESTAURANT_1 = 1;
    public static final int RESTAURANT_2 = RESTAURANT_1 + 1;

    public static final int NOT_FOUND = RESTAURANT_1 + 10;
    private static final Dish update_dish_1 = new Dish(null, "Borsch", 32000L, LocalDate.now().plusDays(1));
    private static final Dish update_dish_2 = new Dish(null, "Beef Stroganoff", 40000L, LocalDate.now().plusDays(1));
    private static final Dish update_dish_3 = new Dish(null, "Black Tea with lemon", 13000L, LocalDate.now().plusDays(1));

    public static Restaurant restaurant_1 = new Restaurant(RESTAURANT_1, "Koza");
    public static Restaurant restaurant_2 = new Restaurant(RESTAURANT_2, "PizzaHut");


    public static Restaurant getNew() {
        return new Restaurant(null, "Bistro");
    }
    public static Restaurant getUpdated() {
        List<Dish> dishes = new ArrayList<>() {{
            add(update_dish_1);
            add(update_dish_2);
            add(update_dish_3);
        }};
        return new Restaurant(RESTAURANT_1, "Koza");
    }

    public static Restaurant getInvalidUpdated() {
        List<Dish> dishes = new ArrayList<>() {{
            add(new Dish(null, "Borsch", 9000L, LocalDate.now().plusDays(1)));
            add(new Dish(null, "Beef Stroganoff", 40000L, LocalDate.now().plusDays(1)));
            add(new Dish(null, "", 13000L, LocalDate.now().plusDays(1)));
        }};

        return new Restaurant(RESTAURANT_1, "Koza");
    }
    public static RestaurantTo getRestaurantTo_1() {
        return new RestaurantTo(restaurant_1.id(), restaurant_1.getName());
    }
    public static List<RestaurantTo> getAll() {
        return Stream.of(restaurant_1, restaurant_2).map(RestaurantUtil::createRestaurantTo).toList();
    }

}
