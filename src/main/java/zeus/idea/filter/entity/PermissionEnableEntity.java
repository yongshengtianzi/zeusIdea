package zeus.idea.filter.entity;

/**
 * 类名：权限关系数据entity
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-04-16 17:39
 * <p>
 * 修改人：           修改时间：
 */
public class PermissionEnableEntity {
    private String urlId;//url主键id
    private String parentUrlId;//url父级主键id
    private String urlName;//url中文名称
    private String urlHref;//url
    private String sort;//url排序字段
    private String roleId;//角色主键id
    private String enName;//角色英文名称，全表唯一
    private String zhName;//角色中文名称
    private String relationType;//关系类型，role角色、perms权限等
    private String menuName;//菜单名称
    private String isMenu;//是否菜单，1是，0否

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(String isMenu) {
        this.isMenu = isMenu;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getParentUrlId() {
        return parentUrlId;
    }

    public void setParentUrlId(String parentUrlId) {
        this.parentUrlId = parentUrlId;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrlHref() {
        return urlHref;
    }

    public void setUrlHref(String urlHref) {
        this.urlHref = urlHref;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }
}
