package org.baeldung.avengers.avengersdashboard.statuses;

import java.util.List;
import org.baeldung.avengers.avengersdashboard.events.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StatusesController {
  @Autowired
  private StatusesService statusesService;

  @Autowired
  private EventsService eventsService;

  @GetMapping("/")
  public ModelAndView getStatuses() {
    var result = new ModelAndView("dashboard");
    result.addObject("statuses", statusesService.getStatuses());

    return result;
  }

  @GetMapping("/avenger/{avenger}")
  public Object getAvengerStatus(@PathVariable String avenger, @RequestParam(required = false) String page) {
    var result = new ModelAndView("dashboard");
    result.addObject("avenger", avenger);
    result.addObject("statuses", statusesService.getStatuses());
    result.addObject("events", eventsService.getEvents(avenger, page));

    return result;
  }
}
