package zeus.idea.expbase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zeus.idea.common.bizc.ISysPcodeBizc;
import zeus.idea.expbase.bizc.IExpBaseBizc;
import zeus.idea.expbase.entity.ExpBaseEntity;
import zeus.idea.sysuser.entity.UserRecordEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 该实例可以作为demo来使用
 * Created by Zeus_Emperor on 2017/9/24.
 */
@Controller
@RequestMapping("/zeus")
class ExpBaseController {

    // 在Java类中创建 logger 实例
    private static final Logger logger = LoggerFactory.getLogger(ExpBaseController.class);

    @Autowired
    private IExpBaseBizc iExpBaseBizc;

    @Autowired
    private ISysPcodeBizc iSysPcodeBizc;

    @RequestMapping(value = "expinit")
    public String getEcharts(ModelMap map, ExpBaseEntity entity) {
        if (entity == null) {
            entity = new ExpBaseEntity();
        }
        entity.setExpType("01");
        //List<ExpBaseEntity> findList = iExpBaseBizc.findList(entity);
        //map.put("findList", findList);
        map.put("pageNumberstr", "1");
        map.put("totalPages", "1");
        map.put("entity", entity);
        map.put("expTypeList", iSysPcodeBizc.pCodeList("expType"));
        map.put("expSourceList", iSysPcodeBizc.pCodeList("expSourceType"));
        return "expbase";
    }

    @RequestMapping(value = "expajax")
    @ResponseBody
    public Map<Object, Object> expInit(ExpBaseEntity entity) {
        if (entity == null) {
            entity = new ExpBaseEntity();
        }
        if (entity.getLength() == null || "".equals(entity.getLength())) {
            entity.setLength("10");
        }
        if (entity.getStart() == null || "".equals(entity.getStart())) {
            entity.setStart("1");
        }


        List<ExpBaseEntity> findList = iExpBaseBizc.findList(entity);
        Map<String, String> expTypeMap = iSysPcodeBizc.pCodeMap("expType");
        Map<String, String> expSourceTypeMap = iSysPcodeBizc.pCodeMap("expSourceType");
        for (ExpBaseEntity tmp_ebe : findList) {
            tmp_ebe.setExpType(expTypeMap.get(tmp_ebe.getExpType()));
            tmp_ebe.setExpSource(expSourceTypeMap.get(tmp_ebe.getExpSource()));
        }

        int recordsTotal = iExpBaseBizc.findListCount(entity);//总共多少数据

        Map<Object, Object> info = new HashMap<Object, Object>();
        info.put("data", findList);
        info.put("recordsTotal", recordsTotal);
        info.put("recordsFiltered", recordsTotal);
        info.put("draw", entity.getDraw());
        return info;
    }

    @RequestMapping(value = "expsave")
    @ResponseBody
    public Map<Object, Object> save(ExpBaseEntity entity) {
        if (entity == null) {
            entity = new ExpBaseEntity();
        }
        if (entity.getExpType() == null || "".equals(entity.getExpType())) {
            //entity.setExpType("01");
        }

        String retCode = iExpBaseBizc.saveExp(entity);


        Map<Object, Object> info = new HashMap<Object, Object>();
        info.put("retCode", retCode);
        return info;
    }

    @RequestMapping(value = "expsjmanage")
    @ResponseBody
    public Map<String, Object> expManage(@RequestBody Map<String, String> paramsMap, HttpServletRequest request, HttpServletResponse response) {

        Map<String,Object> resultMap = null;

        String requestType = paramsMap.get("requestType");
        Object userObj = request.getAttribute("user");
        UserRecordEntity ure = new UserRecordEntity();
        if (userObj != null) {
            ure = (UserRecordEntity) userObj;
        }

        if ("query".equals(requestType)) {
            resultMap = this.query(paramsMap, ure);
        } else if("add".equals(requestType)) {
            resultMap = this.add(paramsMap, ure);
        } else {
            resultMap = new HashMap<String,Object>();
            resultMap.put("code", "0");
            resultMap.put("msg", "requestType类型参数无效");
        }

        return resultMap;
    }

