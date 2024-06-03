package ie.planmyrun.api.planmyrunAPI.dto;
// A Data Transfer Object (DTO) is a design pattern used to transfer data
// between different layers of an application
// Data Transfer Object for a Point
public class PointDTO {
    private double latitude;
    private double longitude;

    // Getters and setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

