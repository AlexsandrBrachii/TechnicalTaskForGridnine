package com.gridnine.testing;

import java.util.List;

public interface FlightFilter {

    List<Flight> filterByDepartureBeforeNow(List<Flight> flights);

    List<Flight> filterByArrivalBeforeDeparture(List<Flight> flights);

    List<Flight> filterByGroundTimeExceeds2Hours(List<Flight> flights);

}