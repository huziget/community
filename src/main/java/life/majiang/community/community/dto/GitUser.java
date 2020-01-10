package life.majiang.community.community.dto;

import lombok.Data;

@Data
public class GitUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
