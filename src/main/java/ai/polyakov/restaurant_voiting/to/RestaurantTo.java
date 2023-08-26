package ai.polyakov.restaurant_voiting.to;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestaurantTo extends NamedTo {

    private List<DishTo> dishes;

    public RestaurantTo(Integer id, String name, List<DishTo> dishTos) {
        super(id, name);
        this.dishes = dishTos;
    }

    @Override
    public String toString() {
        return super.toString() + " " + dishes;
    }
}

