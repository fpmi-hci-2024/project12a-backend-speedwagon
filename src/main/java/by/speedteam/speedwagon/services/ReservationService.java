package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.Reservation;
import by.speedteam.speedwagon.payload.requests.reservations.CreateReservationRequest;
import by.speedteam.speedwagon.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final BusStopRepository busStopRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, TripRepository tripRepository, PaymentMethodRepository paymentMethodRepository, BusStopRepository busStopRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.busStopRepository = busStopRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getReservationsByUserId(long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);

        //convertToDto
        return reservations;
    }

    public Reservation createReservation(CreateReservationRequest createReservationRequest) {
        Reservation newReservation = new Reservation();
        newReservation.setUser(userRepository.findById(
                createReservationRequest.getUserId()).get());
        newReservation.setTrip(tripRepository.findById(
                createReservationRequest.getTripId()).get());
        newReservation.setPaymentMethod(paymentMethodRepository.findByName(
                createReservationRequest.getPaymentMethod()).get());
        newReservation.setStop(busStopRepository.findById(
                createReservationRequest.getBusStopId()).get());
        newReservation.setPassengersAmount(createReservationRequest.getPassengersAmount());
        newReservation.setNote(createReservationRequest.getNote());
        return reservationRepository.save(newReservation);
    }

    public Reservation updateReservation(long id, Reservation updatedReservation) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setUser(updatedReservation.getUser());
        reservation.setTrip(updatedReservation.getTrip());
        reservation.setPassengersAmount(updatedReservation.getPassengersAmount());
        reservation.setPaymentMethod(updatedReservation.getPaymentMethod());
        reservation.setStop(updatedReservation.getStop());
        reservation.setNote(updatedReservation.getNote());

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reservation not found");
        }
        reservationRepository.deleteById(id);
    }
}
