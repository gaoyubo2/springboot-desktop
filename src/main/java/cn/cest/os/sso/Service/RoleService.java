package cn.cest.os.sso.Service;

import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.result.PageResult;
import cn.cest.os.sso.pojo.vo.RoleTreeVO;
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
public interface RoleService extends IService<Role> {

    boolean deleteUserAndRole(List<Integer> roleIds);

    List<RoleTreeVO> showRoles();

    //List<RoleTreeVO> findChildrenRoles(Integer parentId);

    PageResult selectByName(String roleName, Integer pageNum, Integer pageSize);

    List<Role> getEnableRoles();

    boolean enableRole(Integer roleId, Integer isDelete);

    Boolean updateByRole(Integer roleId, String name);
}
