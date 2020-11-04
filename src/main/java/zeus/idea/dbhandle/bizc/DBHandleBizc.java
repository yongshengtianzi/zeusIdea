package zeus.idea.dbhandle.bizc;

import org.springframework.transaction.annotation.Transactional;
import zeus.idea.dbhandle.entity.TableColEntity;

import java.util.List;
import java.util.Map;

/**
 * 类名：处理csv格式数据bizc
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-03-26 09:48
 * <p>
 * 修改人：           修改时间：
 */
public interface DBHandleBizc {
    /**
     * 保存csv数据
     * @param colInfo
     * @param datas
     * @param tableName
     * @param inCols
     * @param inColsType
     * @return
     */
    public Map<String, Object> saveCsvData(Map<String, TableColEntity> colInfo, List<Map<String, String>> datas, String tableName, String inCols, String inColsType, int i);

    /**
     * 查询表字段及字段属性
     * @param tableName
     * @return
     */
    public Map<String, TableColEntity> queryTableCols(String tableName);

    /**
     * 删除csv数据
     * @param keyDatas
     * @param tableName
     * @param keyName
     * @param keyType
     */
    public void delCsvData(List<String> keyDatas, String tableName, String keyName, String keyType);

    /**
     * 删除c所有数据
     * @param tableName
     */
    public void delAllData(String tableName);
}
