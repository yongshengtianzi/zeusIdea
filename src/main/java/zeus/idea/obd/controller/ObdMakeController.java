package zeus.idea.obd.controller;

import org.apache.commons.lang3.StringUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                List<ObdCarEntity> data = obdMakeBizc.queryObdCar(carNum, carPeople, carJia);
                if (data == null || data.size() == 0) {
                    code = "3";
                    msg = "未查到车辆信息！";
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
                Map<String, String> tempMap = obdMakeBizc.insMakeInfo(entity);
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

}
