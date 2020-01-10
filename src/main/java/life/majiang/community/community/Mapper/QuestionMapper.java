package life.majiang.community.community.Mapper;

import life.majiang.community.community.Entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther huang
 * @date 2020/1/10 10:01
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,creator,comment_count,view_count,like_count,tag)" +
            "values(#{title},#{description},#{gmtCreate},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);
}
