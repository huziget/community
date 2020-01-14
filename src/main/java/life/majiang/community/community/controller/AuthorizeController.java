package life.majiang.community.community.controller;

import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Mapper.UserMapper;
import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GitUser;
import life.majiang.community.community.provider.GitHubProvider;
import life.majiang.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * created by huang on 2020/01/07
 */

@Controller
public class AuthorizeController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.url}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    /**
     *登陆后跳转功能
     * @param code
     * @param sate
     * @param response
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String sate,HttpServletResponse response){
        //保存第一次从github,接收的信息
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(sate);
        //信息第二次和github效验，交互
        String accessToken = gitHubProvider.getAccessTokenDTO(accessTokenDTO);
        //第三次交互，获取到github用户的信息
        GitUser gitUser = gitHubProvider.getGitUser(accessToken);
        if(gitUser!=null && gitUser.getId() != null){
            //通过实体USER接受用户信息
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitUser.getName());
            user.setAvatarUrl(gitUser.getAvatarUrl());
            user.setAccountId(String.valueOf(gitUser.getId()));
            //保存到数据库
            userService.editOrAddUser(user);
            //信息存到本地浏览器
            response.addCookie(new Cookie("token",token));
            //跳转页面
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request,HttpServletResponse response){
        //在session中移除user
        request.getSession().removeAttribute("user");
        //新建同名的null token
        Cookie cookie = new Cookie("token", null);
        //设置最大的存在时间
        cookie.setMaxAge(0);
        //放入响应中
        response.addCookie(cookie);
        //返回主页
        return "redirect:/";
    }
}
