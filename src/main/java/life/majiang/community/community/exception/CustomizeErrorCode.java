package life.majiang.community.community.exception;

/**
 * @auther huang
 * @date 2020/1/16 10:23
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你要寻找的问题不存在或者已删除，请从新尝试下吧....");

    @Override
    public String getMessage() {
        return message;
    }

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
