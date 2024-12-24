package by.speedteam.speedwagon.services;

import by.speedteam.speedwagon.models.Route;
import by.speedteam.speedwagon.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Optional<Route> getRouteById(long id) {
        return routeRepository.findById(id);
    }

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    public Route updateRoute(long id, Route updatedRoute) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        route.setStartPoint(updatedRoute.getStartPoint());
        route.setDestinationPoint(updatedRoute.getDestinationPoint());
        route.setListOfStopsId(updatedRoute.getListOfStopsId());

        return routeRepository.save(route);
    }

    public void deleteRoute(long id) {
        if (!routeRepository.existsById(id)) {
            throw new RuntimeException("Route not found");
        }
        routeRepository.deleteById(id);
    }
}
