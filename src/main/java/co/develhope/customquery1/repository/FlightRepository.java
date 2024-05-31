package co.develhope.customquery1.repository;

import co.develhope.customquery1.entity.Flight;
import co.develhope.customquery1.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Modifying
    @Query(value = "INSERT INTO Flight (description, fromAirport, toAirport, status) VALUES (:description, :fromAirport, :toAirport, :status)", nativeQuery = true)
    void saveFlight(@Param("description") String description, @Param("fromAirport") String fromAirport, @Param("toAirport") String toAirport, @Param("status") Status status);

    List<Flight> findByStatus(Status status);

}
