package ai.polyakov.restaurant_voiting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    @Column(name = "date_dish", columnDefinition = "DATE")
    private LocalDate dateDish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @JsonBackReference
    @Schema(hidden = true)
    private Restaurant restaurant;


    public Dish(String name, Long price, LocalDate dateDish) {
        this(null, name, price, dateDish);
    }
    public Dish(Integer id, String name, Long price, LocalDate dateDish) {
        super(id, name);
        this.price = price;
        this.dateDish = dateDish;
    }

    @Override
    public String toString() {
        return "Dish:" + name + " " + price;
    }
}
