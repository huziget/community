package life.majiang.community.community.controller;

import life.majiang.community.community.Entity.Question;
import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Mapper.QuestionMapper;
import life.majiang.community.community.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @auther huang
 * @date 2020/1/9 16:47
 */
@Controller
public class PublishController {

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 发布页面发布功能
     * @param title
     * @param description
     * @param tag
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag, HttpServletRequest request,
            Model model) {
        //把值存入Model
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //前端数据检验
        if(title == null || "".equals(title)){
            model.addAttribute("erro","标题不能为空");
            return "publish";
        }

        if(description == null || "".equals(description)){
            model.addAttribute("erro","内容不能为空");
            return "publish";
        }

        if(tag == null || "".equals(tag)){
            model.addAttribute("erro","标签不能为空");
            return "publish";
        }

        User user = null;
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
                    user = userMapper.findByToken(token);
                    //存在则写入Session
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
            //判断用户是否登录
            if (user == null) {
                //返回错误消息到发布页面
                model.addAttribute("erro", "用户未登录");
                return "publish";
            }
        }
        //新建问题容器，把从cookie得到的用户信息导入到容器中
        Question question = new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setGmtCreate(user.getGmtCreate());
        question.setGmtModified(user.getGmtModified());
        //添加到数据库
        questionMapper.create(question);
        //返回主页
        return "redirect:/";
    }
}

