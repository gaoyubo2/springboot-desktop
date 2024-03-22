package cn.cest.os.sso.Service;

import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.pojo.dto.RoleAppIdDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gaoyubo
 * @since 2024-03-15
 */
public interface RoleAppService extends IService<RoleApp> {

    /**
     * 添加角色应用权限
     * @param roleAppIdDTO 角色信息、权限ID列表
     * @return 添加标志
     */
    Boolean addRoleApp(RoleAppIdDTO roleAppIdDTO,Boolean sameName);

    // 比较两个角色的权限信息是否相同
    boolean hasModifyRoleApp(Integer roleId, List<Integer> updatedPermissions);

    List<Integer> getRoleAppByRoleId(Integer roleId);

    Boolean removeAllWithRoleId(Integer roleId);
}
