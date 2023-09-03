package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterImpl implements FlightFilter {

    @Override
    public List<Flight> filterByDepartureBeforeNow(List<Flight> flights) {

        LocalDateTime currentTime = LocalDateTime.now();
        List<Flight> filterFlights = new ArrayList<>();

        for (Flight flight : flights) {
            boolean correct = true;

            for (Segment segment : flight.getSegments()) {
                if (segment.getDepartureDate().isBefore(currentTime)) {
                    correct = false;
                    break;
                }
            }

            if (correct) {
                filterFlights.add(flight);
            }
        }
        return filterFlights;
    }

    @Override
    public List<Flight> filterByArrivalBeforeDeparture(List<Flight> flights) {

        List<Flight> filterFlights = new ArrayList<>();

        for (Flight flight : flights) {
            boolean correct = true;

            for (Segment segment : flight.getSegments()) {
                if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                    correct = false;
                    break;
                }
            }

            if (correct) {
                filterFlights.add(flight);
            }
        }
        return filterFlights;
    }

    @Override
    public List<Flight> filterByGroundTimeExceeds2Hours(List<Flight> flights) {

        List<Flight> filterFlights = new ArrayList<>();

        for (Flight flight : flights) {
            boolean correct = true;
            List<Segment> segments = flight.getSegments();

            for (int i = 0; i < segments.size() - 1; i++) {
                LocalDateTime currentArrival = segments.get(i).getArrivalDate();
                LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();

                if (currentArrival.plusHours(2).isBefore(nextDeparture)) {
                    correct = false;
                    break;
                }
            }

            if (correct) {
                filterFlights.add(flight);
            }
        }
        return filterFlights;
    }
}