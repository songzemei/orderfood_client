package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Rider {
    private String memberId;//会员id
    private int workStatus;//工作状态 1/0
    private String workStatusStr;//工作状态 1：工作中/0：空闲
    private Date orderTime;//接单时间
    private String orderTimeStr;//接单时间字符串
    private int ordersCount;//接单数量
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(int workStatus) {
        this.workStatus = workStatus;
    }

    public String getWorkStatusStr() {
        return workStatus == 1 ? "送单中" : "空闲";
    }

    public void setWorkStatusStr(String workStatusStr) {
        this.workStatusStr = workStatusStr;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        return sdf.format(orderTime);
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }
}
