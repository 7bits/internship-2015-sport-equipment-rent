package it.sevenbits.web.controllers;

import it.sevenbits.web.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by awemath on 7/21/15.
 */
@Controller
@RequestMapping(value = "/deal")
public class DealController {
    @Autowired
    DealService dealService;

    @RequestMapping(method = RequestMethod.GET)
    public String getIt(@RequestParam(value="deal_id", required = false) String dealId, @RequestParam(value="accept", required = false) String isAccept, final Model model) {
        
        return "";
    }
}
