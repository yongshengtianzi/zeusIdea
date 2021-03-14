package zeus.idea.common.extend;

/**
 * 类名：PageEntity
 * 公司：-----智讯云-----
 * 功能说明：
 * 分页使用的公共entity，供继承使用
 * <p>
 * 作者：jinyang.wang      创建时间：2020/12/27 18:18
 * <p>
 * 修改人：           修改时间：
 */
public class PageEntity {

    private String tablePageSize = "10";//每页显示多少条数据
    private String curPage = "1";//当前查询的是第几页数据


    public String getTablePageSize() {
        return tablePageSize;
    }

    public void setTablePageSize(String tablePageSize) {
        this.tablePageSize = tablePageSize;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }
}
