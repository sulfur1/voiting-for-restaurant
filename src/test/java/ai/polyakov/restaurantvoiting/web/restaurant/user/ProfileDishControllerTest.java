package ai.polyakov.restaurantvoiting.web.restaurant.user;

import ai.polyakov.restaurantvoiting.repository.DishRepository;
import ai.polyakov.restaurantvoiting.web.AbstractControllerTest;
import ai.polyakov.restaurantvoiting.web.restaurant.DishTestData;
import ai.polyakov.restaurantvoiting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static ai.polyakov.restaurantvoiting.web.restaurant.DishTestData.*;
import static ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData.RESTAURANT_1;
import static ai.polyakov.restaurantvoiting.web.restaurant.user.ProfileDishController.REST_PROFILE_DISH_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileDishControllerTest extends AbstractControllerTest {
    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAllToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PROFILE_DISH_URL, RESTAURANT_1))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(DISH_TO_MATCHER.contentJson(dishTos(dishesTodayByRestaurant1())));
    }
    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PROFILE_DISH_URL, RESTAURANT_1))
                .andExpect(status().isUnauthorized());

    }
    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getDishById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PROFILE_DISH_URL + "/" + DISH_1_RESTAURANT_1, RESTAURANT_1))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(DISH_TO_MATCHER.contentJson(dishTo(dish_1)));
    }
    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PROFILE_DISH_URL + "/" + DISH_NOT_FOUND_RESTAURANT_1, RESTAURANT_1))
                .andExpect(status().isNotFound());
    }
}