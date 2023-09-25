package ai.polyakov.restaurantvoiting.repository;

import ai.polyakov.restaurantvoiting.model.Restaurant;
import ai.polyakov.restaurantvoiting.model.User;
import ai.polyakov.restaurantvoiting.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {


    @Query("SELECT v FROM Vote v WHERE v.user.id=:user_id AND v.dateTime BETWEEN :start AND :end")
    Optional<Vote> getVoteUserByDateTime(@Param(value = "user_id") int userId, LocalDateTime start, LocalDateTime end);

}
