package ai.polyakov.restaurantvoiting.to;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {

    @NotNull
    @Min(value = 1, message = "Dish can`t to cost less than 1")
    private Long price;

    @NotNull
    private LocalDate dateDish;

    public DishTo(Integer id, String name, Long price, LocalDate dateDish) {
        super(id, name);
        this.price = price;
        this.dateDish = dateDish;
    }


    @Override
    public String toString() {
        return name + "\n" + price + "\n" + dateDish + "\n";
    }
}
