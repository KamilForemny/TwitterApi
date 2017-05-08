package a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HeloController {
    @Autowired
    private Twitter twitter;

    @RequestMapping("/")

    public String home(){
        return "searchPage";
    }


    @RequestMapping("/result")
    public String hello(@RequestParam(defaultValue = "Spring") String search, Model model){
        SearchResults searchResults = twitter.searchOperations().search(search);
        //Li
        // st<String> tweets = searchResults.getTweets().stream().map(Tweet::getText).collect(Collectors.toList());
        List<Tweet> tweets = searchResults.getTweets();
        model.addAttribute("tweets",tweets);
        model.addAttribute("search",search);
        return "resultPage";
    }

    @RequestMapping(value = "postsearch", method = RequestMethod.POST)
    public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String search = request.getParameter("search");
        if(search.toLowerCase().contains("smieci")){
            redirectAttributes.addFlashAttribute("error","try type dupa");
            return "redirect:/";
        }
        redirectAttributes.addAttribute("search",search);
        return "redirect:result";

    }
}


















