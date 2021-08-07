package org.baeldung.avengers.avengersdashboard.map;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapController {
  @Autowired
  private MapService mapService;

  @Value("${GOOGLE_CLIENT_ID}")
  private String googleClientId;

  @ModelAttribute("googleClientId")
  String getGoogleClientId() {
    return googleClientId;
  }

  @GetMapping("/map")
  public ModelAndView showMap(@RequestParam(name = "avenger", required = false) List<String> avenger,
  @RequestParam(required = false) String start, @RequestParam(required = false) String end) throws Exception {
    var result = new ModelAndView("map");
    result.addObject("inputStart", start);
    result.addObject("inputEnd", end);
    result.addObject("inputAvengers", avenger);
    
    result.addObject("avengers", mapService.listAvengers());

    if (avenger != null && !avenger.isEmpty() && start != null && end != null) {
      var paths = mapService.getPaths(avenger, 
        LocalDateTime.parse(start).toInstant(ZoneOffset.UTC), 
        LocalDateTime.parse(end).toInstant(ZoneOffset.UTC));

      result.addObject("paths", paths);
    }

    return result;
  }
}
