package life.majiang.community.community.controller;

import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther huang
 * @date 2020/1/13 16:57
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 访问问题详情
     * @param id
     * @param model
     * @return
     */
    @ResponseBody
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,Model model){
        //获取问题详情
        QuestionDTO questionDTO = questionService.serchById(id);
        //每次访问增加浏览次数
        questionService.incView(id);
        //载入model
        model.addAttribute("question",questionDTO);
        //转到question页面
        return "question";
    }
}
