package co.develhope.customquery1.controller;

import co.develhope.customquery1.entity.Flight;
import co.develhope.customquery1.repository.FlightRepository;
import co.develhope.customquery1.entity.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/provision")
    public List<Flight> provisionFlights() {
        Random random = new Random();
        return IntStream.range(0, 50)
                .mapToObj(i -> {
                    Flight flight = new Flight();
                    flight.setDescription("Flight " + i);
                    flight.setFromAirport(randomString(5));
                    flight.setToAirport(randomString(5));
                    flight.setStatus(Status.ON_TIME);
                    return flight;
                })
                .peek(flight -> flightRepository.saveFlight(flight.getDescription(), flight.getFromAirport(), flight.getToAirport(), flight.getStatus()))
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @GetMapping("/status/{status}")
    public List<Flight> getFlightsByStatus(@PathVariable("status") Status status) {
        return flightRepository.findByStatus(status);
    }

    private String randomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return new Random().ints(length, 0, chars.length())
                .mapToObj(chars::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
