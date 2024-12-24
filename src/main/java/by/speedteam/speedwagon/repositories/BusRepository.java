package by.speedteam.speedwagon.repositories;

import by.speedteam.speedwagon.models.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {
}
