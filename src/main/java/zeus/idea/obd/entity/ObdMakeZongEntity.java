package zeus.idea.obd.entity;

/**
 * 类名：ObdMakeZongEntity
 * 公司：-----智讯云-----
 * 功能说明：
 * 预约信息汇总
 * <p>
 * 作者：jinyang.wang      创建时间：2020/11/8 8:35
 * <p>
 * 修改人：           修改时间：
 */
public class ObdMakeZongEntity {
    private String cunQux;//区县名称
    private String cunDate;//日期
    private String cunNum;//总预约数
    private String num;//已预约数量
    private String weiNum;//未预约数

    public String getCunQux() {
        return cunQux;
    }

    public void setCunQux(String cunQux) {
        this.cunQux = cunQux;
    }

    public String getCunDate() {
        return cunDate;
    }

    public void setCunDate(String cunDate) {
        this.cunDate = cunDate;
    }

    public String getCunNum() {
        return cunNum;
    }

    public void setCunNum(String cunNum) {
        this.cunNum = cunNum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getWeiNum() {
        return weiNum;
    }

    public void setWeiNum(String weiNum) {
        this.weiNum = weiNum;
    }
}
