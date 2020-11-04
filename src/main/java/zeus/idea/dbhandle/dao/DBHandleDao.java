package zeus.idea.dbhandle.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zeus.idea.dbhandle.entity.TableColEntity;

import java.util.List;
import java.util.Map;

/**
 * 接口名：处理csv格式数据dao
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-03-26 10:02
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface DBHandleDao {
    /**
     * 查询表字段及类型
     * @param tableName
     * @return
     */
    public List<TableColEntity> queryTableCols(@Param("sch") String sch, @Param("tableName") String tableName);

    /**
     * 保存csv数据
     * @param colNames
     * @param datas
     */
    public void saveCsvData(@Param("colNames") List<String> colNames, @Param("datas") List<Map<String, String>> datas, @Param("tableName") String tableName);

    /**
     * 删除csv数据
     * @param keyDatas
     * @param tableName
     * @param colName
     */
    public void delCsvData(@Param("keyDatas") List<String> keyDatas, @Param("tableName") String tableName, @Param("colName") String colName);

    /**
     * 删除所有数据
     * @param tableName
     */
    public void delAllData(@Param("tableName") String tableName);
}
