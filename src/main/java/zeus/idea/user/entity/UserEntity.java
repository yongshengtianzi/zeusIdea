package zeus.idea.user.entity;

/**
 * 类名：用户管理类-entity
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-07-15 18:17
 * <p>
 * 修改人：           修改时间：
 */
public class UserEntity {
    private String userId; //用户标识，此表唯一标识
    private String userRelationId; //用户关系表唯一标识
    private String userNo; //用户编号
    private String coolName; //昵称
    private String phoneNo; //手机号
    private String email; //邮箱
    private String password; //密码
    private String userFirstname; //姓
    private String userName; //名
    private String userGender; //性别，标准代码，男1、女2
    private String userBirthday; //生日，最好能精确到秒
    private String userAge; //年龄	USER_AGE
    private String userConstellation; //星座，标准代码
    private String userBloodType; //血型
    private String userBirthCountry; //故乡所在国家，标准代码
    private String userBirthProvince; //故乡所在省/州，标准代码
    private String userBirthCity; //故乡所在市，标准代码
    private String userBirthCounty; //故乡所在区县，标准代码
    private String userNowCountry; //现所在国家，标准代码
    private String userNowProvince; //现所在省/州，标准代码
    private String userNowCity; //现所在市，标准代码
    private String userNowCounty; //现所在区县，标准代码
    private String userIsCancel; //用户状态，是否注销，1正常、0注销

    private String token; //token值
    private String userIP; //用户IP
    private String recordId; //用户登录记录

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRelationId() {
        return userRelationId;
    }

    public void setUserRelationId(String userRelationId) {
        this.userRelationId = userRelationId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getCoolName() {
        return coolName;
    }

    public void setCoolName(String coolName) {
        this.coolName = coolName;
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

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserConstellation() {
        return userConstellation;
    }

    public void setUserConstellation(String userConstellation) {
        this.userConstellation = userConstellation;
    }

    public String getUserBloodType() {
        return userBloodType;
    }

    public void setUserBloodType(String userBloodType) {
        this.userBloodType = userBloodType;
    }

    public String getUserBirthCountry() {
        return userBirthCountry;
    }

    public void setUserBirthCountry(String userBirthCountry) {
        this.userBirthCountry = userBirthCountry;
    }

    public String getUserBirthProvince() {
        return userBirthProvince;
    }

    public void setUserBirthProvince(String userBirthProvince) {
        this.userBirthProvince = userBirthProvince;
    }

    public String getUserBirthCity() {
        return userBirthCity;
    }

    public void setUserBirthCity(String userBirthCity) {
        this.userBirthCity = userBirthCity;
    }

    public String getUserBirthCounty() {
        return userBirthCounty;
    }

    public void setUserBirthCounty(String userBirthCounty) {
        this.userBirthCounty = userBirthCounty;
    }

    public String getUserNowCountry() {
        return userNowCountry;
    }

    public void setUserNowCountry(String userNowCountry) {
        this.userNowCountry = userNowCountry;
    }

    public String getUserNowProvince() {
        return userNowProvince;
    }

    public void setUserNowProvince(String userNowProvince) {
        this.userNowProvince = userNowProvince;
    }

    public String getUserNowCity() {
        return userNowCity;
    }

    public void setUserNowCity(String userNowCity) {
        this.userNowCity = userNowCity;
    }

    public String getUserNowCounty() {
        return userNowCounty;
    }

    public void setUserNowCounty(String userNowCounty) {
        this.userNowCounty = userNowCounty;
    }

    public String getUserIsCancel() {
        return userIsCancel;
    }

    public void setUserIsCancel(String userIsCancel) {
        this.userIsCancel = userIsCancel;
    }
}
