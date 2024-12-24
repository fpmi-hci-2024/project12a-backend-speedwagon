package by.speedteam.speedwagon.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    private String note;

    public Reservation() {
    }

//    public Reservation(User user, Trip trip, PaymentMethod paymentMethod, BusStop stop,
//                       int passengersAmount, LocalDate date, LocalTime time, String note) {
//        this.user = user;
//        this.trip = trip;
//        this.paymentMethod = paymentMethod;
//        this.stop = stop;
//        this.passengersAmount = passengersAmount;
//        this.date = date;
//        this.time = time;
//        this.note = note;
//    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
