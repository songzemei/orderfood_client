package domain;

import java.util.List;

public class Member {
    private String id;//uuid
    private String username;
    private String password;
    private String email;
    private String phoneNum;
    private int active;//激活状态
    private String activeStr;//激活状态字符串 0 未激活/1 激活
    private Double balance;//余额
    private String paycode;//支付密码
    private String headerImg;//头像地址
    private int status;//状态码
    private String statusStr;//状态码字符串 0禁用/1可用
    private List<Role> roles;//会员角色

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getActiveStr() {
        return active==1?"已激活":"未激活";
    }

    public void setActiveStr(String activeStr) {
        this.activeStr = activeStr;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return status==1?"可用":"禁用";
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}
