package ie.planmyrun.api.planmyrunAPI.dto;

import java.util.List;
// A Data Transfer Object (DTO) is a design pattern used to transfer data
// between different layers of an application
// Data Transfer Object for a Route
public class RouteDTO {
    private String name;
    private List<PointDTO> points;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PointDTO> getPoints() {
        return points;
    }

    public void setPoints(List<PointDTO> points) {
        this.points = points;
    }
}
