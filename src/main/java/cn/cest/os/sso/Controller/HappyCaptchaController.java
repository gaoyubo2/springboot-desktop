package cn.cest.os.sso.Controller;

import cn.cest.os.sso.Util.Result;
import com.ramostear.captcha.HappyCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date 2024/3/19 23:32
 * @Author 郜宇博
 */
@Controller
public class HappyCaptchaController{
    @GetMapping("/getCaptcha")
    public void happyCaptcha(HttpServletRequest request, HttpServletResponse response){
        HappyCaptcha.require(request,response).build().finish();
    }
}