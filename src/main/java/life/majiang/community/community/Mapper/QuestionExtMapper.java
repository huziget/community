package life.majiang.community.community.Mapper;

import life.majiang.community.community.Entity.Question;
import life.majiang.community.community.Entity.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}