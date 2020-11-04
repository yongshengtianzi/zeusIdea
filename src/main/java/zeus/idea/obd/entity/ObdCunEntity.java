package zeus.idea.obd.entity;

/**
 * 类名：ObdCunEntity
 * 公司：-----智讯云-----
 * 功能说明：
 * 当日预约名额数
 * <p>
 * 作者：jinyang.wang      创建时间：2020/11/3 10:46
 * <p>
 * 修改人：           修改时间：
 */
public class ObdCunEntity {
    private String cunQux;//区县名称
    private String cunDate;//日期
    private String cunNum;//可预约数

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
}
