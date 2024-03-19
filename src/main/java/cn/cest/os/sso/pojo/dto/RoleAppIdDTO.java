package cn.cest.os.sso.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleAppIdDTO {
    private Integer roleId;
    @TableField("name")
    private String name;;
    private List<Integer> appIdList;
}
