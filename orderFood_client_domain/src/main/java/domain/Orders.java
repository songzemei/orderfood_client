package domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class Orders {
    private String id;//uuid
    private Date orderTime;//下单时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String orderTimeStr;//下单时间字符串
    private int orderStatus;//订单状态
    private String orderStatusStr;//订单状态字符串
    private String orderDesc;//订单备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTimeStr() {
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusStr() {
        return orderStatus==1?"已支付":"未支付";
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }



    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }


}
