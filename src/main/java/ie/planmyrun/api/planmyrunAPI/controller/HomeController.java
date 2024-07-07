package ie.planmyrun.api.planmyrunAPI.controller;

import ie.planmyrun.api.planmyrunAPI.dto.RouteDTO;
import ie.planmyrun.api.planmyrunAPI.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ie.planmyrun.api.planmyrunAPI.service.RouteService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

// A Controller takes in requests, uses the rest of the API to reform certain tasks and
// then returns the appropriate response

@RestController
// The base URL of the API
@RequestMapping("")
public class HomeController {

    @GetMapping("/")
    public ModelAndView redirectWithUsingRedirectPrefix(ModelMap model) {
        return new ModelAndView("redirect:/swagger-ui/index.html");
    }
}
