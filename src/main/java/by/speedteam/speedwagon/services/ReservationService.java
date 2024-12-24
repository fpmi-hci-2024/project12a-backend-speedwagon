package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.Reservation;
import by.speedteam.speedwagon.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
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

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
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
