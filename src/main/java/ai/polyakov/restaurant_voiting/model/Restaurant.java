package ai.polyakov.restaurant_voiting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "RESTAURANT")
@NoArgsConstructor
@Getter
@Setter
public class Restaurant extends NamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantVote")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Vote> votes;

    public Restaurant(Integer id, String name, List<Dish> dishes, List<Vote> votes) {
        super(id, name);
        this.dishes = dishes;
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant:" + id + "[" + name + "]";
    }
}
