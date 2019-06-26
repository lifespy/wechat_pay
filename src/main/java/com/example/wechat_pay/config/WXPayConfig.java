package com.example.wechat_pay.config;


import com.github.binarywang.wxpay.config.WxPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WXPayConfig {

    @Value("${pay.appid}")
    private String appid;
    @Value("${pay.mchid}")
    private String mchid;
    @Value("${pay.mchkey}")
    private String mchkey;
    @Value("${pay.submchid}")
    private String submchid;
    @Value("${pay.notifyurl}")
    private String notifyurl;

    @Bean
    public WxPayConfig getConfig() {
        WxPayConfig payConfig = new WxPayConfig();

        payConfig.setAppId(appid);
        payConfig.setMchId(mchid);
        payConfig.setMchKey(mchkey);
        payConfig.setSubMchId(submchid);
        payConfig.setNotifyUrl(notifyurl);
        payConfig.setTradeType("NATIVE");
        return payConfig;
    }
}
