package life.majiang.community.community.advice;

import com.alibaba.fastjson.JSON;
import life.majiang.community.community.dto.ResultDTO;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @auther huang
 * @date 2020/1/15 17:38
 */
@ControllerAdvice
public class CustomizeEexcptionHandler {

    /**
     * 异常处理器
     */
    @ExceptionHandler(Exception.class)
    ModelAndView handler(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        //获取请求的格式
        String contentType = request.getContentType();
        //判断返回值是不是json格式
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO = null;
            //返回Json
            if (ex instanceof CustomizeException) {
                //返回我们定义的异常的信息
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
                //返回通用的信息
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            //暴力手写JSON,输出到前端
            PrintWriter writer = null;
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
            }
            return null;
        } else {
            //错误页面跳转
            //判断穿过来的异常是不是我们定义的
            if (ex instanceof CustomizeException) {
                //返回我们定义的异常的信息
                model.addAttribute("message", ex.getMessage());
            } else {
                //返回通用的信息
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR);
            }
            //跳转到错误提示页
            return new ModelAndView("error");
        }
    }
}
