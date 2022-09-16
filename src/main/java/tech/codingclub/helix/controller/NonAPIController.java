package tech.codingclub.helix.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Prasanth
 */
@Controller
@RequestMapping("/")
public class NonAPIController extends BaseController {
    private static final Logger logger = Logger.getLogger(NonAPIController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public
    @ResponseBody
    String testControllerMethod(ModelMap model) {
        logger.info("Test call!");
        return "Test successful!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alien")
    public String errorControllerMethod(ModelMap model) {
        logger.info("Error call!");
        return "alien";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bug")
    public String bugCreateMethod(ModelMap model) {
        int i = 10/0;
        logger.info("Error call!");
        return "alien";
    }

}
