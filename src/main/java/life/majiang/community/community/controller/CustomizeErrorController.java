package life.majiang.community.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @auther huang
 * @date 2020/1/16 11:36
 */
@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class CustomizeErrorController implements ErrorController{

    @Override
    public String getErrorPath() {
        return "error";
    }

    /**
     * 错误页面处理
     */
    @RequestMapping(
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model) {
        //获取连接状态
        HttpStatus status = this.getStatus(request);
        //4xx请求问题
        if(status.is4xxClientError()){
            model.addAttribute("message","你访问的页面不存在，请确认访问链接是否正确!!!");
        }
        //5xx客户端问题
        if(status.is5xxServerError()){
            model.addAttribute("message","服务器好像炸了，请稍等，正在通知工程师维修.......");
        }
        //转到错误页面
        return  new ModelAndView("error");
    }

    /**
     * 查看状态
     * @param request
     * @return
     */
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
