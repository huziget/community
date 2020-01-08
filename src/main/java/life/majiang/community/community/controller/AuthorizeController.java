package life.majiang.community.community.controller;

import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GitUser;
import life.majiang.community.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String sate){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("1e15e2901e9c2ad86816");
        accessTokenDTO.setClient_secret("dc084c0ddbe22dcbe5765252fd198cd1a42ae1f2");
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(sate);
        String accessToken = gitHubProvider.getAccessTokenDTO(accessTokenDTO);
        GitUser user = gitHubProvider.getGitUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
