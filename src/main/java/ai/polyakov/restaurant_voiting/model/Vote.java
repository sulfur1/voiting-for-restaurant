package ai.polyakov.restaurant_voiting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "VOTE", uniqueConstraints = @UniqueConstraint(columnNames = {"vote_id", "rest_id", "date"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Vote {

    @Id
    @Column(name = "vote_id")
    private Integer id;

    @Column(name = "date",unique = true, columnDefinition = "DATE")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurantVote;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    @JsonIgnore
    private User user;
}
