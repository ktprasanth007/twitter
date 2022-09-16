package tech.codingclub.helix.controller;

import org.apache.log4j.Logger;
import org.jooq.Condition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.codingclub.helix.database.GenericDB;
import tech.codingclub.helix.entity.*;
import tech.codingclub.helix.global.SysProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * User: Prasanth
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final String UPLOADED_FOLDER = "/images/profile-images/";
    private static Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/tweet")
    public
    @ResponseBody
    String makeTweet(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws Exception {

            new GenericDB<Tweets>().addRow(tech.codingclub.helix.tables.Tweets.TWEETS, new Tweets(data, null, new Date().getTime(), ControllerUtils.getUserId(request)));
            return "posted successfully";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/public-tweet/{id}")
    public
    @ResponseBody
    List<TweetsUI> showTweets(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Condition condition = tech.codingclub.helix.tables.Tweets.TWEETS.ID.lessThan(id);
        List<Tweets> tweets = (List<Tweets>) GenericDB.getRows(tech.codingclub.helix.tables.Tweets.TWEETS, Tweets.class, condition, 3, tech.codingclub.helix.tables.Tweets.TWEETS.ID.desc());

        Set<Long> memberIds = new HashSet<Long>();
        for (Tweets tweet: tweets) {
            memberIds.add(tweet.author_id);
        }

        HashMap<Long, Member> memberHashMap = new HashMap<Long, Member>();
        Condition condition1 = tech.codingclub.helix.tables.Member.MEMBER.ID.in(memberIds);
        List<Member> members = (List<Member>) GenericDB.getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, condition1, null);

        for (Member member: members) {
            memberHashMap.put(member.id, member);
        }

        ArrayList<TweetsUI> tweetsUIs = new ArrayList<TweetsUI>();
        for (Tweets tweets1 : tweets)
        {
            Member member = memberHashMap.get(tweets1.author_id);
            TweetsUI tweetsUI = new TweetsUI(tweets1, member);
            tweetsUIs.add(tweetsUI);
        }

        return tweetsUIs;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/follow-member")
    public
    @ResponseBody
    String followMember(@RequestBody Long memberId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long currentUserId = ControllerUtils.getUserId(request);
        if (memberId!= null && !currentUserId.equals(memberId)) {
            Followers follower = new Followers(currentUserId, memberId);
            new GenericDB<Followers>().addRow(tech.codingclub.helix.tables.Followers.FOLLOWERS,follower);
            return "Followed this user";
        } else {
            return "Invalid request";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/unfollow-member")
    public
    @ResponseBody
    String unFollowMember(@RequestBody Long memberId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long currentUserId = ControllerUtils.getUserId(request);
        if (memberId!= null && !currentUserId.equals(memberId)) {
            Condition condition = tech.codingclub.helix.tables.Followers.FOLLOWERS.USER_ID.eq(currentUserId)
                    .and(tech.codingclub.helix.tables.Followers.FOLLOWERS.FOLLOWING_ID.eq(memberId));
            boolean success = GenericDB.deleteRow(tech.codingclub.helix.tables.Followers.FOLLOWERS, condition);
            if (success)
                return "Unfollowed this user";
        } else {
            return "Invalid request";
        }
        return "Backend Error";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/followed")
    public String followed(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {

        Long currentMemberId = ControllerUtils.getUserId(request);

        Condition condition = tech.codingclub.helix.tables.Followers.FOLLOWERS.USER_ID.eq(currentMemberId);
        List<Long> followedIds = new GenericDB<Long>().getColumnRows(tech.codingclub.helix.tables.Followers.FOLLOWERS.FOLLOWING_ID, tech.codingclub.helix.tables.Followers.FOLLOWERS, Long.class, condition, 100);

        Condition selectMemberCondition = tech.codingclub.helix.tables.Member.MEMBER.ID.in(followedIds);
        List<Member> members = (List<Member>) GenericDB.getRows(tech.codingclub.helix.tables.Member.MEMBER, Member.class, selectMemberCondition, 10, tech.codingclub.helix.tables.Member.MEMBER.ID.desc());
        modelMap.addAttribute("FOLLOWED", members);
        return "followed";

    }

    @RequestMapping(method = RequestMethod.GET, value = "/upload/profile")
    public String uploadProfile(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "upload";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/profile")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile, HttpServletRequest request) {
        if (uploadfile.isEmpty()) {
            return new ResponseEntity<String>("please select a file!", HttpStatus.OK);
        }
        String path = "";
        try {
            Long currentMemberId = ControllerUtils.getUserId(request);
            path = saveUploadedFile(uploadfile,currentMemberId);

            Member member = ControllerUtils.getCurrentMember(request);
            member.setImage(path);
            member.is_image_uploaded = true;
            Condition condition = tech.codingclub.helix.tables.Member.MEMBER.ID.eq(currentMemberId);
            new GenericDB<Member>().updateColumn(tech.codingclub.helix.tables.Member.MEMBER.IMAGE, member.getImage(), tech.codingclub.helix.tables.Member.MEMBER, condition);
            new GenericDB<Member>().updateColumn(tech.codingclub.helix.tables.Member.MEMBER.IS_IMAGE_UPLOADED, member.is_image_uploaded, tech.codingclub.helix.tables.Member.MEMBER, condition);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(path, new HttpHeaders(), HttpStatus.OK);
    }

    private static String saveUploadedFile( MultipartFile file, Long userId){
        try {
            String path = SysProperties.getBaseDir()+"/images/profile-images/"+userId+".jpeg";
            String relativePath = "/images/profile-images/"+userId+".jpeg";
            file.transferTo(new File(path));
            return relativePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

}