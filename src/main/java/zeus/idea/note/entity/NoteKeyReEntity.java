package zeus.idea.note.entity;

/**
 * 类名：NoteKeyReEntity
 * 公司：-----智讯云-----
 * 功能说明：
 * 笔记和关键字关联关系entity
 * <p>
 * 作者：jinyang.wang      创建时间：2020/12/30 18:37
 * <p>
 * 修改人：           修改时间：
 */
public class NoteKeyReEntity {
    private String nodeKeyRelationId;//笔记关和关键字关系id
    private String nodeKeyId;//关键字id，必须限制长度
    private String nodeInfoId;//笔记主键id
    private String createDate;//创建时间
    private String createUserId;//创建笔记的用户id，表USERSDATA
    private String createUserNo;//创建笔记的用户no，表USERSDATA
    private String updateDate;//修改时间
    private String updateUserId;//修改笔记的用户id，表USERSDATA
    private String updateUserNo;//修改笔记的用户no，表USERSDATA

    public String getNodeKeyRelationId() {
        return nodeKeyRelationId;
    }

    public void setNodeKeyRelationId(String nodeKeyRelationId) {
        this.nodeKeyRelationId = nodeKeyRelationId;
    }

    public String getNodeKeyId() {
        return nodeKeyId;
    }

    public void setNodeKeyId(String nodeKeyId) {
        this.nodeKeyId = nodeKeyId;
    }

    public String getNodeInfoId() {
        return nodeInfoId;
    }

    public void setNodeInfoId(String nodeInfoId) {
        this.nodeInfoId = nodeInfoId;
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
