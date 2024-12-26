package by.speedteam.speedwagon.payload.requests.reservations;

import by.speedteam.speedwagon.models.EPaymentMethod;

public class CreateReservationRequest {
    private long userId;
    private long tripId;
    private EPaymentMethod paymentMethod;
    private long busStopId;
    private int passengersAmount;
    private String note;

    public CreateReservationRequest() {
    }

    public CreateReservationRequest(long userId, long tripId, EPaymentMethod paymentMethod,
                                    long busStopId, int passengersAmount, String note) {
        this.userId = userId;
        this.tripId = tripId;
        this.paymentMethod = paymentMethod;
        this.busStopId = busStopId;
        this.passengersAmount = passengersAmount;
        this.note = note;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public EPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(EPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getBusStopId() {
        return busStopId;
    }

    public void setBusStopId(long busStopId) {
        this.busStopId = busStopId;
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
