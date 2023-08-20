package ai.polyakov.restaurant_voiting.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.util.List;

@Getter
@Setter
public class RestaurantTo extends NamedTo {

    private List<DishTo> dishes;


    public RestaurantTo(Integer id, String name, List<DishTo> dishTos) {
        super(id, name);
        this.dishes = dishTos;
    }

}

