package ai.polyakov.restaurantvoiting.web.restaurant.admin;

import ai.polyakov.restaurantvoiting.web.AbstractControllerTest;
import ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData;
import org.junit.jupiter.api.Test;
import ai.polyakov.restaurantvoiting.model.Restaurant;
import ai.polyakov.restaurantvoiting.repository.RestaurantRepository;
import ai.polyakov.restaurantvoiting.util.JsonUtil;
import ai.polyakov.restaurantvoiting.web.user.UserTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData.*;
import static ai.polyakov.restaurantvoiting.web.restaurant.admin.AdminRestaurantController.ADMIN_REST_URL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantTestData.getAll()));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_REST_URL + "/" + RESTAURANT_1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantTestData.getRestaurantTo_1()));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());
        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int id = created.id();
        newRestaurant.setId(id);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(id), newRestaurant);

    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, " ");
        perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Restaurant newRestaurant = new Restaurant(restaurant_1);
        perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = new Restaurant(restaurant_1);
        updated.setName("CoolRest");
        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + "/" + RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getExisted(RESTAURANT_1), updated);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Restaurant updated = new Restaurant(restaurant_1);
        updated.setName("C");
        perform(MockMvcRequestBuilders.put(ADMIN_REST_URL + "/" + RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + "/" + RESTAURANT_1))
                .andExpect(status().isNoContent());
        assertFalse(restaurantRepository.findById(RESTAURANT_1).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + "/" + RESTAURANT_NOT_FOUND))
                .andExpect(status().isNotFound());
    }
}