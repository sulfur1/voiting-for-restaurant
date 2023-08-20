package ai.polyakov.restaurant_voiting.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class DishTo {

    private String name;

    private Long price;

    private LocalDate dateDish;
}
