package com.example.wechat_pay.service;

import com.example.wechat_pay.config.WXPayConfig;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class WXPayService {
    @Resource
    private WXPayConfig wxPayConfig;


    public WxPayService getService() {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig.getConfig());
        return wxPayService;
    }

    public void createOrder(WxPayUnifiedOrderRequest requestParam) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig.getConfig());
        try {
            wxPayService.createOrder(requestParam);
        } catch (WxPayException e) {
            log.error(e.getMessage());
        }
    }

    public WxPayUnifiedOrderResult unifiedOrder(WxPayUnifiedOrderRequest requestParam) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig.getConfig());
        try {
            return wxPayService.unifiedOrder(requestParam);
        } catch (WxPayException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
