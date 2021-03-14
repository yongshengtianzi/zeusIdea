package zeus.idea.note.entity;

/**
 * 类名：NoteKeyEntity
 * 公司：-----智讯云-----
 * 功能说明：
 * 笔记关键字entity
 * <p>
 * 作者：jinyang.wang      创建时间：2020/12/28 20:05
 * <p>
 * 修改人：           修改时间：
 */
public class NoteKeyEntity {
    private String nodeKeyId;//关键字id
    private String nodeKeyName;//关键字（文字），必须限制长度
    private String createDate;//创建时间
    private String createUserId;//创建笔记的用户id，表USERSDATA
    private String createUserNo;//创建笔记的用户no，表USERSDATA
    private String updateDate;//修改时间
    private String updateUserId;//修改笔记的用户id，表USERSDATA
    private String updateUserNo;//修改笔记的用户no，表USERSDATA

    public String getNodeKeyId() {
        return nodeKeyId;
    }

    public void setNodeKeyId(String nodeKeyId) {
        this.nodeKeyId = nodeKeyId;
    }

    public String getNodeKeyName() {
        return nodeKeyName;
    }

    public void setNodeKeyName(String nodeKeyName) {
        this.nodeKeyName = nodeKeyName;
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
