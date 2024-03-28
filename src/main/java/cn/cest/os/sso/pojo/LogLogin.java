package cn.cest.os.sso.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LogLogin {
    @TableId(value = "tbid", type = IdType.AUTO)
    private Integer tbid;
    private String username;
    private String ip;
    private LocalDateTime loginTime;
    private Integer success;
    private String failReason;
}
