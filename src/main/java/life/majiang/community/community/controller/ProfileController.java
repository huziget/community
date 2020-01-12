package life.majiang.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by huang on 2020-01-11.
 */
@Controller
public class ProfileController {

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model){

        if ("questions".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
        }else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新问题");
        }
        return "profile";
    }

}
