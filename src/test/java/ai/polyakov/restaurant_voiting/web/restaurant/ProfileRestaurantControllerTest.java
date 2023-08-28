package ai.polyakov.restaurant_voiting.web.restaurant;


import ai.polyakov.restaurant_voiting.web.AbstractControllerTest;
import ai.polyakov.restaurant_voiting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static ai.polyakov.restaurant_voiting.web.restaurant.ProfileRestaurantController.PROFILE_REST_URL;
import static ai.polyakov.restaurant_voiting.web.restaurant.RestaurantTestData.*;
import static ai.polyakov.restaurant_voiting.web.restaurant.RestaurantTestData.getRestaurantTo_1;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestaurantControllerTest extends AbstractControllerTest {

    private static final String TEST_REST_URL = PROFILE_REST_URL;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAllRestaurantWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getAll()));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getRestaurantWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_REST_URL + "/" + RESTAURANT_1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getRestaurantTo_1()));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_REST_URL + "/" + RESTAURANT_1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getRestaurantNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_REST_URL + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void voteCreate() throws Exception {

        ResultActions action = perform(MockMvcRequestBuilders.post(TEST_REST_URL + "/vote?"
                + "id=" + RESTAURANT_1
                + "&datetime=" + LocalDate.now().atStartOfDay().plusHours(12)
                .toString()))
                .andExpect(status().isCreated());


    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void voteUpdate() throws Exception {
        perform(MockMvcRequestBuilders.post(TEST_REST_URL + "/vote?"
                + "id=" + RESTAURANT_1
                + "&datetime=" + LocalDate.now().atStartOfDay().plusHours(12)
                .toString()));
        perform(MockMvcRequestBuilders.post(TEST_REST_URL + "/vote?"
                + "id=" + RESTAURANT_1
                + "&datetime=" + LocalDate.now().atStartOfDay().plusHours(10)
                .toString()))
                .andExpect(status().isOk());

    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void voteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(TEST_REST_URL + "/vote?"
                + "id=" + RESTAURANT_1
                + "&datetime=" + LocalDate.now().atStartOfDay().plusHours(12)
                .toString()));
        perform(MockMvcRequestBuilders.post(TEST_REST_URL + "/vote?"
                + "id=" + RESTAURANT_1
                + "&datetime=" + LocalDate.now().atStartOfDay().plusHours(12)
                .toString()))
                .andExpect(status().isForbidden());

    }
}