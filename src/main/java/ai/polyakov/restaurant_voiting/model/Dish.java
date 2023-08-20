package ai.polyakov.restaurant_voiting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;


@Entity
@Table(name = "DISH", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "date_dish", "rest_id"}))
@NoArgsConstructor
@Getter
@Setter
public class Dish extends NamedEntity {

    @Column(name = "price")
    @NotNull
    @Max(value = 1000000, message = "Dish can`t to cost more than 10000 RUB")
    @Min(value = 10000, message = "Dish can`t to cost less than 100 RUB")
    private Long price;

    @NotNull
    @Column(name = "date_dish")
    private LocalDate dateDish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;


    public Dish(String name, Long price, LocalDate dateDish) {
        super(null, name);
        this.price = price;
        this.dateDish = dateDish;
    }

    @Override
    public String toString() {
        return "Dish:" + name + " " + price;
    }
}
