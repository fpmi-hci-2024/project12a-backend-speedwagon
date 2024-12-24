package by.speedteam.speedwagon.repositories;

import by.speedteam.speedwagon.models.EPaymentMethod;
import by.speedteam.speedwagon.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findByName(EPaymentMethod name);
}
