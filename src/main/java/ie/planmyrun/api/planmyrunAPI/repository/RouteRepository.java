package ie.planmyrun.api.planmyrunAPI.repository;

import ie.planmyrun.api.planmyrunAPI.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

// is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a
// collection of objects. The repository pattern is used to abstract the data layer, providing
// a cleaner separation between the data access logic and business logic.
public interface RouteRepository extends JpaRepository<Route, Long> {
}
