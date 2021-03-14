package zeus.idea.note.entity;

import java.util.List;

/**
 * 类名：NoteEntity
 * 公司：-----智讯云-----
 * 功能说明：
 * 笔记entity
 * <p>
 * 作者：jinyang.wang      创建时间：2020/12/27 8:53
 * <p>
 * 修改人：           修改时间：
 */
public class NoteEntity {
    private String nodeInfoId;//笔记主键id
    private String nodeInfoNo;//笔记编号
    private String nodeInfoTitle;//笔记主题
    private String nodeInfoKeys;//关键字，字符串
    private List<String> nodeInfoKey;//关键字，需要存入另外一张表
    private String nodeInfoContent;//笔记内容
    private String nodeInfoUrl;//笔记记录的url
    private String nodeInfoRemark;//备注
    private String createDate;//创建时间
    private String createUserId;//创建笔记的用户id，表USERSDATA
    private String createUserNo;//创建笔记的用户no，表USERSDATA
    private String updateDate;//修改时间
    private String updateUserId;//修改笔记的用户id，表USERSDATA
    private String updateUserNo;//修改笔记的用户no，表USERSDATA

    public String getNodeInfoKeys() {
        return nodeInfoKeys;
    }

    public void setNodeInfoKeys(String nodeInfoKeys) {
        this.nodeInfoKeys = nodeInfoKeys;
    }

    public List<String> getNodeInfoKey() {
        return nodeInfoKey;
    }

    public void setNodeInfoKey(List<String> nodeInfoKey) {
        this.nodeInfoKey = nodeInfoKey;
    }

    public String getNodeInfoId() {
        return nodeInfoId;
    }

    public void setNodeInfoId(String nodeInfoId) {
        this.nodeInfoId = nodeInfoId;
    }

    public String getNodeInfoNo() {
        return nodeInfoNo;
    }

    public void setNodeInfoNo(String nodeInfoNo) {
        this.nodeInfoNo = nodeInfoNo;
    }

    public String getNodeInfoTitle() {
        return nodeInfoTitle;
    }

    public void setNodeInfoTitle(String nodeInfoTitle) {
        this.nodeInfoTitle = nodeInfoTitle;
    }

    public String getNodeInfoContent() {
        return nodeInfoContent;
    }

    public void setNodeInfoContent(String nodeInfoContent) {
        this.nodeInfoContent = nodeInfoContent;
    }

    public String getNodeInfoUrl() {
        return nodeInfoUrl;
    }

    public void setNodeInfoUrl(String nodeInfoUrl) {
        this.nodeInfoUrl = nodeInfoUrl;
    }

    public String getNodeInfoRemark() {
        return nodeInfoRemark;
    }

    public void setNodeInfoRemark(String nodeInfoRemark) {
        this.nodeInfoRemark = nodeInfoRemark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserNo() {
        return createUserNo;
    }

    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserNo() {
        return updateUserNo;
    }

    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }
}
