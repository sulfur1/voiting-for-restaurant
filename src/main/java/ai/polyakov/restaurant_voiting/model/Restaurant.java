package ai.polyakov.restaurant_voiting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "RESTAURANT")
@NamedEntityGraph(
        name = "restaurant-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "dishes")
        }

)
public class Restaurant extends NamedEntity {

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantVote")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Vote> votes;

    public Restaurant(String name, List<Dish> dishes, List<Vote> votes) {
        this(null, name, dishes, votes);
    }
    public Restaurant(Integer id, String name, List<Dish> dishes, List<Vote> votes) {
        super(id, name);
        this.dishes = dishes;
        this.votes = votes;
    }

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.dishes, r.votes);
    }

    @Override
    public String toString() {
        return "Restaurant:" + id + "[" + name + "]";
    }
}
