package ai.polyakov.restaurantvoiting.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    /*@NotNull
    private List<DishTo> dishes;

    private Integer votes;*/

    public RestaurantTo(Integer id, String name) {
        super(id, name);
        /*this.dishes = dishTos;
        this.votes = votes;*/
    }


    @Override
    public String toString() {
        return super.toString()/* + "\n" + dishes*/;
    }
}

