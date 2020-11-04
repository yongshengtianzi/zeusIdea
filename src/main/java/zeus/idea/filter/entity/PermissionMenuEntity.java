package zeus.idea.filter.entity;

import java.util.List;

/**
 * 类名：PermissionMenuEntity
 * 公司：-----智讯云-----
 * 功能说明：菜单entity
 *
 * <p>
 * 作者：jinyang.wang      创建时间：2020/11/2 20:39
 * <p>
 * 修改人：           修改时间：
 */
public class PermissionMenuEntity {
    private String menuId;//菜单主键id
    private String path;//路径
    private String component;//要显示的界面
    private String redirect;//重定向
    private String name;//英文名称
    private String metaTitle;//标题名称
    private String metaIcon;//菜单icon
    private String metaHidden;//是否显示，1是，0否
    private String metaNoCache;//是否缓存，1是，0否
    private String parentMenuId;//父级菜单名称
    private String remark;//备注
    private String createTime;//创建时间
    private String createUser;//创建人，用户id
    private String updateTime;//修改时间
    private String updateUser;//修改人，用户id
    private String orderNum;//菜单排序
    private List<PermissionMenuEntity> children;//子集

    public List<PermissionMenuEntity> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionMenuEntity> children) {
        this.children = children;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaIcon() {
        return metaIcon;
    }

    public void setMetaIcon(String metaIcon) {
        this.metaIcon = metaIcon;
    }

    public String getMetaHidden() {
        return metaHidden;
    }

    public void setMetaHidden(String metaHidden) {
        this.metaHidden = metaHidden;
    }

    public String getMetaNoCache() {
        return metaNoCache;
    }

    public void setMetaNoCache(String metaNoCache) {
        this.metaNoCache = metaNoCache;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
