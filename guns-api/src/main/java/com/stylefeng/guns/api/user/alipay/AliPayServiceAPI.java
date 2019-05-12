package com.stylefeng.guns.api.user.alipay;


import com.stylefeng.guns.api.user.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.api.user.alipay.vo.AliPayResultVO;

public interface AliPayServiceAPI {

    AliPayInfoVO getQRCode(String orderId);

    AliPayResultVO getOrderStatus(String orderId);

}
