package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.BusStop;
import by.speedteam.speedwagon.repositories.BusStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusStopService {
    private final BusStopRepository busStopRepository;

    @Autowired
    public BusStopService(BusStopRepository busStopRepository) {
        this.busStopRepository = busStopRepository;
    }

    public List<BusStop> getAllBusStops() {
        return busStopRepository.findAll();
    }

    public Optional<BusStop> getBusStopById(long id) {
        return busStopRepository.findById(id);
    }

    public BusStop createBusStop(BusStop busStop) {
        return busStopRepository.save(busStop);
    }

    public BusStop updateBusStop(long id, BusStop busStopDetails) {
        BusStop busStop = busStopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BusStop not found with id: " + id));

        busStop.setCity(busStopDetails.getCity());
        busStop.setAddress(busStopDetails.getAddress());
        busStop.setBusStation(busStopDetails.isBusStation());

        return busStopRepository.save(busStop);
    }

    public void deleteBusStop(long id) {
        BusStop busStop = busStopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BusStop not found with id: " + id));

        busStopRepository.delete(busStop);
    }
}
