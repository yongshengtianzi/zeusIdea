package zeus.idea.filter.entity;

import java.util.List;
import java.util.Map;

/**
 * 类名：用于用户认证权限的用户信息entity
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-04-18 18:32
 * <p>
 * 修改人：           修改时间：
 */
public class FilterUserEntity {
    private String userId;//用户ID
    private String userNo;//用户编号
    private String phoneNo;//手机号
    private String email;//邮箱
    private String password;//密码
    private String coolName;//用户昵称
    private String userStatue;//账号状态，0锁定、1正常
    private String ip;//当前登录人员的ip地址
    private String loginResult;//登录结果，0失败、1成功
    private String remark;//备注
    private String roles;//角色
    private List<Map<String, String>> urls;


    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserStatue() {
        return userStatue;
    }

    public void setUserStatue(String userStatue) {
        this.userStatue = userStatue;
    }

    public String getCoolName() {
        return coolName;
    }

    public void setCoolName(String coolName) {
        this.coolName = coolName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