    /**
     * 查询数据
     * @param paramsMap
     * @param ure
     * @return
     */
    private Map<String, Object> query(Map<String, String> paramsMap, UserRecordEntity ure) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = paramsMap.get("startDate");
        String endDate = paramsMap.get("endDate");
        if ((startDate == null || "".equals(startDate)) && (endDate == null || "".equals(endDate))) {
            endDate = sf.format(new Date());
            Calendar cd = Calendar.getInstance();
            cd.setTime(new Date());
            cd.add(Calendar.DAY_OF_MONTH, -1);
            startDate = sf.format(cd.getTime());
        }
        String expType = paramsMap.get("expType");
        String expUserNo = ure.getUserNo();

        ExpBaseEntity entity = new ExpBaseEntity();
        entity.setExpDateStart(startDate);
        entity.setExpDateEnd(endDate);
        entity.setExpType(expType);
        entity.setExpUserNo(expUserNo);

        Map<String, Object> retMap = new HashMap<String, Object>();
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        try {
            List<ExpBaseEntity> findList = iExpBaseBizc.sjQuery(entity);
            Map<String, String> expTypeMap = iSysPcodeBizc.pCodeMap("expType");
            Map<String, String> expSourceTypeMap = iSysPcodeBizc.pCodeMap("expSourceType");
            List<ExpBaseEntity> tempList = null;
            String tempDate = "";
            for (ExpBaseEntity tmp_ebe : findList) {
                tmp_ebe.setExpType(expTypeMap.get(tmp_ebe.getExpType()));
                tmp_ebe.setExpSource(expSourceTypeMap.get(tmp_ebe.getExpSource()));
                if (!tmp_ebe.getExpDate().equals(tempDate)) {
                    if (tempDate != null && !"".equals(tempDate)) {
                        retMap.put("vDate", tempDate);
                        retMap.put("vData", tempList);
                        retList.add(retMap);
                    }
                    tempDate = tmp_ebe.getExpDate();
                    tempList = new ArrayList<ExpBaseEntity>();
                    retMap = new HashMap<String, Object>();
                    tempList.add(tmp_ebe);
                } else {
                    tempList.add(tmp_ebe);
                }
            }
            if (findList != null && findList.size() > 0) {
                retMap.put("vDate", tempDate);
                retMap.put("vData", tempList);
                retList.add(retMap);
            } else {
                code = "3";
                msg = "查询无数据";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            code = "0";
            msg = "查询失败";
        }

        resultMap.put("dataList", retList);
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 手机端，新增花费记录
     * @param paramsMap
     * @param ure
     * @return
     */
    private Map<String, Object> add(Map<String, String> paramsMap, UserRecordEntity ure) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String expType = paramsMap.get("expType");
        if (expType == null || "".equals(expType)) {
            code = "2";
            msg = "花销分类不能为空；";
        }
        String expTime = paramsMap.get("expTime");
        if (expTime == null || "".equals(expTime)) {
            code = "2";
            msg = "花销时间不能为空；";
        }
        String expSource = paramsMap.get("expSource");
        if (expSource == null || "".equals(expSource)) {
            code = "2";
            msg = "花销来源不能为空；";
        }
        String expMoney = paramsMap.get("expMoney");
        if (expMoney == null || "".equals(expMoney)) {
            code = "2";
            msg = "花销金额不能为空；";
        }
        String expDisMoney = paramsMap.get("expDisMoney");
        if (expDisMoney == null || "".equals(expDisMoney)) {
            code = "2";
            msg = "优惠金额不能为空；";
        }
        String expMark = paramsMap.get("expMark");
        if ("1".equals(code)) {
            ExpBaseEntity entity = new ExpBaseEntity();
            entity.setExpType(expType);
            entity.setExpTime(expTime);
            entity.setExpSource(expSource);
            entity.setExpMoney(expMoney);
            entity.setExpDisMoney(expDisMoney);
            entity.setExpMark(expMark);
            entity.setExpUserNo(ure.getUserNo());
            try {
                String retStr = iExpBaseBizc.saveExp(entity);
            } catch (Exception e) {
                logger.error(e.getMessage());
                code = "0";
                msg = "保存失败！";
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

}
