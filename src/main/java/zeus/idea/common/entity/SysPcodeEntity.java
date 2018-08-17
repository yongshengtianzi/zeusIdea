package zeus.idea.common.entity;

/**
 * 类名：查询数据字典模型层
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-03-03 8:33
 * <p>
 * 修改人：           修改时间：
 */
public class SysPcodeEntity {
    private String codeId; //主键id
    private String codeSortId; //数据字典分类主键id
    private String pCode; //上级代码的代码标识，用于支持多级代码
    private String codeType; //上级代码分类的代码分类标识
    private String codeValue; //数据字典值
    private String codeName; //数据字典中文
    private String dispSn; //显示顺序
    private String content1; //保留字段

    private String sortName; //数据字典分类名称
    private String maintTypeCode; //代码分类的维护类型，用于控制代码的统一性，01 不可维护、02 只可增加、03 可增删改。例如，一个代码是二级代码，省公司要求一级代码必须全省统一，二级代码可以进行增加。
    private String vn; //数据字典版本
    private String validFlag; //是否有效

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeSortId() {
        return codeSortId;
    }

    public void setCodeSortId(String codeSortId) {
        this.codeSortId = codeSortId;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDispSn() {
        return dispSn;
    }

    public void setDispSn(String dispSn) {
        this.dispSn = dispSn;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getMaintTypeCode() {
        return maintTypeCode;
    }

    public void setMaintTypeCode(String maintTypeCode) {
        this.maintTypeCode = maintTypeCode;
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }
}
