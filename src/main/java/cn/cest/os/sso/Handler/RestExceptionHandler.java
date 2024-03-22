package cn.cest.os.sso.Handler;


import cn.cest.os.sso.Enum.CodeEnum;
import cn.cest.os.sso.Exception.NoGetExecption;
import cn.cest.os.sso.Util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@ResponseBody
public class RestExceptionHandler {

    /**
     * 处理自定义 获取数据异常
     *
     * @param e BusinessException
     * @return
     */
    @ExceptionHandler(NoGetExecption.class)
    public Result<String> businessException(NoGetExecption e) {
        log.error("查询数据异常 code={}, BusinessException = {}", e.getCode(), e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMsg());
    }




    /**
     * 处理其他异常
     *
     * @param e otherException
     * @return
     */
//    @ExceptionHandler(Exception.class)
//    public Result<String> exception(Exception e) {
//        log.error("未知异常 exception = {}", e.getMessage(), e);
//        return Result.fail(CodeEnum.RC500.getCode(), CodeEnum.RC500.getMsg());
//    }

}
