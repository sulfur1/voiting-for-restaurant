package ai.polyakov.restaurantvoiting.web.restaurant.admin;

import ai.polyakov.restaurantvoiting.model.Dish;
import ai.polyakov.restaurantvoiting.repository.DishRepository;
import ai.polyakov.restaurantvoiting.util.JsonUtil;
import ai.polyakov.restaurantvoiting.web.AbstractControllerTest;
import ai.polyakov.restaurantvoiting.web.restaurant.DishTestData;
import ai.polyakov.restaurantvoiting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static ai.polyakov.restaurantvoiting.web.restaurant.DishTestData.*;
import static ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData.*;
import static ai.polyakov.restaurantvoiting.web.restaurant.admin.AdminDishController.REST_ADMIN_DISH_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDishControllerTest extends AbstractControllerTest {

    @Autowired
    private DishRepository dishRepository;
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAllByRestaurant1() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_DISH_URL, RESTAURANT_1))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(DISH_TO_MATCHER.contentJson(DishTestData.dishTos(dishesAllByRestaurant1())));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAllByRestaurant2() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_DISH_URL, RESTAURANT_2))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(DISH_TO_MATCHER.contentJson(DishTestData.dishTos(dishesByRestaurant2())));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getDishByRestaurant1() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_DISH_URL + "/" + DISH_1_RESTAURANT_1, RESTAURANT_1))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(DISH_TO_MATCHER.contentJson(DishTestData.dishTo(dish_1)));
    }
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getDishByRestaurant2() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_DISH_URL + "/" + DISH_2_RESTAURANT_2, RESTAURANT_2))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(DISH_TO_MATCHER.contentJson(DishTestData.dishTo(dish_8)));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getNotFoundDishRestaurant1() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_DISH_URL + "/" + DISH_NOT_FOUND_RESTAURANT_1, RESTAURANT_1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getNotFoundDishRestaurant2() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_DISH_URL + "/" + DISH_NOT_FOUND_RESTAURANT_2, RESTAURANT_2))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_DISH_URL, RESTAURANT_1))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createDishForRestaurant1() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_ADMIN_DISH_URL, RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());
        Dish created = DISH_MATCHER.readFromJson(action);
        int id = created.id();
        newDish.setId(id);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.getExisted(id), newDish);
    }
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createInvalidDishForRestaurant1() throws Exception {
        Dish newDish = new Dish("", 0L, LocalDate.now());
        perform(MockMvcRequestBuilders.post(REST_ADMIN_DISH_URL, RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createDuplicateDishForRestaurant1() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_ADMIN_DISH_URL, RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish_1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateDishForRestaurant1() throws Exception {
        Dish newDish = new Dish(dish_1);
        newDish.setPrice(100L);
        newDish.setName("Update");
        perform(MockMvcRequestBuilders.put(REST_ADMIN_DISH_URL + "/" + DISH_1_RESTAURANT_1, RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(dishRepository.getExisted(DISH_1_RESTAURANT_1), newDish);
    }
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateInvalidDishForRestaurant1() throws Exception {
        Dish newDish = new Dish(dish_1);
        newDish.setPrice(100L);
        newDish.setName("");
        perform(MockMvcRequestBuilders.put(REST_ADMIN_DISH_URL + "/" + DISH_1_RESTAURANT_1, RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void deleteDish() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_DISH_URL + "/" + DISH_1_RESTAURANT_1, RESTAURANT_1))
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(DISH_1_RESTAURANT_1).isPresent());
    }
    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void deleteNotFoundDish() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_DISH_URL + "/" + DISH_NOT_FOUND_RESTAURANT_1, RESTAURANT_1))
                .andExpect(status().isNotFound());
    }
}