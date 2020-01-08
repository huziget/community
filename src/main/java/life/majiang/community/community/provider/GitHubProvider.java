package life.majiang.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GitUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * created by huang on 2020/1/7
 */
@Component
public class GitHubProvider {
    public String getAccessTokenDTO(AccessTokenDTO accessTokenDTO){
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String tokenStr = string.split("&")[0];
                String token = tokenStr.split("=")[1];
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
        }

        public GitUser getGitUser(String accessToken){
            OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://api.github.com/user?access_token="+accessToken)
                        .build();
            try {
                Response response = client.newCall(request).execute();
                String string = response.body().string();
                GitUser gitUser = JSON.parseObject(string, GitUser.class);
                return gitUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

