package ai.polyakov.restaurantvoiting.web.restaurant;

import ai.polyakov.restaurantvoiting.model.Dish;
import ai.polyakov.restaurantvoiting.to.DishTo;
import ai.polyakov.restaurantvoiting.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

public class DishTestData {

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

    public static DishTo dishTo(Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice(), dish.getDateDish());
    }
    public static List<DishTo> dishTos(List<Dish> dishes) {
        return dishes.stream().map(DishTestData::dishTo).toList();
    }
}
