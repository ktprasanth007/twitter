package tech.codingclub.helix.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jooq.Condition;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tech.codingclub.helix.database.GenericDB;
import tech.codingclub.helix.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * User: rishabh
 */
@Controller
@RequestMapping("/")
public class MainController extends BaseController {

    private static Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/helloworld")
    public String getQuiz(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "hello";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody
    LoginResponse handleLogin(@RequestBody Member member, HttpServletResponse response, HttpServletRequest request) {

        Condition condition = tech.codingclub.helix.tables.Member.MEMBER.EMAIL.eq(member.email).and(tech.codingclub.helix.tables.Member.MEMBER.PASSWORD.eq(member.password));
        List<Member> row = (List<Member>) GenericDB.getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, condition, 1);

        if (!row.isEmpty()) {
            //Condition is met
            ControllerUtils.setUserSession(request, row.get(0)); //This will create cookies for our user
            return new LoginResponse(row.get(0).id, "Welcome to Twitter!!", true);
        } else {
            //Wrong Combination
            return new LoginResponse(null, "Wrong credentials", false);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signup")
    public String signUp(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public @ResponseBody
    SignupResponse signUpData(@RequestBody Member member, HttpServletResponse response, HttpServletRequest request) {

        boolean user_created = false;
        String message = "";
        if (GenericDB.getCount(tech.codingclub.helix.tables.Member.MEMBER, tech.codingclub.helix.tables.Member.MEMBER.EMAIL.eq(member.email)) > 0) {
            message = "User already exist for this mail Id";
        } else {
            member.role = "cm";
            new GenericDB<Member>().addRow(tech.codingclub.helix.tables.Member.MEMBER, member);
            user_created = true;
            message = "User created";
        }

        return new SignupResponse(message, user_created);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/time")
    public @ResponseBody String getTime(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
//        return "hello"; //As we have added ResponseBody annotation it won't check for the hello.jsp anymore

        TimeApi timeApi = new TimeApi(new Date().toString(), new Date().getTime());
        return new Gson().toJson(timeApi);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/wiki")
    public @ResponseBody String getWikiResultGson(ModelMap modelMap, @RequestParam("country") String country, HttpServletResponse response, HttpServletRequest request) {

        WikipediaDownloader wiki = new WikipediaDownloader(country);
        WikiResult wikiResult = wiki.getResult();
        return new Gson().toJson(wikiResult);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/wiki/html")
    public String getWikiResultHtml(ModelMap modelMap, @RequestParam("keyword") String keyword, HttpServletResponse response, HttpServletRequest request) {

        WikipediaDownloader wiki = new WikipediaDownloader(keyword);
        WikiResult wikiResult = wiki.getResult();

        modelMap.addAttribute("IMAGE_URL", wikiResult.getImage_url());
        modelMap.addAttribute("DESCRIPTION", wikiResult.getText_result());

        return "wikiapi";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/handle")
    public
    @ResponseBody
    String handleEncrypt(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        return "ok";
    }
}