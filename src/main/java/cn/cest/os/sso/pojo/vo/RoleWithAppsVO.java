package cn.cest.os.sso.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author gaoyubo
 * @since 2024-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleWithAppsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tbid", type = IdType.AUTO)
    private Integer tbid;

    private String name;
    private String createTime;
    private String updateTime;
    private Integer isDelete;
    private List<AppWithChildVO> apps;


}
