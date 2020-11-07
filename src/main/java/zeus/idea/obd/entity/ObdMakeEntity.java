package zeus.idea.obd.entity;

import java.util.List;

/**
 * 类名：ObdMakeEntity
 * 公司：-----智讯云-----
 * 功能说明：
 * 预约信息entity
 * <p>
 * 作者：jinyang.wang      创建时间：2020/11/3 10:47
 * <p>
 * 修改人：           修改时间：
 */
public class ObdMakeEntity {
    private String carNum;//车牌号，表主键
    private String delFlag;//是否删除，1未删除、0已删除
    private String makePeople;//预约人
    private String phoneNum;//电话
    private String quX;//区县
    private String anZhDi;//安装点
    private String expTime;//安装日期
    private String expMark;//备注
    private String createTime;//创建时间
    private String createUser;//创建人，用户id
    private String updateTime;//修改时间
    private String updateUser;//修改人，用户id
    private String addr;//地址
    private String tel;//姓名及电话
    private String duoFlag;//批量预约标识，uuid生成

    private List<ObdCarEntity> cars;//车辆信息列表

    public String getDuoFlag() {
        return duoFlag;
    }

    public void setDuoFlag(String duoFlag) {
        this.duoFlag = duoFlag;
    }

    public List<ObdCarEntity> getCars() {
        return cars;
    }

    public void setCars(List<ObdCarEntity> cars) {
        this.cars = cars;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getMakePeople() {
        return makePeople;
    }

    public void setMakePeople(String makePeople) {
        this.makePeople = makePeople;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getQuX() {
        return quX;
    }

    public void setQuX(String quX) {
        this.quX = quX;
    }

    public String getAnZhDi() {
        return anZhDi;
    }

    public void setAnZhDi(String anZhDi) {
        this.anZhDi = anZhDi;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getExpMark() {
        return expMark;
    }

    public void setExpMark(String expMark) {
        this.expMark = expMark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
