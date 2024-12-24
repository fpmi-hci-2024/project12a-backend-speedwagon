package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.EPaymentMethod;
import by.speedteam.speedwagon.models.PaymentMethod;
import by.speedteam.speedwagon.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    @Autowired
    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public Optional<PaymentMethod> getPaymentMethodById(long id) {
        return paymentMethodRepository.findById(id);
    }

    public Optional<PaymentMethod> findPaymentMethodByName(EPaymentMethod name) {
        return paymentMethodRepository.findByName(name);
    }

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod updatePaymentMethod(long id, PaymentMethod paymentMethod) {
        Optional<PaymentMethod> paymentMethodData = paymentMethodRepository.findById(id);
        if (paymentMethodData.isPresent()) {
            PaymentMethod existingPaymentMethod = paymentMethodData.get();
            existingPaymentMethod.setName(paymentMethod.getName());
            return paymentMethodRepository.save(existingPaymentMethod);
        } else {
            throw new RuntimeException("Payment method with id = " + id + " not found");
        }
    }

    public void deletePaymentMethod(long id) {
        paymentMethodRepository.deleteById(id);
    }
}