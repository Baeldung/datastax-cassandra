package org.baeldung.avengers.avengersdashboard.events;

public record Event(String avenger, 
  String timestamp,
  Double latitude,
  Double longitude,
  Double status) {}