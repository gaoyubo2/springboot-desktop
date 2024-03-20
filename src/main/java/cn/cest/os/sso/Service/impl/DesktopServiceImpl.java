package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.desktop.AppModel;
import cn.cest.os.sso.pojo.desktop.MemberAppModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @Override
    public AppModel getAppModelById(Integer appId) {
        return restTemplate.getForObject(getAppModelById, AppModel.class,appId);
    }

    @Override
    public List<AppModel> getApps() {
        return restTemplate.getForObject(getApps, List.class);
    }

    @Override
    public Boolean addMemberAppModel(MemberAppModel memberAppModel) {
        try {
            // 将对象转换为 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(memberAppModel);

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 设置请求体内容和请求头
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // 发送 POST 请求
            return restTemplate.postForObject(addMemberAppModel, requestEntity, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}
