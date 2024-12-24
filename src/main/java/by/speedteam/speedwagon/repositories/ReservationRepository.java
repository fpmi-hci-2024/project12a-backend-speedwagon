package by.speedteam.speedwagon.repositories;

import by.speedteam.speedwagon.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//    @Query("select r from Reservation r where r.user.id = :userId")
    List<Reservation> findByUserId(long userId);
}
