package cn.cest.os.sso.pojo.vo;

import cn.cest.os.sso.pojo.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleTreeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer roleTreeId;
    private String roleTreeName;


    private List<RoleTreeVO> child; // 子角色列表，用于实现二级展开列表
}
