package life.majiang.community.community.dto;

/**
 * @auther huang
 * @date 2020/1/16 18:24
 */
public class ResultDTO {
    private Integer code;
    private String message;

    /**
     * 定义一个errorOf返回值验证接口
     * @param code
     * @param message
     * @return
     */
    public static ResultDTO errorOf(Integer code, String message) {
        return new ResultDTO();
    }
}
