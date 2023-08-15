package ai.polyakov.restaurant_voiting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;


@Entity
@Table(name = "DISH", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "name", "date_dish"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class Dish extends NamedEntity {

    @Column(name = "price")
    @Max(value = 1000000, message = "The amount of money exceeds the allowable value")
    private Long price;

    @Column(name = "date_dish")
    private LocalDate dateDish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Dish:" + name + " " + price;
    }
}
