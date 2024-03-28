package cn.cest.os.sso.service.impl;

import cn.cest.os.sso.service.DesktopService;
import cn.cest.os.sso.service.UserService;
import cn.cest.os.sso.utils.Result;
import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.pojo.desktop.AppModel;
import cn.cest.os.sso.pojo.desktop.MemberAppModel;
import cn.cest.os.sso.pojo.desktop.MemberModel;
import cn.cest.os.sso.pojo.vo.AppVO;
import cn.cest.os.sso.pojo.vo.MemberNameVO;
import cn.cest.os.sso.pojo.vo.RoleWithAppsNoChildVO;
import cn.cest.os.sso.pojo.vo.RoleWithMembersAndAppsVO;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class DesktopServiceImpl implements DesktopService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    @Value("${desktop.getAppModelById}")
    private String getAppModelById;

    @Value("${desktop.getApps}")
    private String getApps;

    @Value("${desktop.addMemberAppModel}")
    private String addMemberAppModel;

    @Value("${desktop.addMemberModel}")
    private String addMemberModel;

    @Value("${desktop.updateMemberByUserName}")
    private String updateMemberByUserName;

    @Value("${desktop.modifyRoleApp}")
    private String modifyRoleApp;

    @Value("${desktop.updateMemberName}")
    private String updateMemberName;


    @Override
    public AppModel getAppModelById(Integer appId) {
        String token = StpUtil.getTokenValue();
        // 创建HttpHeaders对象，并设置token到请求头中
        HttpHeaders headers = new HttpHeaders();
        headers.set("token",  token);
        // 构建请求体，可以为空
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<AppModel> responseEntity = restTemplate.exchange(
                getAppModelById,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<AppModel>() {},
                appId
        );
        return responseEntity.getBody();
    }

    @Override
    public List<AppModel> getApps() {

        System.out.println(getApps);
        try {
            String token = StpUtil.getTokenValue();
            // 创建HttpHeaders对象，并设置token到请求头中
            HttpHeaders headers = new HttpHeaders();
            headers.set("token",  token);
            System.out.println("token:"+token);
            // 构建请求体，可以为空
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<List<AppModel>> responseEntity = restTemplate.exchange(getApps, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<AppModel>>() {});
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                List<AppModel> result = responseEntity.getBody();
                if (result != null && !result.isEmpty()) {
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        //return restTemplate.getForObject(getApps, List.class);

    }

    private Boolean addObject(Object object, String Url) {
        try {

            // 将对象转换为 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(object);

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String token = StpUtil.getTokenValue();
            headers.set("token",  token);

            // 设置请求体内容和请求头
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // 发送 POST 请求
            return restTemplate.postForObject(Url, requestEntity, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean addMemberAppModel(MemberAppModel memberAppModel) {
        return addObject(memberAppModel, addMemberAppModel);
    }

    @Override
    public Boolean addMemberModel(MemberModel memberModel) {
        return addObject(memberModel, addMemberModel);
    }

    @Override
    public Boolean updateMemberByUserName(MemberModel memberModel) {
        System.out.println("新的MemberModel:"+memberModel);
        return addObject(memberModel,updateMemberByUserName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyRoleApp(RoleWithAppsNoChildVO role) {
        System.out.println("RoleWithAppsNoChildVO:"+role);
        //获取该角色的username列表
        List<User> users = userService.list(new QueryWrapper<User>().eq("role_id", role.getRoleId()));
        //封装username
        List<String> usernameList = new ArrayList<>(users.size());
        for(User user: users){
            usernameList.add(user.getUsername());
        }
        System.out.println("usernameList:"+usernameList);
        //封装vo类
        RoleWithMembersAndAppsVO roleWithMembersAndAppsVO = new RoleWithMembersAndAppsVO();
        roleWithMembersAndAppsVO.setRoleId(role.getRoleId());
        roleWithMembersAndAppsVO.setUsernameList(usernameList);
        roleWithMembersAndAppsVO.setAppList(role.getAppList());
        System.out.println("roleWithMembersAndAppsVO:"+roleWithMembersAndAppsVO);
        return addObject(roleWithMembersAndAppsVO,modifyRoleApp);
    }

    @Override
    public Boolean updateUsername(String originName, String newName) {
        if (originName.equals(newName)) return true;
        MemberNameVO memberNameVO = new MemberNameVO();
        memberNameVO.setNewName(newName);
        memberNameVO.setOriginName(originName);
        return addObject(memberNameVO,updateMemberName);
    }

    @Override
    public Result<List<AppVO>> getAppList() {
        List<AppModel> apps = this.getApps();
        if(apps.size() == 0) return Result.fail("应用列表为空");
        List<AppVO> apps2 = new ArrayList<>(apps.size());

        for(AppModel appModel:apps){
            AppVO appVO = new AppVO();
            BeanUtils.copyProperties(appModel, appVO);
            apps2.add(appVO);
        }
        return Result.ok(apps2);
    }
}
