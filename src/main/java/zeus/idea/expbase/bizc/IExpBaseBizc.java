package zeus.idea.expbase.bizc;

import zeus.idea.expbase.entity.ExpBaseEntity;

import java.util.List;

/**
 * 接口名：花费记录逻辑处理接口
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-02-23 09:23
 * <p>
 * 修改人：           修改时间：
 */
public interface IExpBaseBizc {
    public List<ExpBaseEntity> findList(ExpBaseEntity ee);
    public int findListCount(ExpBaseEntity ee);

    /**
     * 获取序列
     * @return
     */
    public String getSEQ();

    /**
     * 保存花费记录数据
     * @param ee
     * @return
     */
    public String saveExp(ExpBaseEntity ee);

    /**
     * 手机端，查询消费记录
     * @param ee
     * @return
     */
    public List<ExpBaseEntity> sjQuery(ExpBaseEntity ee);

}
