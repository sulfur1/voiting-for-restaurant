package ai.polyakov.restaurantvoiting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "dish", uniqueConstraints = @UniqueConstraint(columnNames = {"rest_id", "date_dish", "name"}))
@NoArgsConstructor
@Getter
@Setter
public class Dish extends NamedEntity {

    @Column(name = "price")
    @NotNull
    @Min(value = 1, message = "Dish can`t to cost less than 1")
    private Long price;

    @NotNull
    @Column(name = "date_dish", columnDefinition = "DATE")
    private LocalDate dateDish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    //@JsonBackReference
    @JsonIgnore
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
