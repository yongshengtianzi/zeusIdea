package zeus.idea.obd.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zeus.idea.obd.entity.ObdCarEntity;
import zeus.idea.obd.entity.ObdCunEntity;
import zeus.idea.obd.entity.ObdMakeEntity;
import zeus.idea.obd.entity.ObdMakeZongEntity;

import java.util.List;
import java.util.Map;

/**
 * 接口名：ObdMakeDao
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * obd终端安装预约dao
 * <p>
 * 作者：jinyang.wang      创建时间：2020/11/3 10:39
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface ObdMakeDao {

    /**
     * 方法功能说明：查询车辆信息
     *
     * @param carNum
     * @param carPeople
     * @param carJia
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 11:22
     *
     * 修改人:          修改日期:
     */
    List<ObdCarEntity> queryObdCar(@Param("carNum") String carNum, @Param("carPeople") String carPeople,
                                   @Param("carJia") String carJia);

    /**
     * 方法功能说明：保存预约信息
     *
     * @param ome
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 11:21
     *
     * 修改人:          修改日期:
     */
    void saveObdMake(@Param("ome") ObdMakeEntity ome);

    /**
     * 方法功能说明：更新车辆信息
     *
     * @param oce
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 15:41
     *
     * 修改人:          修改日期:
     */
    void updCarInfo(@Param("oce") ObdCarEntity oce);

    /**
     * 方法功能说明：保存新增的车辆
     *
     * @param carNum
     * @param carJia
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/9 21:21
     *
     * 修改人:          修改日期:
     */
    void insObdCar(@Param("carNum") String carNum, @Param("carJia") String carJia);

    /**
     * 方法功能说明：根据车牌号查询预约信息
     *
     * @param carNum
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 16:15
     *
     * 修改人:          修改日期:
     */
    List<ObdMakeEntity> queryMakeInfo(@Param("carNum") String carNum, @Param("quX") String quX,
                                      @Param("expTime") String expTime, @Param("anZhDi") String anZhDi);
    
    /**
     * 方法功能说明：根据区县和日期查询可用预约数
     *
     * @param cunQux
     * @param cunDate
     * @return 
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 16:24
     *
     * 修改人:          修改日期:
     */
    List<ObdCunEntity> queryCunInfo(@Param("cunQux") String cunQux, @Param("cunDate") String cunDate,
                                    @Param("cunDateNow") String cunDateNow);

    /**
     * 方法功能说明：批量保存预约信息
     *
     * @param lsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/6 18:49
     *
     * 修改人:          修改日期:
     */
    void insObdMakeAll(@Param("lsMap") List<ObdMakeEntity> lsMap);

    /**
     * 方法功能说明：汇总预约数量
     *
     * @param nowDate
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/8 9:02
     *
     * 修改人:          修改日期:
     */
    List<ObdMakeZongEntity> queryMakeZong(@Param("nowDate") String nowDate);

    /**
     * 方法功能说明：查询字典
     *
     * @param
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/8 21:04
     *
     * 修改人:          修改日期:
     */
    List<Map<String, String>> queryDict();

    /**
     * 方法功能说明：查询预约数据，供导出使用
     *
     * @param expTime
     * @param quX
     * @param anZhDi
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/9 10:46
     *
     * 修改人:          修改日期:
     */
    List<ObdMakeEntity> handleQuery(@Param("expTime") String expTime, @Param("quX") String quX,
                                    @Param("anZhDi") String anZhDi);

    /**
     * 方法功能说明：查询系统参数
     *
     * @param paraCode
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/2 15:49
     *
     * 修改人:          修改日期:
     */
    List<Map<String, String>> querySysPara(@Param("paraCode") String paraCode);
}
