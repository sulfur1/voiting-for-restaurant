package ai.polyakov.restaurant_voiting.web.restaurant;

import ai.polyakov.restaurant_voiting.model.Dish;
import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.to.DishTo;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import ai.polyakov.restaurant_voiting.util.RestaurantUtil;
import ai.polyakov.restaurant_voiting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int RESTAURANT_1 = 1;
    public static final int RESTAURANT_2 = RESTAURANT_1 + 1;
    public static final int NEW_RESTAURANT = RESTAURANT_1 + 2;

    public static final int NOT_FOUND = RESTAURANT_1 + 10;
    private static final Dish dish_1 = new Dish(1, "Risotto", 56812L, LocalDate.now());
    private static final Dish dish_2 = new Dish(2, "Appetizers", 50000L, LocalDate.now());
    private static final Dish dish_3 = new Dish(3, "Cold platter", 32000L, LocalDate.now());
    private static final Dish dish_4 = new Dish(4, "Beverages", 20000L, LocalDate.now());
    private static final Dish dish_5 = new Dish(5, "Soups", 56000L, LocalDate.now());
    private static final Dish dish_6 = new Dish(6, "Meat", 50000L, LocalDate.now());
    private static final Dish dish_7 = new Dish(7, "Pizza", 80000L, LocalDate.now());
    private static final Dish dish_8 = new Dish(8, "Beverages", 20000L, LocalDate.now());

    private static final Dish dish_9 = new Dish(null, "Hamburger", 35000L, LocalDate.now());
    private static final Dish dish_10 = new Dish(null, "Deep-fried potatoes", 22000L, LocalDate.now());
    private static final Dish dish_11 = new Dish(null, "Coca-Cola", 15000L, LocalDate.now());

    private static final Dish update_dish_1 = new Dish(null, "Borsch", 32000L, LocalDate.now().plusDays(1));
    private static final Dish update_dish_2 = new Dish(null, "Beef Stroganoff", 40000L, LocalDate.now().plusDays(1));
    private static final Dish update_dish_3 = new Dish(null, "Black Tea with lemon", 13000L, LocalDate.now().plusDays(1));

    public static Restaurant restaurant_1 = new Restaurant(RESTAURANT_1, "Koza", List.of(dish_1, dish_2, dish_3, dish_4), null);
    public static Restaurant restaurant_2 = new Restaurant(RESTAURANT_2, "PizzaHut", List.of(dish_5, dish_6, dish_7, dish_8), null);

    public static Restaurant getNew() {
        return new Restaurant(null, "Bistro", List.of(dish_9, dish_10, dish_11), null);
    }
    public static Restaurant getUpdated() {
        List<Dish> dishes = new ArrayList<>() {{
            add(update_dish_1);
            add(update_dish_2);
            add(update_dish_3);
        }};

        return new Restaurant(RESTAURANT_1, "Koza", dishes, null);
    }

    public static Restaurant getInvalidUpdated() {
        List<Dish> dishes = new ArrayList<>() {{
            add(new Dish(null, "Borsch", 9000L, LocalDate.now().plusDays(1)));
            add(new Dish(null, "Beef Stroganoff", 40000L, LocalDate.now().plusDays(1)));
            add(new Dish(null, "", 13000L, LocalDate.now().plusDays(1)));
        }};

        return new Restaurant(RESTAURANT_1, "Koza", dishes, null);
    }
    public static RestaurantTo getRestaurantTo_1() {
        return new RestaurantTo(restaurant_1.id(), restaurant_1.getName(), dishTos(restaurant_1.getDishes()), 0);
    }
    public static List<RestaurantTo> getAll() {
        return Stream.of(restaurant_1, restaurant_2).map(restaurant -> RestaurantUtil.createRestaurantToWithDishesTo(restaurant, dishTos(restaurant.getDishes()), 0)).toList();
    }


    public static DishTo dishTo(Dish dish) {
        return new DishTo(dish.getName(), (dish.getPrice() / 100.0), dish.getDateDish());
    }

    public static List<DishTo> dishTos(Dish... dishes) {
        return dishTos(List.of(dishes));
    }
    public static List<DishTo> dishTos(List<Dish> dishes) {
        return dishes.stream().map(RestaurantTestData::dishTo).toList();
    }
}
