package life.majiang.community.community.service;

import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Entity.UserExample;
import life.majiang.community.community.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
       //用户不存在
       if(users.size() == 0){
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           //用户数据插入数据库
           userMapper.insert(user);
       }else{
           //用户存在,更新用户信息
           User dbUser = users.get(0);
           //更更新
           User updateUser = new User();
           updateUser.setGmtUpdatedata(System.currentTimeMillis());
           updateUser.setAvatarUrl(user.getAvatarUrl());
           updateUser.setName(user.getName());
           updateUser.setToken(user.getToken());
           UserExample example = new UserExample();
           example.createCriteria().andIdEqualTo(dbUser.getId());
           userMapper.updateByExampleSelective(updateUser,example);
       }

    }
}
