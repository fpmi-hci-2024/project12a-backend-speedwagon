package by.speedteam.speedwagon.repositories;

import by.speedteam.speedwagon.models.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Long> {
}
