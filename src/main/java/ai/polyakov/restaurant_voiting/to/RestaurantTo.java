package ai.polyakov.restaurant_voiting.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    @NotNull
    private List<DishTo> dishes;

    private Integer votes;

    public RestaurantTo(Integer id, String name, List<DishTo> dishTos, Integer votes) {
        super(id, name);
        this.dishes = dishTos;
        this.votes = votes;
    }


    @Override
    public String toString() {
        return super.toString() + "\n" + dishes;
    }
}

