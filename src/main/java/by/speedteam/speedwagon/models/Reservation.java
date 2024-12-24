package by.speedteam.speedwagon.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(nullable = false)
    private BusStop stop;

    @Column(nullable = false)
    private int passengersAmount;

    private String note;

    public Reservation() {
    }

    public Reservation(User user, Trip trip, PaymentMethod paymentMethod,
                       BusStop stop, int passengersAmount, String note) {
        this.user = user;
        this.trip = trip;
        this.paymentMethod = paymentMethod;
        this.stop = stop;
        this.passengersAmount = passengersAmount;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BusStop getStop() {
        return stop;
    }

    public void setStop(BusStop stop) {
        this.stop = stop;
    }

    public int getPassengersAmount() {
        return passengersAmount;
    }

    public void setPassengersAmount(int passengersAmount) {
        this.passengersAmount = passengersAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
