package cn.cest.os.sso.pojo;

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
    private Integer tbid;
    private String username;
    private String ip;
    private LocalDateTime loginTime;
    private Integer success;
    private String failReason;
}
