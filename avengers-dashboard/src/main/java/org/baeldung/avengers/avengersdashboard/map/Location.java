package org.baeldung.avengers.avengersdashboard.map;

import java.time.Instant;
import java.math.BigDecimal;

public record Location(String avenger, Instant timestamp, BigDecimal latitude, BigDecimal longitude, BigDecimal status) {}
