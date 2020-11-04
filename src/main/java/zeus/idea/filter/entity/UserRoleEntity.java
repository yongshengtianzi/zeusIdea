package zeus.idea.filter.entity;

/**
 * 类名：用户登录时，用户和角色权限关系实体模型
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-04-17 21:03
 * <p>
 * 修改人：           修改时间：
 */
public class UserRoleEntity {
    private String userNo;//用户编号
    private String relationType;//用户角色权限关系类型，role角色、perms权限等
    private String enName;//角色英文名称

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}
