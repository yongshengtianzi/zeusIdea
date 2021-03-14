package zeus.idea.note.entity;

import zeus.idea.common.extend.PageEntity;

import java.util.List;

/**
 * 类名：SelectEntity
 * 公司：-----智讯云-----
 * 功能说明：
 * 页面传来的数据承接entity
 * <p>
 * 作者：jinyang.wang      创建时间：2020/12/27 18:26
 * <p>
 * 修改人：           修改时间：
 */
public class SelectEntity extends PageEntity {

    private String nodeInfoNo;//笔记编号
    private String nodeInfoTitle;//笔记主题
    private List<String> nodeInfoKey;//关键字集合
    private String url;//原文链接
    private String createUserId;//创建笔记的用户id

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getNodeInfoNo() {
        return nodeInfoNo;
    }

    public void setNodeInfoNo(String nodeInfoNo) {
        this.nodeInfoNo = nodeInfoNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNodeInfoTitle() {
        return nodeInfoTitle;
    }

    public void setNodeInfoTitle(String nodeInfoTitle) {
        this.nodeInfoTitle = nodeInfoTitle;
    }

    public List<String> getNodeInfoKey() {
        return nodeInfoKey;
    }

    public void setNodeInfoKey(List<String> nodeInfoKey) {
        this.nodeInfoKey = nodeInfoKey;
    }
}
