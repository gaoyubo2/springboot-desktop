package cn.cest.os.sso.Service;

import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.pojo.result.PageResult;
import cn.cest.os.sso.pojo.vo.UserInfoVO;
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
public interface UserService extends IService<User> {

    void extracted(List<UserInfoVO> userInfoVOList, User user);

    Boolean addUserAndMember(User user);

    Boolean changeUserAndMember(User user);

    PageResult search(Integer pageNum, Integer pageSize, String rolename, String username);

    Boolean deleteusers(List<Integer> uids);

    Boolean enableUser(Integer userId, Integer isDelete);
}
