package ai.polyakov.restaurantvoiting.web.restaurant.user;

import ai.polyakov.restaurantvoiting.web.AbstractControllerTest;
import ai.polyakov.restaurantvoiting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static ai.polyakov.restaurantvoiting.util.RestaurantUtil.createDishTo;
import static ai.polyakov.restaurantvoiting.web.restaurant.DishTestData.*;
import static ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData.RESTAURANT_1;
import static ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData.RESTAURANT_2;
import static ai.polyakov.restaurantvoiting.web.restaurant.user.ProfileDishController.REST_PROFILE_DISH_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileDishControllerTest extends AbstractControllerTest {
    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAllToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PROFILE_DISH_URL, RESTAURANT_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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
        perform(MockMvcRequestBuilders.get(REST_PROFILE_DISH_URL + "/" + DISH_2_RESTAURANT_2, RESTAURANT_2))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(createDishTo(dish_8)));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_PROFILE_DISH_URL + "/" + DISH_NOT_FOUND_RESTAURANT_1, RESTAURANT_1))
                .andExpect(status().isNotFound());
    }
}