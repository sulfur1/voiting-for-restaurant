package ai.polyakov.restaurant_voiting.repository;

import ai.polyakov.restaurant_voiting.model.Dish;
import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {


    @EntityGraph(value = "restaurant-entity-graph")
    @Query("SELECT r FROM Restaurant r")
    Optional<List<Restaurant>> getAllWithDishes();

    @EntityGraph(value = "restaurant-entity-graph")
    @Query("SELECT r FROM Restaurant r WHERE r.id=:id")
    Optional<Restaurant> getRestaurantByIdWithDishes(int id);


}
