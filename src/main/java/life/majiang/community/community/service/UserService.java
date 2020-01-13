package life.majiang.community.community.service;

import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huang on 2020-01-13.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 判断用户是否存在
     * @param user
     */
    public void editOrAddUser(User user) {
        //从数据库效验数据是否存在
       User dbUser = userMapper.selectByAccountId(user.getAccountId());
       //用户不存在
       if(dbUser == null){
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           //用户数据插入数据库
           userMapper.insert(user);
       }else{
           //用户存在,更新用户信息
           dbUser.setGmtUpdateData(System.currentTimeMillis());
           dbUser.setAvatarUrl(user.getAvatarUrl());
           dbUser.setName(user.getName());
           dbUser.setToken(user.getToken());
           //更更新
           userMapper.updateUser(dbUser);
       }

    }
}
