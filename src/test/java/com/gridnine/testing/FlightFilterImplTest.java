package com.gridnine.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightFilterImplTest {

    private FlightFilter flightFilter;
    private List<Flight> testFlights;

    @BeforeEach
    void setUp() {
        flightFilter = new FlightFilterImpl();
        testFlights = new ArrayList<>();
    }

    @Test
    void testFilterByDepartureBeforeNow() {

        Segment segmentIncorrect = new Segment(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(2));
        Segment segmentCorrect = new Segment(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4));
        Flight flightFalse = new Flight(List.of(segmentIncorrect, segmentCorrect));
        Flight flightTrue = new Flight(List.of(segmentCorrect));
        testFlights.add(flightFalse);
        testFlights.add(flightTrue);

        List<Flight> filterFlights = flightFilter.filterByDepartureBeforeNow(testFlights);

        assertEquals(1, filterFlights.size());
        assertEquals(flightTrue, filterFlights.get(0));
    }

    @Test
    void testFilterByDepartureBeforeNow_EmptyList() {
        List<Flight> filterFlights = flightFilter.filterByDepartureBeforeNow(testFlights);
        assertEquals(0, filterFlights.size());
    }

    @Test
    void testFilterByArrivalBeforeDeparture() {

        Segment segmentCorrect1 = new Segment(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        Segment segmentCorrect2 = new Segment(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4));
        Segment segmentIncorrect1 = new Segment(LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(6));
        Segment segmentIncorrect2 = new Segment(LocalDateTime.now().plusHours(7), LocalDateTime.now().plusHours(6));
        Flight flightTrue = new Flight(List.of(segmentCorrect1, segmentCorrect2));
        Flight flightFalse = new Flight(List.of(segmentIncorrect1, segmentIncorrect2));
        testFlights.add(flightTrue);
        testFlights.add(flightFalse);

        List<Flight> filteredFlights = flightFilter.filterByArrivalBeforeDeparture(testFlights);

        assertEquals(1, filteredFlights.size());
        assertEquals(flightTrue, filteredFlights.get(0));
    }

    @Test
    void testFilterByArrivalBeforeDeparture_EmptyList() {
        List<Flight> filterFlights = flightFilter.filterByArrivalBeforeDeparture(testFlights);
        assertEquals(0, filterFlights.size());
    }

    @Test
    void testFilterByGroundTimeExceeds2Hours() {

        Segment segment1 = new Segment(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(4));
        Segment segment2 = new Segment(LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5));
        Segment segment3 = new Segment(LocalDateTime.now().plusHours(10), LocalDateTime.now().plusHours(12));
        Flight flightTrue = new Flight(List.of(segment1, segment2));
        Flight flightFalse = new Flight(List.of(segment2, segment3));
        testFlights.add(flightTrue);
        testFlights.add(flightFalse);

        List<Flight> filterFlights = flightFilter.filterByGroundTimeExceeds2Hours(testFlights);

        assertEquals(1, filterFlights.size());
        assertEquals(flightTrue, filterFlights.get(0));
    }

    @Test
    void testFilterByGroundTimeExceeds2Hours_EmptyList() {
        List<Flight> filterFlights = flightFilter.filterByGroundTimeExceeds2Hours(testFlights);
        assertEquals(0, filterFlights.size());
    }
}