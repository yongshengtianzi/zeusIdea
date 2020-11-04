package zeus.idea.common.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * 接口名：ISeqDao
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * 序列处理dao
 * <p>
 * 作者：jinyang.wang      创建时间：2020/9/13 15:52
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface ISeqDao {
    /**
     * 方法功能说明：获取用户序列
     *
     * @param
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/9/13 15:55
     *
     * 修改人:          修改日期:
     */
    String getUserSEQ();
}
