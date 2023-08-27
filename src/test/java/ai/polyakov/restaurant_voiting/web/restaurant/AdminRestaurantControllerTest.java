package ai.polyakov.restaurant_voiting.web.restaurant;

import ai.polyakov.restaurant_voiting.error.NotFoundException;
import ai.polyakov.restaurant_voiting.model.Dish;
import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.repository.RestaurantRepository;
import ai.polyakov.restaurant_voiting.to.DishTo;
import ai.polyakov.restaurant_voiting.to.RestaurantTo;
import ai.polyakov.restaurant_voiting.util.JsonUtil;
import ai.polyakov.restaurant_voiting.util.RestaurantUtil;
import ai.polyakov.restaurant_voiting.web.AbstractControllerTest;
import ai.polyakov.restaurant_voiting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static ai.polyakov.restaurant_voiting.web.restaurant.AdminRestaurantController.ADMIN_REST_URL;
import static ai.polyakov.restaurant_voiting.web.restaurant.RestaurantTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = UserTestData.ADMIN_MAIL)
class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String TEST_REST_URL = ADMIN_REST_URL;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void getAllRestaurantWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getAll()));
    }

    @Test
    void getRestaurantWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_REST_URL + "/" + RESTAURANT_1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getRestaurantTo_1()));
    }

    @Test
    void getRestaurantNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(TEST_REST_URL + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createRestaurant() throws Exception {
        Restaurant restaurant = RestaurantTestData.getNew();

        ResultActions action = perform(MockMvcRequestBuilders.post(TEST_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        restaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, restaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getRestaurantByIdWithDishes(newId).get(), restaurant);
    }

    @Test
    void createInvalidRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(null, null, null, null);;

        perform(MockMvcRequestBuilders.post(TEST_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateDishes() throws Exception {
        Restaurant restaurant = getUpdated();

        perform(MockMvcRequestBuilders.put(TEST_REST_URL + "/" + RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isNoContent());

        Restaurant updated = restaurantRepository.getRestaurantByIdWithDishes(RESTAURANT_1).get();
        restaurant.getDishes().addAll(restaurant_1.getDishes());

        RESTAURANT_MATCHER.assertMatch(updated, restaurant);
    }

    @Test
    void updateInvalidDishes() throws Exception {
        Restaurant restaurant = getInvalidUpdated();

        perform(MockMvcRequestBuilders.put(TEST_REST_URL + "/" + RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isUnprocessableEntity());
    }
}