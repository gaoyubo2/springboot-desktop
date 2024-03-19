package cn.cest.os.sso.Service;

import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.pojo.dto.RoleAppIdDTO;
import com.baomidou.mybatisplus.extension.service.IService;

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
    Boolean addRoleApp(RoleAppIdDTO roleAppIdDTO);
}
