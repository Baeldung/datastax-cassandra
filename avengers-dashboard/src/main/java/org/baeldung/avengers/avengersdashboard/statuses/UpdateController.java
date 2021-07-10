package org.baeldung.avengers.avengersdashboard.statuses;

import java.util.List;
import org.baeldung.avengers.avengersdashboard.events.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UpdateController {
  @Autowired
  private StatusesService statusesService;

  @Autowired
  private EventsService eventsService;

  @PostMapping("/update/{avenger}")
  public void update(@PathVariable String avenger, @RequestBody UpdateBody body) throws Exception {
    eventsService.createEvent(avenger, body.lat(), body.lng(), body.status());
    statusesService.updateStatus(avenger, lookupLocation(body.lat(), body.lng()), getStatus(body.status()));
  }

  private String lookupLocation(Double lat, Double lng) {
    return "New York";
  }

  private String getStatus(Double status) {
    if (status == 0) {
      return "DECEASED";
    } else if (status > 0.9) {
      return "HEALTHY";
    } else {
      return "INJURED";
    }
  }

  private static record UpdateBody(Double lat, Double lng, Double status) {}
}
