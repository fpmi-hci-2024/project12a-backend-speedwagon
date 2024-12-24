package by.speedteam.speedwagon.models;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPaymentMethod name;

    public PaymentMethod() {
    }

    public PaymentMethod(EPaymentMethod name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EPaymentMethod getName() {
        return name;
    }

    public void setName(EPaymentMethod name) {
        this.name = name;
    }
}
