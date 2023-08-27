package ai.polyakov.restaurant_voiting.web.restaurant;

import ai.polyakov.restaurant_voiting.error.NotFoundException;
import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.model.Vote;
import ai.polyakov.restaurant_voiting.repository.RestaurantRepository;
import ai.polyakov.restaurant_voiting.repository.VoteRepository;
import ai.polyakov.restaurant_voiting.to.DishTo;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import ai.polyakov.restaurant_voiting.util.RestaurantUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;

import static org.slf4j.LoggerFactory.*;

public abstract class AbstractRestaurantController {
    protected final Logger log = getLogger(this.getClass());

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    protected VoteRepository voteRepository;

    protected List<RestaurantTo> getAllRestaurantWithDishes() {
        List<Restaurant> allRestaurants = restaurantRepository.getAllWithDishes().orElse(Collections.emptyList());
        List<RestaurantTo> restaurantTos = new ArrayList<>();

        ListIterator<Restaurant> restIterator = allRestaurants.listIterator();
        while (restIterator.hasNext()) {
            Restaurant restaurant = restIterator.next();
            Optional<List<Vote>> votes = voteRepository.getVotesByRestId(restaurant);
            List<DishTo> dishesTo = restaurant.getDishes().stream()
                    .filter(dish -> dish.getDateDish().isEqual(LocalDate.now()))
                    .map(RestaurantUtil::createDishTo)
                    .toList();

            RestaurantTo restaurantTo = RestaurantUtil.createRestaurantToWithDishesTo(restaurant, dishesTo, votes.orElse(Collections.emptyList()).size());
            restaurantTos.add(restaurantTo);
        }

        return restaurantTos;
    }
    protected RestaurantTo getRestaurantWithDishes(int id) {
        Restaurant restaurant = restaurantRepository.getRestaurantByIdWithDishes(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));
        List<DishTo> dishTos = restaurant.getDishes().stream()
                .map(RestaurantUtil::createDishTo)
                .filter(dishTo -> dishTo.getDateDish().isEqual(LocalDate.now()))
                .toList();
        Optional<List<Vote>> votes = voteRepository.getVotesByRestId(restaurant);

        return RestaurantUtil.createRestaurantToWithDishesTo(restaurant, dishTos, votes.orElse(Collections.emptyList()).size());
    }
}
