package com.example.wechat_pay.config;


import com.github.binarywang.wxpay.config.WxPayConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WXPayConfig {
    @Bean
    public WxPayConfig getConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setMchId("");
        payConfig.setMchKey("");
        payConfig.setSubMchId("");
        payConfig.setNotifyUrl("");
        payConfig.setTradeType("NATIVE");
        payConfig.setAppId("");
        payConfig.setMchId("");
        return payConfig;
    }
}
