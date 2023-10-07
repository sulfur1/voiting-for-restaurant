package ai.polyakov.restaurantvoiting.web.restaurant.user;

import ai.polyakov.restaurantvoiting.model.Vote;
import ai.polyakov.restaurantvoiting.web.MatcherFactory;
import ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData;
import ai.polyakov.restaurantvoiting.web.user.UserTestData;

import java.time.LocalDateTime;


public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    public static final Vote vote = new Vote(3, LocalDateTime.now());


}
