package ca.sheridancollege.pajaynar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    /**
     * Handles GET requests to the root URL ("/").
     * 
     * @param model The model object used to pass data to the view.
     * @return The name of the view to be displayed (dashboard).
     */
    @GetMapping("/")
    public String dashboard(Model model) {
        // Returning the name of the view template to render the dashboard
        return "dashboard";
    }
}