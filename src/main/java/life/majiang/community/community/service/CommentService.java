package life.majiang.community.community.service;

import life.majiang.community.community.Entity.Comment;
import life.majiang.community.community.Entity.Question;
import life.majiang.community.community.Mapper.CommentMapper;
import life.majiang.community.community.Mapper.QuestionExtMapper;
import life.majiang.community.community.Mapper.QuestionMapper;
import life.majiang.community.community.enums.CommentTypeEnum;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther huang
 * @date 2020/1/17 10:25
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    /**
     * 插入逻辑处理
     *
     * @param comment
     */
    public void addComment(Comment comment) {
        //过去回复人的ID。如果为null就报错
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        //判断回复类型是否正确或者存在
        if (comment.getType() == null || !CommentTypeEnum.isExst(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //如果回复类型是comment(回复评论)，向数据库查询
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                //数据为空，评论不存在。
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //添加评论到数据库
            commentMapper.insert(comment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                //如果查不到底问题
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //添加评论到数据库
            commentMapper.insert(comment);
            question.setGmtUpdatedata(System.currentTimeMillis());
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }

    }
}
