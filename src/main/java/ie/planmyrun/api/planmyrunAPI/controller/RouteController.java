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

// A Controller takes in requests, uses the rest of the API to reform certain tasks and
// then returns the appropriate response

@RestController
// The base URL of the API
@RequestMapping("/api/route")
public class RouteController {

    // Create an instance of the Service so the business logic can be used.
    private final RouteService routeService;

    // Autowired on a constructor will automatically inject the right dependencies on
    // creation of an instance. Here it allows you to inject RouteServices when an instance
    // of RouteController is created.
    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    // Map a GET request with the URL /all which uses the getAllRoutes method from
    // the route service
    @GetMapping("/all")
    public List<Route> getRoute() {
        return routeService.getAllRoutes();
    }

    // Map a GET request with the URL /route/{id} where id is the route. This request uses the
    // getRouteById method from the route service
    @GetMapping("/route/{id}")
    // ResponseEntity is a class in Spring Boot used to represent an HTTP response, including
    // status code, headers, and body. Makes sure that a proper HTTP response is returned rather
    // than an object. You can use an object to define the data that is body of the response
    // Create the method getRouteById and use the 'PathVariable' (i.e. the {id} in the GetMapping URL)
    public ResponseEntity<Route> getRouteById(@PathVariable Long id) {
        // optional is used when the value may or may not be returned.
        Optional<Route> route = routeService.getRouteById(id);
        // if a value is present in route it will return the entity with status ok (200)
        return route.map(ResponseEntity::ok)
                // If nothing is present then return Not found
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Map a post request for creating a route with URL ending in /create
    @PostMapping("/create")
    // Same as above. It creates createRoute method and uses the input data (RequestBody) and
    // the Data Transfer Object to create a route. The requestBody provides the data and teh DTO
    // shows how it is supposed to be structured.
    public ResponseEntity<Route> createRoute(@RequestBody RouteDTO routeDTO) {
        // Create the route with the routeDTO that uses the data from the RequestBody
        Route createdRoute = routeService.createRoute(routeDTO);
        // Return the created route
        return new ResponseEntity<>(createdRoute, HttpStatus.CREATED);
    }

    // Map a PUT request for editing a route giving its {id}
    @PutMapping("/edit/{id}")
    // This request uses both PathVariable and RequestBody
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody RouteDTO routeDTO) {
        // use the updateRoute method from the service to edit the route
        Optional<Route> updatedRoute = routeService.updateRoute(id, routeDTO);
        // same as above
        return updatedRoute.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete mapping with id as bath variable
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        // Use the deleteRoute method from the Service and get the returned True or False if it
        // was successfully deleted or not
        boolean isDeleted = routeService.deleteRoute(id);
        // If it is deleted return the entity with 200
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            // if not return not found
            return ResponseEntity.notFound().build();
        }
    }
}
