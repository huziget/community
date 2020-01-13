package life.majiang.community.community.Mapper;


import life.majiang.community.community.Entity.User;
import org.apache.ibatis.annotations.*;

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

    /**
     * 根据Id查找用户信息
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    /**
     * 根据AccountId 效验数据库是否存在
     * @param accountId
     * @return
     */
    @Select("select * from user where ACCOUNT_ID = #{accountId}")
    User selectByAccountId(@Param("accountId") String accountId);

    /**
     * 更新数据
     * @param dbUser
     */
    @Update("update user set name = #{name},token = #{token},GMT_UPDATEDATA = #{gmtUpdateData},AVATAR_URL = #{avatarUrl} where ACCOUNT_ID = #{accountId}")
    void updateUser(User dbUser);
}
