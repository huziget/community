package life.majiang.community.community.controller;

import life.majiang.community.community.dto.PaginationDTO;
import life.majiang.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * created by huang on 2020/1/7
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    /**
     * 主页面处理，免登陆验证COOKIR和SESSION
     * @param
     * @return
     */
    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        //获取问题列表
        PaginationDTO pagination = questionService.searchQuestionList(page, size);
        //添加到MODEL，传给前端
        model.addAttribute("pagination", pagination);
        //去往主页
        return "index";
    }
}
