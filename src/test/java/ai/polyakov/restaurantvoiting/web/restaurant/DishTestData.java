package ai.polyakov.restaurantvoiting.web.restaurant;

import ai.polyakov.restaurantvoiting.model.Dish;
import ai.polyakov.restaurantvoiting.to.DishTo;
import ai.polyakov.restaurantvoiting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);
    public static final int DISH_1_RESTAURANT_1 = 1;
    public static final int DISH_2_RESTAURANT_2 = 8;
    public static final int DISH_NOT_FOUND_RESTAURANT_1 = 8;
    public static final int DISH_NOT_FOUND_RESTAURANT_2 = 1;
    //Dishes Restaurant 1
    public static final Dish dish_1 = new Dish(1, "Risotto", 56812L, LocalDate.now());
    public static final Dish dish_2 = new Dish(2, "Appetizers", 50000L, LocalDate.now());
    public static final Dish dish_3 = new Dish(3, "Cold platter", 32000L, LocalDate.now());
    public static final Dish dish_4 = new Dish(4, "Beverages", 20000L, LocalDate.now());
    public static final Dish dish_5 = new Dish(5, "Hamburger", 15100L, LocalDate.of(2023, 9, 28));
    public static final Dish dish_6 = new Dish(6, "Deep-fried potatoes", 13200L, LocalDate.of(2023, 9, 28));
    public static final Dish dish_7 = new Dish(7, "Coca-Cola", 5000L, LocalDate.of(2023, 9, 27));
    // Dishes restaurant 2
    public static final Dish dish_8 = new Dish(8, "Soups", 56000L, LocalDate.now());
    public static final Dish dish_9 = new Dish(9, "Meat", 50000L, LocalDate.now());
    public static final Dish dish_10 = new Dish(10, "Pizza", 80000L, LocalDate.now());
    public static final Dish dish_11 = new Dish(11, "Beverages", 20000L, LocalDate.now());
    public static final Dish dish_12 = new Dish(12, "Pel`meni", 10100L, LocalDate.of(2023, 7, 26));
    public static Dish getNew() {
        return new Dish("Fry", 1000L, LocalDate.now());
    }
    public static DishTo dishTo(Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice(), dish.getDateDish());
    }
    public static List<DishTo> dishTos(List<Dish> dishes) {
        return dishes.stream().map(DishTestData::dishTo).toList();
    }

    public static List<Dish> dishesAllByRestaurant1() {
        return List.of(dish_1, dish_2, dish_3, dish_4, dish_5, dish_6, dish_7);
    }
    public static List<Dish> dishesTodayByRestaurant1() {
        return List.of(dish_1, dish_2, dish_3, dish_4);
    }
    public static List<Dish> dishesByRestaurant2() {
        return List.of(dish_8, dish_9, dish_10, dish_11, dish_12);
    }
}
