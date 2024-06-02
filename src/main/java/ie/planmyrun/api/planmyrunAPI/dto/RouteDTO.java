package ie.planmyrun.api.planmyrunAPI.dto;

import java.util.List;

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
