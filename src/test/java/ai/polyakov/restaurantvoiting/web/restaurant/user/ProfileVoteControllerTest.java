package ai.polyakov.restaurantvoiting.web.restaurant.user;

import ai.polyakov.restaurantvoiting.model.Vote;
import ai.polyakov.restaurantvoiting.repository.VoteRepository;
import ai.polyakov.restaurantvoiting.util.JsonUtil;
import ai.polyakov.restaurantvoiting.web.AbstractControllerTest;
import ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData;
import ai.polyakov.restaurantvoiting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ai.polyakov.restaurantvoiting.web.restaurant.user.ProfileVoteController.PROFILE_REST_VOTE_URL;
import static ai.polyakov.restaurantvoiting.web.restaurant.user.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileVoteControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteRepository voteRepository;
    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getVoteToday() throws Exception {
        perform(MockMvcRequestBuilders.post(PROFILE_REST_VOTE_URL + "/" + RestaurantTestData.RESTAURANT_1));
        perform(MockMvcRequestBuilders.get(PROFILE_REST_VOTE_URL))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Vote actual = JsonUtil.readValue(result.getResponse().getContentAsString(), Vote.class);
                    assertTrue(actual.getDateTime().truncatedTo(ChronoUnit.SECONDS).isEqual(vote.getDateTime().truncatedTo(ChronoUnit.SECONDS)));
                    assertSame(actual.id(), vote.id());
                });

    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(PROFILE_REST_VOTE_URL + "/" + RestaurantTestData.RESTAURANT_1))
                .andDo(print())
                .andExpect(status().isCreated());
        Vote actual = VOTE_MATCHER.readFromJson(action);
        int id = actual.id();
        Vote repVote = voteRepository.getExisted(id);
        assertTrue(actual.getDateTime().truncatedTo(ChronoUnit.SECONDS).isEqual(VoteTestData.vote.getDateTime().truncatedTo(ChronoUnit.SECONDS)));
        assertTrue(repVote.getDateTime().truncatedTo(ChronoUnit.SECONDS).isEqual(VoteTestData.vote.getDateTime().truncatedTo(ChronoUnit.SECONDS)));
        assertSame(actual.id(), vote.id());
    }

    @Test
    void update() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(PROFILE_REST_VOTE_URL + "/" + RestaurantTestData.RESTAURANT_1));
        Vote actual = VOTE_MATCHER.readFromJson(action);
        int id = actual.id();
        Vote repVote = voteRepository.getExisted(id);
        LocalDate dateNow = LocalDate.now();
        //border time
        LocalTime time = LocalTime.of(10, 59, 59);
        repVote.setDateTime(LocalDateTime.of(dateNow, time));
        perform(MockMvcRequestBuilders.post(PROFILE_REST_VOTE_URL + "/" + RestaurantTestData.RESTAURANT_2))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}