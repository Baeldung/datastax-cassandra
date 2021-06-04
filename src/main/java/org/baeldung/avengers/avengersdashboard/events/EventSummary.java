package org.baeldung.avengers.avengersdashboard.events;

public record EventSummary(String timestamp,
  Double latitude,
  Double longitude,
  Double status) {}