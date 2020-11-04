package zeus.idea.dbhandle.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import zeus.idea.dbhandle.bizc.DBHandleBizc;
import zeus.idea.dbhandle.entity.TableColEntity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * 类名：数据库问题模拟处理类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-03-25 16:06
 * <p>
 * 修改人：           修改时间：
 */
@Controller
@RequestMapping("/zeus/dbhandle")
public class DBHandleController {
    // 在Java类中创建 logger 实例
    private Logger logger = LoggerFactory.getLogger(DBHandleController.class);

    @Autowired
    private DBHandleBizc dbHandleBizc;

    /**
     * 入口
     * @param file
     * @param requestType
     * @param tableName
     * @param num
     * @param cols
     * @param colsType
     * @return
     */
    @RequestMapping(value = "init")
    @ResponseBody
    public Map<String, Object> init(@RequestParam("file") MultipartFile file,
                                    @RequestParam("requestType") String requestType,
                                    @RequestParam("tableName") String tableName,
                                    @RequestParam("num") String num,
                                    @RequestParam("cols") String cols,
                                    @RequestParam("colsType") String colsType) {

        Map<String, Object> resultMap = null;

        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("requestType", requestType);
        paramsMap.put("tableName", tableName);
        paramsMap.put("num", num);
        paramsMap.put("cols", cols);
        paramsMap.put("colsType", colsType);

        if ("csv".equals(requestType)) {
            resultMap = this.csvHandle(paramsMap, file);
        } else if("excel".equals(requestType)) {

        } else {
            resultMap = new HashMap<String,Object>();
            resultMap.put("code", "0");
            resultMap.put("msg", "requestType类型参数无效");
        }

        return resultMap;
    }

    /**
     * 处理csv格式数据
     * @param paramsMap
     * @return
     */
    private Map<String, Object> csvHandle(Map<String, String> paramsMap, MultipartFile file) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "";


        String tableName = paramsMap.get("tableName");
        String numStr = paramsMap.get("num");
        String cols = paramsMap.get("cols");
        String colsType = paramsMap.get("colsType");
        int num = 0;

        String fileName = file.getOriginalFilename();

        if (StringUtils.isBlank(tableName)) {
            code = "2";
            msg = msg + "表名不能为空；";
        }
        if (StringUtils.isBlank(numStr)) {
            code = "2";
            msg = msg + "重复次数不能都为空；";
        } else {
            num = Integer.parseInt(numStr);
        }
        if (StringUtils.isNotBlank(cols)) {
            if (StringUtils.isBlank(colsType)) {
                code = "2";
                msg = msg + "字段数据类型不能为空；";
            }
        }
        if (StringUtils.isBlank(fileName)) {
            code = "2";
            msg = msg + "未上传csv文件；";
        } else if (!(fileName.endsWith(".csv") || fileName.endsWith(".CSV"))) {
            code = "2";
            msg = msg + "上传的文件不是csv格式；";
        }

        if ("1".equals(code)) {
            Reader reader = null;
            try {
                reader = new InputStreamReader(file.getInputStream());
            } catch (IOException e) {
                logger.error("读取file文件数据出现异常：", e);
                code = "0";
                msg = "读取file文件数据出现异常";
            }
            Iterable<CSVRecord> records = null;
            if (reader != null && "1".equals(code)) {
                try {
                    CSVFormat csvFormat = CSVFormat.EXCEL.withFirstRecordAsHeader();
                    records = csvFormat.parse(reader);
                } catch (IOException e) {
                    logger.error("解析csv文件出现异常：", e);
                    code = "0";
                    msg = "解析csv文件出现异常";
                }
            }
            /**
             * 执行保存数据
             */
            if (records != null && "1".equals(code)) {
                List<Map<String, String>> datas = new ArrayList();
                for (CSVRecord for_csv : records) {
                    datas.add(for_csv.toMap());
                }
                Map<String, TableColEntity> colInfo = dbHandleBizc.queryTableCols(tableName);
                Map<String, Object> tempMap;
                for (int i = 0; i < num; i++) {
                    tempMap = dbHandleBizc.saveCsvData(colInfo, datas, tableName, cols, colsType, i);
                    if ("1".equals(tempMap.get("code"))) {
                        List<String> retDatas = (List) tempMap.get("retDatas");
                        String keyName = String.valueOf(tempMap.get("keyName"));
                        String keyType = String.valueOf(tempMap.get("keyType"));
//                        dbHandleBizc.delCsvData(retDatas, tableName, keyName, keyType);
//                        dbHandleBizc.delAllData(tableName);

                        List<String> tempDatas = new ArrayList();
                        for (String for_str : retDatas) {
                            tempDatas.add(for_str);
                            if (tempDatas.size() > 1000) {
                                dbHandleBizc.delCsvData(tempDatas, tableName, keyName, keyType);
                                tempDatas = new ArrayList();
                            }
                        }
                        if (tempDatas.size() > 0) {
                            dbHandleBizc.delCsvData(tempDatas, tableName, keyName, keyType);
                        }
                    }
                }
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }
}
