package zeus.idea.dbhandle.bizc.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.common.utils.UuidUtil;
import zeus.idea.dbhandle.bizc.DBHandleBizc;
import zeus.idea.dbhandle.entity.TableColEntity;
import zeus.idea.dbhandle.dao.DBHandleDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：处理csv格式数据bizcImpl
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-03-26 09:50
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class DBHandleBizcImpl implements DBHandleBizc {

    @Autowired
    private DBHandleDao dbHandleDao;

    /**
     * 查询表字段及字段属性
     * @param tableName
     * @return
     */
    public Map<String, TableColEntity> queryTableCols(String tableName) {
        String[] strs = tableName.split("\\.");
        List<TableColEntity> tempList;
        if (tableName.indexOf(".") < 0) {
            tempList = dbHandleDao.queryTableCols("public", tableName);
        } else {
            tempList = dbHandleDao.queryTableCols(strs[0], strs[1]);
        }

        Map<String, TableColEntity> retMap = new HashMap();

        for (TableColEntity for_tce : tempList) {
            retMap.put(for_tce.getColName(), for_tce);
        }

        return retMap;
    }

    /**
     * 保存csv数据
     * @param colInfo
     * @param datas
     * @param tableName
     * @param inCols
     * @param inColsType
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, Object> saveCsvData(Map<String, TableColEntity> colInfo, List<Map<String, String>> datas,
                                           String tableName, String inCols, String inColsType, int i) {
        Map<String, Object> retMap = new HashMap();
        String code = "0";
        String msg = "无数据";
        List<String> retDatas = new ArrayList();//保存插入的数据主键
        String keyName = "";//保存主键名
        String keyType = "";//保存主键类型

        Map<String, String> colAndType = this.colAndType(inCols, inColsType);

        /*
        匹配数据库中表字段和数据中表字段
         */
        Map<String, TableColEntity> tempColsMap = new HashMap();
        Map<String, String> tempMap = datas.get(0);
        TableColEntity tempTCE;
        for (String str_for : colInfo.keySet()) {
            tempTCE = colInfo.get(str_for);
            String tempStr = tempMap.get(str_for);
            if (StringUtils.isBlank(tempStr) && !"key".equals(tempTCE.getIsKey())) {
                continue;
            } else {
                tempColsMap.put(str_for, tempTCE);
                /*
                寻找主键
                 */
                if ("key".equals(tempTCE.getIsKey())) {
                    keyName = tempTCE.getColName();
                    keyType = tempTCE.getColType();
                }
            }
        }
        if (tempColsMap.size() == 0) {
            code = "2";
            msg = "数据列名与数据库表字段名称不匹配";
        } else {
            List<String> colNames = new ArrayList();
            for (String for_key : tempColsMap.keySet()) {
                colNames.add(for_key);

            }
            TableColEntity tempTce;
            String tempVal;
            List<Map<String, String>> tempDatas = new ArrayList();
            for (Map<String, String> for_map : datas) {
                if (i == 0) {
                    for (String for_str : colNames) {
                        tempTce = tempColsMap.get(for_str);
                        tempVal = this.handleAutoData(colAndType, for_str, for_map.get(for_str));

                        for_map.put(for_str, this.handleVal(tempTce.getColType(), tempVal));
                    }
                }
                tempDatas.add(for_map);//将map对象数据保存到list中，保存数据使用
                retDatas.add(for_map.get(keyName));//将主键数据保存到list中，供删除数据时使用
                if (tempDatas.size() > 100) {
                    /*
                    保存数据，50次保存一次
                     */
                    dbHandleDao.saveCsvData(colNames, tempDatas, tableName);
                    tempDatas = new ArrayList();
                }
                code = "1";
                msg = "成功";
            }
            if (tempDatas.size() > 0) {
                dbHandleDao.saveCsvData(colNames, tempDatas, tableName);
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        retMap.put("retDatas", retDatas);
        retMap.put("keyName", keyName);
        retMap.put("keyType", keyType);
        return retMap;
    }

    /**
     * 根据数据类型处理值
     * @param colType
     * @param colVal
     * @return
     */
    private String handleVal(String colType, String colVal) {
        if ("timestamp".equals(colType)) {
            return "TO_TIMESTAMP('" + colVal + "', 'DD/MM/YYYY HH24:MI:SS')";
        } else {
            return "'" + colVal + "'";
        }
    }

    /**
     * 分解自动填数参数
     * @param inCols
     * @param inColsType
     * @return
     */
    private Map<String, String> colAndType(String inCols, String inColsType) {
        Map<String, String> colAndType = new HashMap();
        if (StringUtils.isNotBlank(inCols)) {
            String[] strs = inCols.split(",");
            String[] strsType = inColsType.split(",");
            for (int i = 0; i < strs.length; i++) {
                colAndType.put(strs[i], strsType[i]);
            }
        }
        return colAndType;
    }

    /**
     * 处理自动要填的数
     * @param colAndType
     * @param colName
     * @param colVal
     * @return
     */
    private String handleAutoData(Map<String, String> colAndType, String colName, String colVal) {
        String tempType = colAndType.get(colName);
        if (StringUtils.isBlank(tempType)) {
            return colVal;
        } else {
            if ("UUID".equals(tempType)) {
                return UuidUtil.getUUIDLine();
            } else {
                return colVal;
            }
        }
    }

    /**
     * 删除csv数据
     * @param keyDatas
     * @param tableName
     * @param keyName
     * @param keyType
     */
    @Transactional(readOnly = false)
    public void delCsvData(List<String> keyDatas, String tableName, String keyName, String keyType) {
//        List<String> tempDatas = new ArrayList();
//        for (String for_str : keyDatas) {
//            tempDatas.add(for_str);
//            if (tempDatas.size() > 1000) {
//                dbHandleDao.delCsvData(tempDatas, tableName, keyName);
//                tempDatas = new ArrayList();
//            }
//        }
//        if (tempDatas.size() > 0) {
//            dbHandleDao.delCsvData(tempDatas, tableName, keyName);
//        }
        dbHandleDao.delCsvData(keyDatas, tableName, keyName);
    }

    /**
     * 删除所有数据
     * @param tableName
     */
    @Transactional(readOnly = false)
    public void delAllData(String tableName) {
        dbHandleDao.delAllData(tableName);
    }
}
