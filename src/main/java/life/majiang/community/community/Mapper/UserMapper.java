package life.majiang.community.community.Mapper;


import life.majiang.community.community.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * user用户交互Dao层
 */

@Mapper
public interface UserMapper {

    /**
     * 存用户信息到数据库
     * @param user
     */
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url)values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    /**
     * 从数据库查询用户信息
     * @param token
     * @return
     */
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);
}
