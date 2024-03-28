package cn.cest.os.sso.service;

import cn.cest.os.sso.utils.Result;
import cn.cest.os.sso.pojo.desktop.AppModel;
import cn.cest.os.sso.pojo.desktop.MemberAppModel;
import cn.cest.os.sso.pojo.desktop.MemberModel;
import cn.cest.os.sso.pojo.vo.AppVO;
import cn.cest.os.sso.pojo.vo.RoleWithAppsNoChildVO;

import java.util.List;

public interface DesktopService {
    AppModel getAppModelById(Integer appId);
    List<AppModel> getApps();
//    List<MemberModel> getRoles();
    Boolean addMemberAppModel(MemberAppModel memberAppModel);

    Boolean addMemberModel(MemberModel memberModel);

    Boolean updateMemberByUserName(MemberModel memberModel);

    Boolean modifyRoleApp(RoleWithAppsNoChildVO roleWithAppsNoChildVO);

    Boolean updateUsername(String originName, String username);

    Result<List<AppVO>> getAppList();
}
