package ie.planmyrun.api.planmyrunAPI.service;

import ie.planmyrun.api.planmyrunAPI.entity.Route;
import ie.planmyrun.api.planmyrunAPI.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}