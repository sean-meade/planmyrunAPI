package ie.planmyrun.api.planmyrunAPI.service;

import ie.planmyrun.api.planmyrunAPI.dto.PointDTO;
import ie.planmyrun.api.planmyrunAPI.dto.RouteDTO;
import ie.planmyrun.api.planmyrunAPI.entity.Point;
import ie.planmyrun.api.planmyrunAPI.entity.Route;
import ie.planmyrun.api.planmyrunAPI.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

// The Service holds all the 'business logic'. This is stuff like methods to perform
// CRUD operations as well as anything like if input values needed to have any math
// performed on it before saving

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    // Like in the Controller Autowired allows you to inject routeRepository when an instance
    // of RouteService is created.
    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    // using the inbuilt method of the JpaRepository return all routes
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    // using the inbuilt method of the JpaRepository return a route by id
    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id);
    }

    private void convertToRoute(RouteDTO routeDTO, Route route) {
        // Set the name for the route
        route.setName(routeDTO.getName());

        // Create a list to hold the points
        List<Point> points = new ArrayList<>();
        // For every point in the route Data Transfer Object
        for (PointDTO pointDTO : routeDTO.getPoints()) {
            // Create a new point and add the lat and long from the point Data Transfer Object
            Point point = new Point();
            point.setLatitude(pointDTO.getLatitude());
            point.setLongitude(pointDTO.getLongitude());
            // Set the route reference in the Point
            point.setRoute(route);
            // Add the point to the list
            points.add(point);
        }
        // Set the points to the route
        route.setPoints(points);
    }

    // Create a Route using the help of the route Data Transfer Object
    public Route createRoute(RouteDTO routeDTO) {

        // Create route and set the name from thr routeDTO
        Route route = new Route();

        // Convert the RouteDTO to Route using the helper method
        convertToRoute(routeDTO, route);

        // save the route and return it
        return routeRepository.save(route);
    }

    // Update route using the id and the RouteDTO
    // Using Optional in case the route the doesn't exist
    public Optional<Route> updateRoute(Long id, RouteDTO routeDTO) {
        // use the find by id to look for existingRoute. If none exists then an
        // empty Optional is returned
        return routeRepository.findById(id).map(existingRoute -> {
            // Convert the RouteDTO to the existing Route using the helper method
            convertToRoute(routeDTO, existingRoute);
            // Save the updated route and return it
            return routeRepository.save(existingRoute);
        });
    }

    // Delete a route using a route id
    public boolean deleteRoute(Long id) {
        // Look for using the findById method
        Optional<Route> route = routeRepository.findById(id);
        // If it exists
        if (route.isPresent()) {
            // Delete the route
            routeRepository.delete(route.get());
            // Return true
            return true;
        }
        // If it doesn't exist return false
        return false;
    }
}
