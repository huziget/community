package life.majiang.community.community.controller;


import life.majiang.community.community.Entity.Question;
import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Mapper.QuestionMapper;
import life.majiang.community.community.Mapper.UserMapper;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * created by huang on 2020/1/7
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /**
     * 主页面处理，免登陆验证COOKIR和SESSION
     * @param request
     * @return
     */
    @GetMapping("/")
    public String hello(HttpServletRequest request,Model model) {
        //获取cookie
        Cookie[] cookies = request.getCookies();
        //首次进入页面，不验证
        if (cookies != null && cookies.length > 0) {
            //循环cookie
            for (Cookie cookie : cookies) {
                //寻找name为token的cookie
                if (cookie.getName().equals("token")) {
                    //获取token 同时查询数据库，验证存在性
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    //存在则写入Session
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //获取问题列表
        List<QuestionDTO> questionList = questionService.searchQuestionList();
        //添加到MODEL，传给前端
        model.addAttribute("questions", questionList);
        //去往主页
        return "index";
    }
}
