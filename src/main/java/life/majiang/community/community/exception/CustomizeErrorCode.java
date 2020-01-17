package life.majiang.community.community.exception;

/**
 * @auther huang
 * @date 2020/1/16 10:23
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    //定义一个枚举，实现icustomizeErrorCode接口
    QUESTION_NOT_FOUND(2001,"你要寻找的问题不存在或者已删除，请从新尝试。"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NOT_LOGIN(2003,"当前操作需要登录，请操作后重试"),
    SYS_ERROR(2004,"服务器炸了,攻城狮正在修复....."),
    TYPE_PARAM_WRONG(2005,"评论类型错误,或者不存在"),
    COMMENT_NOT_FOUND(2006,"回复评论不存在，或者已删除。" )
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;

    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
