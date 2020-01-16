package life.majiang.community.community.advice;

import life.majiang.community.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


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
    ModelAndView handler(Throwable ex, Model model) {
        //判断穿过来的异常是不是我们定义的
        if (ex instanceof CustomizeException) {
            //返回我们定义的异常的信息
            model.addAttribute("message", ex.getMessage());
        } else {
            //返回通用的信息
            model.addAttribute("message", "服务器炸了,攻城狮正在修复.....");
        }
        //跳转到错误提示页
        return new ModelAndView("error");
    }
}
