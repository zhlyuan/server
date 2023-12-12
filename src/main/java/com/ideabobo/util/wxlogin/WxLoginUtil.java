package com.ideabobo.util.wxlogin;

import cn.hutool.http.*;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.util.HashMap;

public class WxLoginUtil {
    /**
     * 请求微信接口服务，获取小程序全局唯一后台接口调用凭据（access_token）
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/access-token/auth.getAccessToken.html
     *
     * @param appid
     * @param secretKey
     * @return
     */
    public static JSONObject getAccessToken(String appid, String secretKey) {
        String result = null;
        try {
            String baseUrl = "https://api.weixin.qq.com/cgi-bin/token";
            HashMap<String, Object> requestParam = new HashMap<>();
            // 小程序 appId
            requestParam.put("grant_type", "client_credential");
            // 小程序唯一凭证id
            requestParam.put("appid", appid);
            // 小程序 appSecret（小程序的唯一凭证密钥）
            requestParam.put("secret", secretKey);
            // 发送GET请求读取调用微信接口获取openid用户唯一标识
            result = HttpUtil.get(baseUrl, requestParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONUtil.parseObj(result);
    }

    /**
     * 请求微信接口服务，用code换取用户手机号（每个code只能使用一次，code的有效期为5min）
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/phonenumber/phonenumber.getPhoneNumber.html
     *
     * @param code
     * @param accessToken
     * @return
     */
    public static JSONObject getPhoneNumber(String code, String accessToken) {
        String result = null;
        try {
            // 接口调用凭证：accessToken
            String baseUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
            HashMap<String, Object> requestParam = new HashMap<>();
            // 手机号调用凭证
            requestParam.put("code", code);
            // 发送post请求读取调用微信接口获取openid用户唯一标识
            String jsonStr = JSONUtil.toJsonStr(requestParam);
            HttpResponse response = HttpRequest.post(baseUrl)
                    .header(Header.CONTENT_ENCODING, "UTF-8")
                    // 发送json数据需要设置contentType
                    .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
                    .body(jsonStr)
                    .execute();
            if (response.getStatus() == HttpStatus.HTTP_OK) {
                result = response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONUtil.parseObj(result);
    }

}
