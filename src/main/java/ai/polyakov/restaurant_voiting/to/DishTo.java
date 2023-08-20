package ai.polyakov.restaurant_voiting.to;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class DishTo {
    @NotBlank
    @Size(min = 5, message = "Name`s dish must have size not less than 5")
    private String name;

    @NotNull
    @Max(value = 10000, message = "Dish can`t to cost more than 10000 RUB")
    @Min(value = 100, message = "Dish can`t to cost less than 100 RUB")
    private Double price;

    @NotNull
    private LocalDate dateDish;
}
