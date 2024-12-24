package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.Trip;
import by.speedteam.speedwagon.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> getTripById(long id) {
        return tripRepository.findById(id);
    }

    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip updateTrip(long id, Trip updatedTrip) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        trip.setBus(updatedTrip.getBus());
        trip.setRoute(updatedTrip.getRoute());
        trip.setTotalSeats(updatedTrip.getTotalSeats());
        trip.setFreeSeats(updatedTrip.getFreeSeats());
        trip.setDate(updatedTrip.getDate());
        trip.setStartTime(updatedTrip.getStartTime());
        trip.setEndTime(updatedTrip.getEndTime());
        trip.setCost(updatedTrip.getCost());

        return tripRepository.save(trip);
    }

    public void deleteTrip(long id) {
        if (!tripRepository.existsById(id)) {
            throw new RuntimeException("Trip not found");
        }
        tripRepository.deleteById(id);
    }
}
