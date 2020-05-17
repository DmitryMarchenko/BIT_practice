package rbc;

import rbc.Rate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {
    Optional<Rate> findByDate(String date);
}
