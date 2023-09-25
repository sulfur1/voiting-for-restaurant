package ai.polyakov.restaurantvoiting.repository;

import ai.polyakov.restaurantvoiting.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    List<Dish> findAllByRestaurant_Id(int restId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:rest_id AND d.dateDish=:date_dish")
    List<Dish> findAllTodayByRestaurantId(@Param(value = "rest_id") int restId, @Param(value = "date_dish") LocalDate dateDish);
}
