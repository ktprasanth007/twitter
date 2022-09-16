package tech.codingclub.helix.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jooq.Condition;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.codingclub.helix.database.GenericDB;
import tech.codingclub.helix.entity.Followers;
import tech.codingclub.helix.entity.Member;
import tech.codingclub.helix.entity.Tweets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Prasanth
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/admin")
    public String adminLogin(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "adminlogin";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public String home(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {

        /*
//        modelMap.addAttribute("NAME", member.firstName);
//        modelMap.addAttribute("MEMBER", member); //If we want just name then specify it in the jsp file like ${MEMBER.firstName}
        if (member != null) {
            //Should be able to access only he is logged in - For this we use Interceptor
            modelMap.addAttribute("MEMBER", new Gson().toJson(member));
            return "home";
        }
        return "login";
        */
        //This will be executed after the preHandle method is executed from ApplicationInterceptor
//        modelMap.addAttribute("MEMBER", new Gson().toJson(member));

        Member member = ControllerUtils.getCurrentMember(request); //To make this work we need to setSession at the time of

        //Logic for Recommendations starts here
        List<Member> members = (List<Member>) GenericDB.getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, null, 3, tech.codingclub.helix.tables.Member.MEMBER.ID.desc());

        ArrayList<Long> memberIds = new ArrayList<Long>();
        for (Member mem : members) {
            memberIds.add(mem.id);
        }

        Condition condition = tech.codingclub.helix.tables.Followers.FOLLOWERS.USER_ID.eq(member.id)
                .and(tech.codingclub.helix.tables.Followers.FOLLOWERS.FOLLOWING_ID.in(memberIds));
        List<Followers> followersList = (List<Followers>) GenericDB.getRows(tech.codingclub.helix.tables.Followers.FOLLOWERS, Followers.class, condition,null);

        Set<Long> followedMemberIds = new HashSet<Long>();
        for (Followers follower : followersList) {
            followedMemberIds.add(follower.following_id);
        }

        for (Member member1 : members) {
                if (followedMemberIds.contains(member1.id)) {
                        member1.is_followed = true;
                }
        }
        // Recommendations logic end here

        modelMap.addAttribute("RECOMMENDATIONS", members);
        modelMap.addAttribute("MEMBER", member);
        return "home";

    }

}