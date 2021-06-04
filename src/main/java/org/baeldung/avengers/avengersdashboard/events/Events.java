package org.baeldung.avengers.avengersdashboard.events;

import java.util.List;

public record Events(List<EventSummary> values, String pageState) {}