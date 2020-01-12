package life.majiang.community.community.controller;

import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Mapper.QuestionMapper;
import life.majiang.community.community.Mapper.UserMapper;
import life.majiang.community.community.dto.PaginationDTO;
import life.majiang.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by huang on 2020-01-11.
 */
@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "4") Integer size){
        Cookie[] cookies = request.getCookies();
        User user = null;
        //首次进入页面，不验证
        if (cookies != null && cookies.length > 0) {
            //循环cookie
            for (Cookie cookie : cookies) {
                //寻找name为token的cookie
                if (cookie.getName().equals("token")) {
                    //获取token 同时查询数据库，验证存在性
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    //存在则写入Session
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //判断登录状态
        if(user == null){
            return "redirect:/";
        }
        //对页面传过来的选择校验
        if ("questions".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
        }else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新问题");
        }
        //获取个人问题
        PaginationDTO paginationDTO = questionService.serchOurselvesQuestions(user.getId(), page, size);
        //添加到MODEL，传给前端
        model.addAttribute("pagination", paginationDTO);
        //前往个人问题页
        return "profile";
    }

}
