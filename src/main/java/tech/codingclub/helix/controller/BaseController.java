package tech.codingclub.helix.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Prasanth.
 */

public class BaseController {

    private static Logger logger = Logger.getLogger(BaseController.class);

    @ExceptionHandler
    public
    String defaultErrorHandler(HttpServletRequest req, Exception e) {
//        logger.info("Exception occurred: "+ e.getLocalizedMessage());
        System.out.println(e);
        return "alert";
    }
}