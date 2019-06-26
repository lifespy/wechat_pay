package com.example.wechat_pay.controller;

import com.example.wechat_pay.service.WXPayService;
import com.example.wechat_pay.utils.QrCodeCreateUtil;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class QRCodeController {
    @Resource
    private WXPayService wxPayService;

    @GetMapping("qrCode/{price}")
    public void qrCode(@PathVariable String price, HttpServletResponse response) throws IOException, WriterException {
        WxPayUnifiedOrderResult result = new WxPayUnifiedOrderResult();
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setBody("测试支付");
            orderRequest.setOutTradeNo(UUID.randomUUID().toString().substring(0, 16));
            orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(price));//元转成分
            orderRequest.setSpbillCreateIp("192.168.0.1");
            orderRequest.setProductId("13657");

            result = wxPayService.unifiedOrder(orderRequest);
            log.info(result.getCodeURL());
            log.info(result.getPrepayId());
            log.info(result.getTradeType());
            log.info(result.getResultCode());
            log.info(result.getReturnMsg());
            log.info(result.getNonceStr());
        } catch (Exception e) {
            log.error("微信支付失败！,原因:{}", e.getMessage());
            e.printStackTrace();

        }
        response.setContentType("image/jpeg");
        // 禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        QrCodeCreateUtil.createQrCode(response.getOutputStream(), result.getCodeURL(), 900, "JPEG");
    }
}
