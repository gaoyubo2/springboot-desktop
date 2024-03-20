package cn.cest.os.sso.Service;

import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.desktop.AppModel;
import cn.cest.os.sso.pojo.desktop.MemberAppModel;
import cn.cest.os.sso.pojo.desktop.MemberModel;

import java.util.List;

public interface DesktopService {
    AppModel getAppModelById(Integer appId);
    List<AppModel> getApps();
//    List<MemberModel> getRoles();
    Boolean addMemberAppModel(MemberAppModel memberAppModel);

    Boolean addMemberModel(MemberModel memberModel);

    Boolean updateMemberByUserName(MemberModel memberModel);

}
