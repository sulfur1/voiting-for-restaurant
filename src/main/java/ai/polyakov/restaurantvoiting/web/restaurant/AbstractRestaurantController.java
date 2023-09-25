package ai.polyakov.restaurantvoiting.web.restaurant;

import ai.polyakov.restaurantvoiting.error.NotFoundException;
import ai.polyakov.restaurantvoiting.model.Restaurant;
import ai.polyakov.restaurantvoiting.repository.RestaurantRepository;
import ai.polyakov.restaurantvoiting.repository.VoteRepository;
import ai.polyakov.restaurantvoiting.to.RestaurantTo;
import ai.polyakov.restaurantvoiting.util.RestaurantUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.slf4j.LoggerFactory.*;

public abstract class AbstractRestaurantController {

    protected final Logger log = getLogger(this.getClass());

    @Autowired
    protected RestaurantRepository restaurantRepository;


    protected List<RestaurantTo> getAllRestaurant() {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();

        return allRestaurants.stream()
                .map(RestaurantUtil::createRestaurantTo).toList();
    }

    protected RestaurantTo getRestaurantById(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));

        return RestaurantUtil.createRestaurantTo(restaurant);
    }
}
