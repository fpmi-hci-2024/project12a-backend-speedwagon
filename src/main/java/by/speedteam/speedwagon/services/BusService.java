package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.Bus;
import by.speedteam.speedwagon.repositories.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {
    private final BusRepository busRepository;

    @Autowired
    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public Optional<Bus> getBusByNumber(String busNumber) {
        return busRepository.findById(busNumber);
    }

    public Bus createBus(Bus bus) {
        return busRepository.save(bus);
    }

    public Bus updateBus(String busNumber, Bus updatedBus) {
        Bus bus = busRepository.findById(busNumber)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        bus.setDriverName(updatedBus.getDriverName());
        bus.setTotalSeats(updatedBus.getTotalSeats());
        bus.setCarFirm(updatedBus.getCarFirm());
        bus.setColor(updatedBus.getColor());

        return busRepository.save(bus);
    }

    public void deleteBus(String busNumber) {
        if (!busRepository.existsById(busNumber)) {
            throw new RuntimeException("Bus not found");
        }
        busRepository.deleteById(busNumber);
    }
}
