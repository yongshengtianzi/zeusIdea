package zeus.idea.dbhandle.entity;

/**
 * 类名：表字段实体类entity
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-03-26 09:56
 * <p>
 * 修改人：           修改时间：
 */
public class TableColEntity {
    private String colName;//字段英文名称
    private String colType;//字段类型
    private String colLen;//长度
    private String colIsNull;//是否运行为空
    private String colDefault;//默认值
    private String isKey;//是否是主键
    private String desc;//备注

    public String getIsKey() {
        return isKey;
    }

    public void setIsKey(String isKey) {
        this.isKey = isKey;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    public String getColLen() {
        return colLen;
    }

    public void setColLen(String colLen) {
        this.colLen = colLen;
    }

    public String getColIsNull() {
        return colIsNull;
    }

    public void setColIsNull(String colIsNull) {
        this.colIsNull = colIsNull;
    }

    public String getColDefault() {
        return colDefault;
    }

    public void setColDefault(String colDefault) {
        this.colDefault = colDefault;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
}
