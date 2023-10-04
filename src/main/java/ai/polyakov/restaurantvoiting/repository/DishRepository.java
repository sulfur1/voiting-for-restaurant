package ai.polyakov.restaurantvoiting.repository;

import ai.polyakov.restaurantvoiting.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> findAllByRestaurant_Id(int restId);
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:rest_id AND d.dateDish=:date_dish ORDER BY d.id")
    List<Dish> findAllTodayByRestaurantId(@Param(value = "rest_id") int restId, @Param(value = "date_dish") LocalDate dateDish);
    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.restaurant.id=:rest_id")
    Optional<Dish> findByIdAndRestaurantId(int id, @Param(value = "rest_id") int restId);
}
