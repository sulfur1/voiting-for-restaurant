package ai.polyakov.restaurantvoiting.web.restaurant.user;

import ai.polyakov.restaurantvoiting.web.AbstractControllerTest;
import ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData;
import ai.polyakov.restaurantvoiting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData.*;
import static ai.polyakov.restaurantvoiting.web.restaurant.user.ProfileDishController.REST_PROFILE_DISH_URL;
import static ai.polyakov.restaurantvoiting.web.restaurant.user.ProfileRestaurantController.PROFILE_REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestaurantControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantTestData.getAll()));
    }
    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PROFILE_DISH_URL, RESTAURANT_1))
                .andExpect(status().isUnauthorized());

    }
    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL + "/" + RESTAURANT_1))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getRestaurantTo_1()));
    }
    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_REST_URL + "/" + RESTAURANT_NOT_FOUND))
                .andExpect(status().isNotFound());
    }
}