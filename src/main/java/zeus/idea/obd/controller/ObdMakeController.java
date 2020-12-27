package zeus.idea.obd.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zeus.idea.obd.bizc.ObdMakeBizc;
import zeus.idea.obd.entity.ObdCarEntity;
import zeus.idea.obd.entity.ObdCunEntity;
import zeus.idea.obd.entity.ObdMakeEntity;
import zeus.idea.obd.entity.ObdMakeZongEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 类名：ObdMakeController
 * 公司：-----智讯云-----
 * 功能说明：
 * obd终端安装预约controller
 * <p>
 * 作者：jinyang.wang      创建时间：2020/11/3 10:35
 * <p>
 * 修改人：           修改时间：
 */
@Controller
@RequestMapping("/obd/make")
public class ObdMakeController {
    // 在Java类中创建 logger 实例
    private static final Logger logger = LoggerFactory.getLogger(ObdMakeController.class);

    @Autowired
    private ObdMakeBizc obdMakeBizc;

    /**
     * 方法功能说明：查询车辆信息
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 16:01
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "querycarinfo")
    @ResponseBody
    public Map<String, Object> queryCarInfo(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        List<Map<String, String>> tempMapList = obdMakeBizc.querySysPara("obdStart");

        if (tempMapList != null && !tempMapList.isEmpty()) {
            if ("0".equals(tempMapList.get(0).get("defaultValue"))) {
                retMap.put("code", "2");
                retMap.put("msg", tempMapList.get(0).get("remark"));
                return retMap;
            }
        }

        LocalDateTime nowTime = LocalDateTime.now();
        int tempHour = nowTime.getHour();
        if (tempHour < 7 || tempHour >= 20) {
            code = "2";
            msg = "当天预约已经结束，请在7:00~20:00之间预约！";
            retMap.put("code", code);
            retMap.put("msg", msg);
            return retMap;
        }

        String carNum = paramsMap.get("carNum");
        String carPeople = paramsMap.get("carPeople");
        String carJia = paramsMap.get("carJia");

        if (StringUtils.isBlank(carNum)) {
            code = "2";
            msg = "车牌号不能为空；";
        } else {
            carNum = carNum.toUpperCase();
        }
        if (StringUtils.isBlank(carJia)) {
            code = "2";
            msg = msg + "车架/VIN号不能为空；";
        } else if (carJia.length() < 6) {
            code = "2";
            msg = msg + "请输入车架/VIN号后六位；";
        } else {
            carJia = carJia.toUpperCase();
        }

        if ("1".equals(code)) {
            try {
                List<ObdCarEntity> data = obdMakeBizc.queryInsObdCar(carNum, carJia);
                if (data == null || data.size() == 0) {
                    code = "3";
                    msg = "未查到车辆信息，请去区县环保局登记！";
                } else if (data.size() > 1) {
                    code = "3";
                    msg = "查询到多个车辆信息，请输入完整车架/VIN号查询！";
                } else {
                    retMap.put("data", data.get(0));//查询成功放入车辆信息
                    msg = "查询成功";
                }
            } catch (Exception e) {
                logger.error("查询车辆信息异常：" + e.getMessage());
                code = "0";
                msg = "查询车辆信息异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：根据车牌号查询预约信息
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 20:16
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "querymakeinfo")
    @ResponseBody
    public Map<String, Object> queryMakeInfo(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        String carNum = paramsMap.get("carNum");

        if (StringUtils.isBlank(carNum)) {
            code = "2";
            msg = "车牌号不能为空，请返回后重新查询车辆信息；";
        } else {
            carNum = carNum.toUpperCase();
        }

        if ("1".equals(code)) {
            try {
                List<ObdMakeEntity> data = obdMakeBizc.queryObdMake(carNum);
                if (data.size() >= 1) {
                    retMap.put("data", data.get(0));//查询成功放入车辆信息
                    msg = "查询成功";
                }
            } catch (Exception e) {
                logger.error("查询车辆信息异常：" + e.getMessage());
                code = "0";
                msg = "查询车辆信息异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：更新车辆信息
     *
     * @param entity
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 16:01
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "updcarinfo")
    @ResponseBody
    public Map<String, Object> updCarInfo(@RequestBody ObdCarEntity entity) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        if (StringUtils.isBlank(entity.getCarNum())) {
            code = "2";
            msg = "车牌号不能为空；";
        } else {
            entity.setCarNum(entity.getCarNum().toUpperCase());
        }
        if (StringUtils.isBlank(entity.getCarPeople())) {
            code = "2";
            msg = msg + "车主姓名不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarJia())) {
            code = "2";
            msg = msg + "车架号不能为空；";
        } else {
            entity.setCarJia(entity.getCarJia().toUpperCase());
        }
        if (StringUtils.isBlank(entity.getCarDrive())) {
            code = "2";
            msg = msg + "发动机号不能为空；";
        } else {
            entity.setCarDrive(entity.getCarDrive().toUpperCase());
        }
        if (StringUtils.isBlank(entity.getCarVehicleType())) {
            code = "2";
            msg = msg + "车辆型号不能为空；";
        } else {
            entity.setCarVehicleType(entity.getCarVehicleType().toUpperCase());
        }
        if (StringUtils.isBlank(entity.getCarBrand())) {
            code = "2";
            msg = msg + "车辆品牌不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarPhoneNum())) {
            code = "2";
            msg = msg + "电话号码不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarNumColor())) {
            code = "2";
            msg = msg + "车牌颜色不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarType())) {
            code = "2";
            msg = msg + "车辆类型不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarEngineType())) {
            code = "2";
            msg = msg + "发动机型号不能为空；";
        } else {
            entity.setCarEngineType(entity.getCarEngineType().toUpperCase());
        }
        if (StringUtils.isBlank(entity.getCarPopleNum())) {
            code = "2";
            msg = msg + "总客数不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarMaxKg())) {
            code = "2";
            msg = msg + "列车最大总质量不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarNiaos())) {
            code = "2";
            msg = msg + "尿素箱容积不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarManufacturer())) {
            code = "2";
            msg = msg + "生产厂商不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarQux())) {
            code = "2";
            msg = msg + "车辆所属区县不能为空；";
        }
        if (StringUtils.isBlank(entity.getCarDanw())) {
            code = "2";
            msg = msg + "所属单位名称不能为空；";
        }

        if ("1".equals(code)) {
            try {
                obdMakeBizc.updCarInfo(entity);
                msg = "更新成功";
            } catch (Exception e) {
                logger.error("更新车辆信息异常：" + e.getMessage());
                code = "0";
                msg = "更新车辆信息异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：保存预约信息
     *
     * @param entity
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 16:01
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "insmakeinfo")
    @ResponseBody
    public Map<String, Object> insMakeInfo(@RequestBody ObdMakeEntity entity) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        if (StringUtils.isBlank(entity.getCarNum())) {
            code = "2";
            msg = "车牌号不能为空；";
        } else {
            entity.setCarNum(entity.getCarNum().toUpperCase());
        }
        if (StringUtils.isBlank(entity.getMakePeople())) {
            code = "2";
            msg = "预约人不能为空；";
        }
        if (StringUtils.isBlank(entity.getPhoneNum())) {
            code = "2";
            msg = msg + "电话不能为空；";
        }
        if (StringUtils.isBlank(entity.getQuX())) {
            code = "2";
            msg = msg + "区县不能为空；";
        }
        if (StringUtils.isBlank(entity.getAnZhDi())) {
            code = "2";
            msg = msg + "安装点不能为空；";
        }
        if (StringUtils.isBlank(entity.getExpTime())) {
            code = "2";
            msg = msg + "安装日期不能为空；";
        }

        List<ObdCarEntity> tempList = obdMakeBizc.queryObdCar(entity.getCarNum(), null, null);
        if (tempList.size() == 0) {
            //该车牌不在库中
            code = "2";
            msg = msg + "当前车牌号车辆不属于可预约车辆；";
        }

        if ("1".equals(code)) {
            try {
                Map<String, String> tempMap = obdMakeBizc.insMakeInfo(entity, "0");
                code = tempMap.get("code");
                msg = tempMap.get("msg");
            } catch (Exception e) {
                logger.error("预约异常：" + e.getMessage());
                code = "0";
                msg = "预约安装异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：查询可预约的日期
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 17:53
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "queryquxinfo")
    @ResponseBody
    public Map<String, Object> queryQuxInfo(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        String quX = paramsMap.get("quX");

        if (StringUtils.isBlank(quX)) {
            code = "2";
            msg = "请选择区县；";
        }

        if ("1".equals(code)) {
            try {
                List<ObdCunEntity> data = obdMakeBizc.queryCunDateList(quX);
                if (data == null || data.size() == 0) {
                    code = "3";
                    msg = quX + "没有开放预约！";
                } else {
                    List<String> dataList = new ArrayList<>();
                    for (ObdCunEntity for_entity : data) {
                        dataList.add(for_entity.getCunDate());
                    }
                    retMap.put("data", dataList);//查询成功放入车辆信息
                    msg = "查询成功";
                }
            } catch (Exception e) {
                logger.error("查询可用预约日期异常：" + e.getMessage());
                code = "0";
                msg = "查询可用预约日期异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：批量查询同一个车主所有未预约车辆信息
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 16:01
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "querynomakecarinfo")
    @ResponseBody
    public Map<String, Object> queryNoMakeCarInfo(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        LocalDateTime nowTime = LocalDateTime.now();
        int tempHour = nowTime.getHour();
        if (tempHour < 7 || tempHour >= 20) {
            code = "2";
            msg = "当天预约已经结束，请在7:00~20:00之间预约！";
            retMap.put("code", code);
            retMap.put("msg", msg);
            return retMap;
        }

        String carNum = paramsMap.get("carNum");
        String carJia = paramsMap.get("carJia");

        if (StringUtils.isBlank(carNum)) {
            code = "2";
            msg = "车牌号不能为空；";
        } else {
            carNum = carNum.toUpperCase();
        }
        if (StringUtils.isBlank(carJia)) {
            code = "2";
            msg = msg + "车架/VIN号不能为空；";
        } else if (carJia.length() < 6) {
            code = "2";
            msg = msg + "请输入车架/VIN号后六位；";
        } else {
            carJia = carJia.toUpperCase();
        }

        if ("1".equals(code)) {
            try {
                List<ObdCarEntity> data = obdMakeBizc.queryNoMakeCarInfo(carNum, carJia);
                if (data == null || data.size() == 0) {
                    code = "3";
                    msg = "未查到车辆信息，请去区县环保局登记！";
                } else if (data.size() > 1) {
                    code = "3";
                    msg = "查询到多个车辆信息，请输入完整车架/VIN号查询！";
                } else {
                    if ("1".equals(data.get(0).getIsMake())) {
                        code = "3";
                        msg = "当前车辆已经被预约，请勿重复预约！";
                    } else {
                        retMap.put("data", data.get(0));//查询成功放入车辆信息
                        msg = "查询成功";
                    }
                }
            } catch (Exception e) {
                logger.error("查询车辆信息异常：" + e.getMessage());
                code = "0";
                msg = "查询车辆信息异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：保存多车预约信息
     *
     * @param entity
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/6 19:52
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "insallmakeinfo")
    @ResponseBody
    public Map<String, Object> insAllMakeInfo(@RequestBody ObdMakeEntity entity) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        if (entity.getCars() == null || entity.getCars().size() < 20) {
            code = "2";
            msg = "预约的车辆数至少需要20辆！";
            retMap.put("code", code);
            retMap.put("msg", msg);
            return retMap;
        }

        if (entity.getCars().size() > 100) {
            code = "2";
            msg = "预约的车辆数不能超过100辆！";
            retMap.put("code", code);
            retMap.put("msg", msg);
            return retMap;
        }

        List<ObdCarEntity> tempList;
        for (ObdCarEntity for_entity : entity.getCars()) {
            if (StringUtils.isBlank(for_entity.getCarNum())) {
                code = "2";
                msg = "编号为" + for_entity.getId() + "的车辆，车牌号不能为空；";
                retMap.put("code", code);
                retMap.put("msg", msg);
                return retMap;
            } else {
                for_entity.setCarNum(for_entity.getCarNum().toUpperCase());
            }

            tempList = obdMakeBizc.queryObdCar(for_entity.getCarNum(), null, null);
            if (tempList.size() == 0) {
                //该车牌不在库中
                code = "2";
                msg = for_entity.getCarNum() + "当前车牌号车辆不属于可预约车辆；";
                retMap.put("code", code);
                retMap.put("msg", msg);
                return retMap;
            }
        }

        if (StringUtils.isBlank(entity.getMakePeople())) {
            code = "2";
            msg = "预约人不能为空；";
        }
        if (StringUtils.isBlank(entity.getPhoneNum())) {
            code = "2";
            msg = msg + "电话不能为空；";
        }
        if (StringUtils.isBlank(entity.getQuX())) {
            code = "2";
            msg = msg + "区县不能为空；";
        }
        if (StringUtils.isBlank(entity.getAddr())) {
            code = "2";
            msg = msg + "预约安装地址不能为空；";
        }
        if (StringUtils.isBlank(entity.getExpTime())) {
            code = "2";
            msg = msg + "安装日期不能为空；";
        }

        if ("1".equals(code)) {
            try {
                Map<String, String> tempMap = obdMakeBizc.insMakeInfo(entity, "1");
                code = tempMap.get("code");
                msg = tempMap.get("msg");
            } catch (Exception e) {
                logger.error("预约异常：" + e.getMessage());
                code = "0";
                msg = "预约安装异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：查询预约数据
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/8 8:26
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "querymakenowdate")
    @ResponseBody
    public Map<String, Object> queryMakeNowDate(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        String nowDate = paramsMap.get("nowDate");
        if (StringUtils.isBlank(nowDate)) {
            code = "2";
            msg = "查询日期不能为空！";
        }

        if ("1".equals(code)) {
            try {
                List<ObdMakeZongEntity> data = obdMakeBizc.queryMakeNowDate(nowDate);
                if (data == null || data.size() == 0) {
                    code = "3";
                    msg = "未查到预约信息！";
                } else {
                    retMap.put("data", data);//查询成功放入车辆信息
                    msg = "查询成功";
                }
            } catch (Exception e) {
                logger.error("查询预约信息异常：" + e.getMessage());
                code = "0";
                msg = "查询预约信息异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：查询字典
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/8 21:27
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "querydict")
    @ResponseBody
    public Map<String, Object> queryDict(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        if ("1".equals(code)) {
            try {
                Map<String, Map<String, Map<String, String>>> data = obdMakeBizc.queryDict();
                retMap.put("data", data);//查询成功放入数据字典
                msg = "查询成功";
            } catch (Exception e) {
                logger.error("查询预约信息异常：" + e.getMessage());
                code = "0";
                msg = "查询预约信息异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：查询系统参数
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/2 15:54
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "querysyspara")
    @ResponseBody
    public Map<String, Object> querySysPara(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        String paraStr = paramsMap.get("paraStr");

        if ("1".equals(code)) {
            try {
                if (StringUtils.isNotBlank(paraStr)) {
                    List<Map<String, String>> mapList;
                    String[] strs = paraStr.split(",");
                    for (String for_str : strs) {
                        mapList = obdMakeBizc.querySysPara(for_str);
                        if (mapList != null || !mapList.isEmpty()) {
                            retMap.put(for_str, mapList.get(0));
                        }
                    }
                }
                msg = "查询成功";
            } catch (Exception e) {
                logger.error("查询系统参数异常：" + e.getMessage());
                code = "0";
                msg = "查询系统参数异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：导出预约信息
     *
     * @param mapInfo
     * @param request
     * @param response
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/9 11:06
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "output")
    @ResponseBody
    public Map<String, Object> handleOutput(@RequestBody Map<String,String> mapInfo, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String expTime = mapInfo.get("expTime");
        String quX = mapInfo.get("quX");
        String anZhDi = mapInfo.get("anZhDi");
        String yanZh = mapInfo.get("yanZh");

        if (!"杨涛".equals(yanZh)) {
            code = "2";
            msg = "验证失败，请确认身份后再试！";
        }

        if ("1".equals(code)) {
            try {
                List<String> listCol = new ArrayList<String>();
                List<ObdMakeEntity> tempList = obdMakeBizc.handleQuery(expTime, quX, anZhDi);
                if (tempList != null && tempList.size() > 0) {
                    InputStream in = ObdMakeController.class.getClassLoader().getResourceAsStream("templates/other/yuYueInfo.xlsx");
                    XSSFWorkbook wb = new XSSFWorkbook(in);
                    XSSFSheet st = wb.getSheet("预约信息");
                    for (int i = 0; i < st.getRow(0).getLastCellNum(); i++) {
                        listCol.add(st.getRow(0).getCell(i).getStringCellValue());
                    }
                    XSSFRow tempRow = null;
                    for (int i = 0; i < tempList.size(); i++) {
                        tempRow = st.createRow(i + 1);
                        for (int j = 0; j < listCol.size(); j++) {
                            if ("车牌号".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getCarNum());
                            } else if ("预约人".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getMakePeople());
                            } else if ("电话".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getPhoneNum());
                            } else if ("区县".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getQuX());
                            } else if ("安装点".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getAnZhDi());
                            } else if ("安装日期".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getExpTime());
                            } else if ("备注".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getExpMark());
                            } else if ("创建时间".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getCreateTime());
                            } else if ("修改时间".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getUpdateTime());
                            } else if ("地址".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getAddr());
                            } else if ("姓名及电话".equals(listCol.get(j))) {
                                tempRow.createCell(j).setCellValue(tempList.get(i).getTel());
                            }
                        }
                    }
                    response.reset();
                    SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmmss");
                    String rootPath = request.getContextPath();
                    if (StringUtils.isBlank(rootPath)) {
                        rootPath = "webapp";
                    }
                    String tempPath = request.getSession().getServletContext().getRealPath("");
                    tempPath = tempPath + "file_temp/";
                    File file = new File(tempPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    tempPath = tempPath + "预约信息" + sm.format(new Date()) + ".xlsx";
                    file = new File(tempPath);
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    FileOutputStream output=new FileOutputStream(file);
                    wb.write(output);
                    output.close();
                    in.close();
                    tempPath = tempPath.replace("\\", "/");
                    tempPath = tempPath.substring(tempPath.lastIndexOf("/file_temp"));
                    resultMap.put("path", tempPath);
                    msg = "成功";
                } else {
                    code = "2";
                    msg = "没有数据可以导出！";
                }
            } catch (Exception e) {
                code = "0";
                msg = "导出失败";
                logger.error("导出异常：", e);
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);

        return resultMap;
    }

}
