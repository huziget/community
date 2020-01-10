package life.majiang.community.community.dto;

import life.majiang.community.community.Entity.User;
import lombok.Data;

/**
 * @auther huang
 * @date 2020/1/10 15:16
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer CommentCount;
    private Integer likeCount;
    private User user;
}
