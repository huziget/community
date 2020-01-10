package life.majiang.community.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private String client_id;
    private String redirect_uri;
    private String login;
    private String scope;
    private String state;
    private String Code;
    private String client_secret;
}
