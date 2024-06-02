package ie.planmyrun.api.planmyrunAPI.controller;

import ie.planmyrun.api.planmyrunAPI.dto.RouteDTO;
import ie.planmyrun.api.planmyrunAPI.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ie.planmyrun.api.planmyrunAPI.service.RouteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/route")
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public List<Route> getRoute() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/route/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Long id) {
        Optional<Route> route = routeService.getRouteById(id);
        return route.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Route> createRoute(@RequestBody RouteDTO routeDTO) {
        Route createdRoute = routeService.createRoute(routeDTO);
        return new ResponseEntity<>(createdRoute, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody RouteDTO routeDTO) {
        Optional<Route> updatedRoute = routeService.updateRoute(id, routeDTO);
        return updatedRoute.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        boolean isDeleted = routeService.deleteRoute(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
