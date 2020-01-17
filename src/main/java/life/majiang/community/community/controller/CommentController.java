package life.majiang.community.community.controller;

import life.majiang.community.community.Entity.Comment;
import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Mapper.CommentMapper;
import life.majiang.community.community.dto.CommentDTO;
import life.majiang.community.community.dto.ResultDTO;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther huang
 * @date 2020/1/16 16:28
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 添加回复
     * @param commentDTO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object postComment(@RequestBody  CommentDTO commentDTO,
                              HttpServletRequest request){
        //登录验证
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        //新建回复容器，同时载入 从前端 传过来的JSON 反序列的 CommentDTO
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentor(1L);
        comment.setLikeCount(0L);
        //插入到数据库
        commentService.addComment(comment);
        return ResultDTO.okOf();
    }
}
