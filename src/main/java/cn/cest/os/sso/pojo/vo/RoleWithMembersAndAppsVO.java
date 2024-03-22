package cn.cest.os.sso.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RoleWithMembersAndAppsVO {
    private Integer roleId;
    private List<Integer> appList;
    private List<String> usernameList;
}
