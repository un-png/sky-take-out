package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    public User wxLogin(UserLoginDTO userLoginDTO) {
        //1.调用wx接口，获取微信用户信息
        String openid= getOPenid(userLoginDTO.getCode());
        //2.判断openid是否为空，为空表示登录失败，抛出业务层异常
        if(openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //3.判断用户是否存在，不存在则自动注册
        User user = userMapper.getByOpenid(openid);
        if (user==null) {
            user = user.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    /**
     * 调用微信接口，获取openid
     * @param code
     * @return
     */
    private String getOPenid(String code){
        Map<String, String> map=new HashMap<>() ;
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, map);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String openid=jsonObject.getString("openid");
        return openid;
    }
}
