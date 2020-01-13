package life.majiang.community.community.Mapper;

import life.majiang.community.community.Entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @auther huang
 * @date 2020/1/10 10:01
 */
@Mapper
public interface QuestionMapper {

    /**
     * 发布问题到数据
     * @param question
     */
    @Insert("insert into question (title,description,gmt_create,creator,comment_count,view_count,like_count,tag)" +
            "values(#{title},#{description},#{gmtCreate},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);

    /**
     * 查询所有问题
     * @param offset
     * @param size
     * @return
     */
    @Select("select * from question limit #{offset},#{size}")
    List<Question> List(@Param("offset")Integer offset, @Param("size")Integer size);

    /**
     * 查询问题的总条数
     * @return
     */
    @Select("select count(1) from question")
    Integer selectQuestionCount();

    /**
     * 个人问题查询
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> selectOurselvesQuestions(@Param("userId") Integer userId, @Param("offset")Integer offset, @Param("size")Integer size);
    /**
     * 查询个人问题的总条数
     * @return
     * @param userId
     */
    @Select("select count(1) from question where creator = #{userId}")
    Integer selectOurselvesQuestionCount(Integer userId);
}
