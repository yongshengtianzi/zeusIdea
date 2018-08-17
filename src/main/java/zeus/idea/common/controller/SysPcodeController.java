package zeus.idea.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zeus.idea.common.bizc.ISysPcodeBizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：查询数据字典控制层
 * 公司：-----智讯云-----
 * 功能说明：用于查询数据字典数据
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-03-03 8:29
 * <p>
 * 修改人：           修改时间：
 */
@Controller
@RequestMapping("/sys")
public class SysPcodeController {

    @Autowired
    private ISysPcodeBizc iSysPcodeBizc;

    @RequestMapping(value = "pCodeManage")
    @ResponseBody
    public Map<String, Object> pCodeManage(@RequestBody Map<String, String> paramsMap) {
        Map<String,Object> resultMap = null;

        String requestType = paramsMap.get("requestType");

        if ("query".equals(requestType)) {
            resultMap = this.query(paramsMap);
        } else  {
            resultMap = new HashMap<String,Object>();
            resultMap.put("code", "0");
            resultMap.put("msg", "requestType类型参数无效");
        }

        return resultMap;
    }

    /**
     * 查询数据字典
     * @param mapInfo
     * @return
     */
    private Map<String,Object> query(Map<String,String> mapInfo) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String zdCode = mapInfo.get("zdCode");
        if (zdCode == null || "".equals(zdCode)) {
            code = "2";
            msg = "参数zdCode为空";
        }
        List<Map<String, String>> tempList = null;
        if ("1".equals(code)) {
            try {
                String[] zdCodes = zdCode.split(",");
                for (String for_str : zdCodes) {
                    if (for_str == null || "".equals(for_str)) {
                        continue;
                    }
                    tempList = iSysPcodeBizc.pCodeList(for_str);

                    if (tempList == null) {
                        tempList = new ArrayList<Map<String, String>>();
                    }
                    resultMap.put(for_str, tempList);
                }
            } catch (Exception e) {
                code = "0";
                msg = "查询失败";
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);

        return resultMap;
    }
}
