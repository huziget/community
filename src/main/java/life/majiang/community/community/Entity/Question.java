package life.majiang.community.community.Entity;

import lombok.Data;

/**
 * @auther huang
 * @date 2020/1/10 10:07
 */
@Data
public class Question {
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
    private Long gmtUpdateData;
}
