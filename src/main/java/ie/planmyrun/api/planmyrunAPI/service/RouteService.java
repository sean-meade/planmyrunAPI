package ie.planmyrun.api.planmyrunAPI.service;

import ie.planmyrun.api.planmyrunAPI.dto.PointDTO;
import ie.planmyrun.api.planmyrunAPI.dto.RouteDTO;
import ie.planmyrun.api.planmyrunAPI.entity.Point;
import ie.planmyrun.api.planmyrunAPI.entity.Route;
import ie.planmyrun.api.planmyrunAPI.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id);
    }

    public Route createRoute(RouteDTO routeDTO) {
        Route route = new Route();
        route.setName(routeDTO.getName());

        List<Point> points = new ArrayList<>();
        for (PointDTO pointDTO : routeDTO.getPoints()) {
            Point point = new Point();
            point.setLatitude(pointDTO.getLatitude());
            point.setLongitude(pointDTO.getLongitude());
            point.setRoute(route); // Set the route reference in the Point
            points.add(point);
        }
        route.setPoints(points);

        return routeRepository.save(route);
    }

    public Optional<Route> updateRoute(Long id, RouteDTO routeDTO) {
        return routeRepository.findById(id).map(existingRoute -> {
            existingRoute.setName(routeDTO.getName());

            Set<Point> newPoints = new HashSet<>();
            for (PointDTO pointDTO : routeDTO.getPoints()) {
                Point point = new Point();
                point.setLatitude(pointDTO.getLatitude());
                point.setLongitude(pointDTO.getLongitude());
                point.setRoute(existingRoute); // Set the route reference in the Point
                newPoints.add(point);
            }
            existingRoute.getPoints().clear(); // Clear existing points
            existingRoute.getPoints().addAll(newPoints); // Add new points

            return routeRepository.save(existingRoute);
        });
    }

    public boolean deleteRoute(Long id) {
        Optional<Route> route = routeRepository.findById(id);
        if (route.isPresent()) {
            routeRepository.delete(route.get());
            return true;
        }
        return false;
    }
}
