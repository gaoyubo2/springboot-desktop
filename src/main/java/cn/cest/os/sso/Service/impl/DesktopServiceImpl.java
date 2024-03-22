package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.pojo.desktop.AppModel;
import cn.cest.os.sso.pojo.desktop.MemberAppModel;
import cn.cest.os.sso.pojo.desktop.MemberModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class DesktopServiceImpl implements DesktopService {

    @Autowired
    private RestTemplate restTemplate;

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

    @Override
    public AppModel getAppModelById(Integer appId) {
        return restTemplate.getForObject(getAppModelById, AppModel.class,appId);
    }

    @Override
    public List<AppModel> getApps() {
        try {
            ResponseEntity<List<AppModel>> responseEntity = restTemplate.exchange(getApps, HttpMethod.GET, null, new ParameterizedTypeReference<List<AppModel>>() {});
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
        return addObject(memberModel,updateMemberByUserName);
    }


}
