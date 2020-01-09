package life.majiang.community.community.controller;

import life.majiang.community.community.Entity.User;
import life.majiang.community.community.Mapper.UserMapper;
import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GitUser;
import life.majiang.community.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String sate, HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(sate);

        String accessToken = gitHubProvider.getAccessTokenDTO(accessTokenDTO);
        GitUser gitUser = gitHubProvider.getGitUser(accessToken);
        if(gitUser!=null){
            //通过实体USER接受用户信息
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitUser.getName());
            user.setAccountId(String.valueOf(gitUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //保存到数据库
            userMapper.insert(user);
            //登录成功
            request.getSession().setAttribute("user",gitUser);
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }
}
