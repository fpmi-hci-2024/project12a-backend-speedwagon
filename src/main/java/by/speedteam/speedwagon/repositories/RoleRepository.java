package by.speedteam.speedwagon.repositories;

import by.speedteam.speedwagon.models.ERole;
import by.speedteam.speedwagon.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
