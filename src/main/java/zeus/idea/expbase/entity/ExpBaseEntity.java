package zeus.idea.expbase.entity;

/**
 * 类名：ExpBaseEntity
 * 公司：-----智讯云-----
 * 功能说明：个人花费实体类
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-02-12 9:54
 * <p>
 * 修改人：           修改时间：
 */
public class ExpBaseEntity {
    private String expId; //主键ID
    private String expNo; //花销编号
    private String expDate; //发生日期
    private String expTime; //发生时间
    private String expType; //花销分类
    private String expMoney; //花销金额，打折前金额
    private String expDisMoney; //优惠金额，便宜了多少钱
    private String expSource; //支出来源
    private String expMark; //备注
    private String expUserNo; //花费人
    private String operationDate; //操作时间
    private String operatorUser; //操作人
    private String expDateStart; //起始日期，用于前台的查询条件
    private String expDateEnd; //截止日期，用于前台的查询条件

    /*分页内容*/
    private String start; //起始位置，第几页
    private String length; //条数
    private String draw; //请求次数

    public String getExpNo() {
        return expNo;
    }

    public void setExpNo(String expNo) {
        this.expNo = expNo;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getExpDateStart() {
        return expDateStart;
    }

    public void setExpDateStart(String expDateStart) {
        this.expDateStart = expDateStart;
    }

    public String getExpDateEnd() {
        return expDateEnd;
    }

    public void setExpDateEnd(String expDateEnd) {
        this.expDateEnd = expDateEnd;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getExpMoney() {
        return expMoney;
    }

    public void setExpMoney(String expMoney) {
        this.expMoney = expMoney;
    }

    public String getExpDisMoney() {
        return expDisMoney;
    }

    public void setExpDisMoney(String expDisMoney) {
        this.expDisMoney = expDisMoney;
    }

    public String getExpSource() {
        return expSource;
    }

    public void setExpSource(String expSource) {
        this.expSource = expSource;
    }

    public String getExpMark() {
        return expMark;
    }

    public void setExpMark(String expMark) {
        this.expMark = expMark;
    }

    public String getExpUserNo() {
        return expUserNo;
    }

    public void setExpUserNo(String expUserNo) {
        this.expUserNo = expUserNo;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperatorUser() {
        return operatorUser;
    }

    public void setOperatorUser(String operatorUser) {
        this.operatorUser = operatorUser;
    }
}
