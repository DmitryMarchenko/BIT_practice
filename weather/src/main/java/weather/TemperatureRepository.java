package weather;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemperatureRepository extends CrudRepository<Temperature, Long> {
    Optional<Temperature> findByDate(String date);
}
