package life.majiang.community.community.dto;

import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import lombok.Data;

/**
 * @auther huang
 * @date 2020/1/16 18:24
 */
@Data
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
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    /**
     * 接口返回错误信息 对接的是customizeErrorCode
     * @param errorCode
     * @return
     */
    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    /**
     * 参数是EXCEPTION的errorOf接口
     * @param ex
     * @return
     */
    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }

    /**
     * 请求成功
     * @return
     */
    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

}
