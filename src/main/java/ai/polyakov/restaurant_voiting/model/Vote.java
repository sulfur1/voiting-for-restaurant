package ai.polyakov.restaurant_voiting.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "VOTE", uniqueConstraints = @UniqueConstraint(columnNames = {"vote_id", "rest_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Vote {

    @Id
    @Column(name = "vote_id")
    private Integer id;

    @Column(name = "date",unique = true)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    private Restaurant restaurantVote;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private User user;
}
