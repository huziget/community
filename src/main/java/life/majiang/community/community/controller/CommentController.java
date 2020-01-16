package life.majiang.community.community.controller;

import life.majiang.community.community.Entity.Comment;
import life.majiang.community.community.Mapper.CommentMapper;
import life.majiang.community.community.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther huang
 * @date 2020/1/16 16:28
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;


    /**
     * 添加回复
     * @param commentDTO
     * @return
     */
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object postComment(@RequestBody  CommentDTO commentDTO){
        //新建回复容器，同时载入 从前端 传过来的JSON 反序列的 CommentDTO
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentor(1);
        comment.setLikeCount(0L);
        //插入到数据库
        commentMapper.insert(comment);
        //跳转到主页
        return null;
    }
}
