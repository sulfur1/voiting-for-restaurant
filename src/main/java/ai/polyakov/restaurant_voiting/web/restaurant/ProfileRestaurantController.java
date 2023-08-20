package ai.polyakov.restaurant_voiting.web.restaurant;

import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.model.User;
import ai.polyakov.restaurant_voiting.model.Vote;
import ai.polyakov.restaurant_voiting.repository.RestaurantRepository;
import ai.polyakov.restaurant_voiting.repository.UserRepository;
import ai.polyakov.restaurant_voiting.repository.VoteRepository;
import ai.polyakov.restaurant_voiting.to.DishTo;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import ai.polyakov.restaurant_voiting.util.RestaurantUtil;
import ai.polyakov.restaurant_voiting.web.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

@RestController
@RequestMapping(value = ProfileRestaurantController.PROFILE_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantController {
    static final String PROFILE_REST_URL = "/api/profile/restaurants";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping
    public List<RestaurantTo> getAllRestaurantWithDishes() {
        List<Restaurant> allRestaurants = restaurantRepository.getAllWithDishes().orElse(Collections.emptyList());
        List<RestaurantTo> restaurantTos = new ArrayList<>();

        ListIterator<Restaurant> restIterator = allRestaurants.listIterator();
        while (restIterator.hasNext()) {
            Restaurant restaurant = restIterator.next();
            List<DishTo> dishesTo = restaurant.getDishes().stream()
                    .filter(dish -> dish.getDateDish().isEqual(LocalDate.now()))
                    .map(RestaurantUtil::createDishTo)
                    .toList();

            RestaurantTo restaurantTo = RestaurantUtil.createRestaurantWithDishesTo(restaurant, dishesTo);
            restaurantTos.add(restaurantTo);
        }

        return restaurantTos;
    }

    @PostMapping("/vote")
    @Transactional
    public ResponseEntity<Vote> vote(@RequestParam int id, @RequestParam(name = "datetime") LocalDateTime dateTime, @AuthenticationPrincipal AuthUser authUser) {
        Restaurant restaurant = restaurantRepository.getReferenceById(id);
        User user = userRepository.getReferenceById(authUser.id());
        URI uri;

        Vote vote = voteRepository.getVoteUserByDateTime(authUser.id(), dateTime.toLocalDate()).orElse(null);
        if (vote == null) {
            uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(PROFILE_REST_URL + "/{id}")
                    .buildAndExpand(authUser.id()).toUri();

            Vote created = new Vote();
            //created.setId(authUser.id());
            created.setUser(user);
            created.setRestaurantVote(restaurant);
            created.setDate(dateTime.toLocalDate());

            return ResponseEntity.created(uri).body(voteRepository.save(created));
        } else if (dateTime.toLocalTime().isBefore(RestaurantUtil.CONTROL_TIME)){
            voteRepository.update(restaurant, vote);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }

    }
}
