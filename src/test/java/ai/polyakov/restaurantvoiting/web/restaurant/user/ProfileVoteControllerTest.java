package ai.polyakov.restaurantvoiting.web.restaurant.user;

import ai.polyakov.restaurantvoiting.model.Vote;
import ai.polyakov.restaurantvoiting.repository.VoteRepository;
import ai.polyakov.restaurantvoiting.util.JsonUtil;
import ai.polyakov.restaurantvoiting.util.TimeUtil;
import ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData;
import ai.polyakov.restaurantvoiting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ai.polyakov.restaurantvoiting.web.restaurant.user.ProfileVoteController.PROFILE_REST_VOTE_URL;
import static ai.polyakov.restaurantvoiting.web.restaurant.user.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProfileVoteControllerTest {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private TimeUtil timeUtil;

    @Autowired
    private MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

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
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void update() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        if (timeUtil.isTimeBeforeControl(now.toLocalTime())) {
            perform(MockMvcRequestBuilders.post(PROFILE_REST_VOTE_URL + "/" + RestaurantTestData.RESTAURANT_1));

            perform(MockMvcRequestBuilders.put(PROFILE_REST_VOTE_URL + "/" + RestaurantTestData.RESTAURANT_2))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }
    }
}