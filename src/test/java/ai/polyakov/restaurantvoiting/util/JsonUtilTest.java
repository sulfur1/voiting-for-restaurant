package ai.polyakov.restaurantvoiting.util;

import ai.polyakov.restaurantvoiting.to.RestaurantTo;
import ai.polyakov.restaurantvoiting.web.AbstractControllerTest;
import ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static ai.polyakov.restaurantvoiting.web.restaurant.RestaurantTestData.restaurant_1;

class JsonUtilTest extends AbstractControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    void readWriteValue() {
        try {
            String value = mapper.writeValueAsString(restaurant_1);
            System.out.println(value);
            String value1 = mapper.writeValueAsString(RestaurantTestData.getRestaurantTo_1());
            RestaurantTo restaurantTo = mapper.readValue(value1, RestaurantTo.class);
            System.out.println(restaurantTo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}