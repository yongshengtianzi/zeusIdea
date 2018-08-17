package zeus.idea.expbase.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zeus.idea.expbase.entity.ExpBaseEntity;

import java.util.List;

/**
 * 接口名：IExpBaseDao
 * 公司：-----智讯云-----
 * 功能说明：个人花费数据层
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-02-12 9:45
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface ExpBaseDao {
    /**
     * 查询已有的所有消费记录数据
     * @param ee
     * @return
     */
    public List<ExpBaseEntity> findList(@Param("ee") ExpBaseEntity ee);

    /**
     * 查询总条数
     * @param ee
     * @return
     */
    public int findListCount(@Param("ee") ExpBaseEntity ee);

    /**
     * 手机端，查询消费记录
     * @param ee
     * @return
     */
    public List<ExpBaseEntity> sjQuery(@Param("ee") ExpBaseEntity ee);

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
    public void saveExp(@Param("ee") ExpBaseEntity ee);
}
