package cn.cest.os.sso.Exception;

import cn.cest.os.sso.Enum.CodeEnum;
import lombok.Getter;
import lombok.Setter;

/*
没有获取到数据 异常
 */
@Getter
@Setter
public class NoGetExecption extends RuntimeException{

    private int code;
    private String msg;

    public NoGetExecption() {
    }

    public NoGetExecption(CodeEnum returnCode) {
        this(returnCode.getCode(),returnCode.getMsg());
    }

    public NoGetExecption(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
