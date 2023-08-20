package ai.polyakov.restaurant_voiting.repository;

import ai.polyakov.restaurant_voiting.model.Restaurant;
import ai.polyakov.restaurant_voiting.model.User;
import ai.polyakov.restaurant_voiting.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.id=:id AND v.date=:date")
    Optional<Vote> getVoteUserByDateTime(int id, LocalDate date);

    @Transactional
    default Vote update(Restaurant restaurant, Vote vote) {
        vote.setRestaurantVote(restaurant);

        return save(vote);
    }
    @Transactional
    default Vote create(Vote vote, Restaurant restaurant, User user) {
        vote.setRestaurantVote(restaurant);
        vote.setUser(user);

        return save(vote);
    }
}
