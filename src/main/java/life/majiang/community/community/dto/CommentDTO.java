package life.majiang.community.community.dto;

import life.majiang.community.community.Entity.User;
import lombok.Data;


/**
 * @auther huang
 * @date 2020/1/16 17:14
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
    private User user;
    private Long likeCount;
    private Long gmtCreate;
    private Long gmtModified;
}
