package com.example.wechat_pay;

import com.example.wechat_pay.service.WXPayService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WxPayTest {
    @Autowired
    private WXPayService wxPayService;
    @Autowired
    private HttpServletRequest request;

    @Test
    public void pay() {
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setBody("测试支付");
            orderRequest.setOutTradeNo(UUID.randomUUID().toString().substring(0, 16));
            orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen("100"));//元转成分
            orderRequest.setSpbillCreateIp("192.168.0.1");
            orderRequest.setProductId("13657");

            WxPayUnifiedOrderResult result = wxPayService.unifiedOrder(orderRequest);
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
    }

    @Test
    public void Wxnotify(){
        String xmlResult = null;
        try {
            xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = wxPayService.getService().parseOrderNotifyResult(xmlResult);
            // 结果正确
            String orderId = result.getOutTradeNo();
            String tradeNo = result.getTransactionId();
            String totalFee = BaseWxPayResult.fenToYuan(result.getTotalFee());
            //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
        } catch (IOException | WxPayException e) {
            e.printStackTrace();
        }

    }
}
