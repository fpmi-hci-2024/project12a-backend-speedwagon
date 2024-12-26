package by.speedteam.speedwagon.repositories;

import by.speedteam.speedwagon.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT t FROM Trip t WHERE :cost IS NULL OR t.cost <= :cost")
    List<Trip> findByCost(@Param("cost") double cost);
}
