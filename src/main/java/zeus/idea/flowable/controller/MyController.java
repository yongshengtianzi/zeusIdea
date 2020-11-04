package zeus.idea.flowable.controller;

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zeus.idea.flowable.bizc.IMyBizc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：天子测试流程Controller
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-12-01 10:25
 * <p>
 * 修改人：           修改时间：
 */
@Controller
@RequestMapping("/my")
public class MyController {

    @Autowired
    private IMyBizc iMyBizc;

    @RequestMapping(value = "expsjmanage")
    @ResponseBody
    public Map<String, Object> expManage(@RequestBody Map<String, String> paramsMap) {

        Map<String,Object> resultMap = null;

        String requestType = paramsMap.get("requestType");

        if ("start".equals(requestType)) {
            resultMap = this.start(paramsMap);
        } else if("query".equals(requestType)) {
            resultMap = this.queryTask(paramsMap);
        } else if("queryHistory".equals(requestType)) {
            resultMap = this.queryHistory(paramsMap);
        } else if("queryProcessHistory".equals(requestType)) {
            resultMap = this.queryProcessHistory(paramsMap);
        } else if("apply".equals(requestType)) {
            resultMap = this.apply(paramsMap);
        } else if("send".equals(requestType)) {
            resultMap = this.send(paramsMap);
        } else if("claim".equals(requestType)) {
            resultMap = this.claim(paramsMap);
        } else if("delete".equals(requestType)) {
            resultMap = this.delete(paramsMap);
        } else if("stop".equals(requestType)) {
            resultMap = this.stop(paramsMap);
        } else if("startStop".equals(requestType)) {
            resultMap = this.startStop(paramsMap);
        } else if("findPicture".equals(requestType)) {
            resultMap = this.findPicture(paramsMap);
        } else {
            resultMap = new HashMap<>();
            resultMap.put("code", "0");
            resultMap.put("msg", "requestType类型参数无效");
        }

        return resultMap;
    }

    /**
     * 启动流程
     * @param paramsMap
     * @return
     */
    private Map<String, Object> start(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String userId = paramsMap.get("userId");
        String money = paramsMap.get("money");

        String processInstanceId = iMyBizc.startProcess(userId, money);

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("processId", processInstanceId);
        return resultMap;
    }

    /**
     * 查询任务
     * @param paramsMap
     * @return
     */
    private Map<String, Object> queryTask(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String assignee = paramsMap.get("assignee");

        List<Task> listData = iMyBizc.getTasks(assignee);
        List<Map<String, String>> retList = new ArrayList<>();
        Map<String, String> tempMap;
        for (Task forTK : listData) {
            tempMap = new HashMap<>();
            tempMap.put("id", forTK.getId());
            tempMap.put("name", forTK.getName());
            tempMap.put("processId", forTK.getProcessInstanceId());
            tempMap.put("assignee", forTK.getAssignee());
            retList.add(tempMap);
        }
        resultMap.put("data", retList);

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 查询历史任务
     * @param paramsMap
     * @return
     */
    private Map<String, Object> queryHistory(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String assignee = paramsMap.get("assignee");

        List<HistoricTaskInstance> listData = iMyBizc.getHistoryTasks(assignee);
        List<Map<String, String>> retList = new ArrayList<>();
        Map<String, String> tempCodeMap;
        Map<String, String> tempMap;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (HistoricTaskInstance forTK : listData) {
            tempCodeMap = iMyBizc.processStatue(forTK.getProcessInstanceId());
            tempMap = new HashMap<>();
            tempMap.put("id", forTK.getId());
            tempMap.put("assignee", forTK.getAssignee());
            tempMap.put("name", forTK.getName());
            tempMap.put("processId", forTK.getProcessInstanceId());
            if (forTK.getStartTime() != null) {
                tempMap.put("startTime", sf.format(forTK.getStartTime()));
            } else {
                tempMap.put("startTime", null);
            }
            if (forTK.getEndTime() != null) {
                tempMap.put("endTime", sf.format(forTK.getEndTime()));
            } else {
                tempMap.put("endTime", null);
            }
            tempMap.put("statue", tempCodeMap.get("code"));
            tempMap.put("mark", tempCodeMap.get("msg"));
            retList.add(tempMap);
        }
        resultMap.put("data", retList);

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 查询所有流程信息
     * @param paramsMap
     * @return
     */
    private Map<String, Object> queryProcessHistory(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String assignee = paramsMap.get("assignee");

        List<ProcessDefinition> listData = iMyBizc.getProcessDefinition(assignee);
        List<Map<String, String>> retList = new ArrayList<>();
        Map<String, String> tempMap;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ProcessDefinition forTK : listData) {
            tempMap = new HashMap<>();
            tempMap.put("id", forTK.getId());
            tempMap.put("name", forTK.getName());
            tempMap.put("key", forTK.getKey());
            tempMap.put("version", forTK.getVersion() + "");
            tempMap.put("objId", forTK.getDeploymentId());
            tempMap.put("isStop", forTK.isSuspended() + "");
            retList.add(tempMap);
        }
        resultMap.put("data", retList);

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 审批
     * @param paramsMap
     * @return
     */
    private Map<String, Object> apply(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String taskId = paramsMap.get("taskId");
        String outcome = paramsMap.get("outcome");

        String retCode = iMyBizc.apply(taskId, outcome);

        if ("2".equals(retCode)) {
            code = "0";
            msg = "流程不存在";
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 流程下发下一环节
     * @param paramsMap
     * @return
     */
    private Map<String, Object> send(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String taskId = paramsMap.get("taskId");
        String money = paramsMap.get("money");

        String retCode = iMyBizc.sendProcess(taskId, money);

        if ("2".equals(retCode)) {
            code = "0";
            msg = "流程不存在";
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 签收流程任务
     * @param paramsMap
     * @return
     */
    private Map<String, Object> claim(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String taskId = paramsMap.get("taskId");
        String userId = paramsMap.get("userId");

        Map<String, String> tempMap = iMyBizc.claim(taskId, userId);

        if ("2".equals(tempMap.get("code"))) {
            code = "0";
            msg = "流程不存在";
        } else if ("3".equals(tempMap.get("code"))) {
            code = "0";
            msg = "流程已被" + tempMap.get("msg") + "签收";
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 终止流程
     * @param paramsMap
     * @return
     */
    private Map<String, Object> delete(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String processInstanceId = paramsMap.get("processInstanceId");
        String reason = paramsMap.get("reason");

        String retCode = iMyBizc.delProcess(processInstanceId, reason);

        if ("2".equals(retCode)) {
            code = "0";
            msg = "流程不存在";
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 暂停/挂起流程
     * @param paramsMap
     * @return
     */
    private Map<String, Object> stop(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String processInstanceId = paramsMap.get("processInstanceId");

        String retCode = iMyBizc.suspendProcess(processInstanceId);

        if ("2".equals(retCode)) {
            code = "0";
            msg = "流程不存在";
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 启动暂停/挂起流程
     * @param paramsMap
     * @return
     */
    private Map<String, Object> startStop(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String processInstanceId = paramsMap.get("processInstanceId");

        String retCode = iMyBizc.activateProcess(processInstanceId);

        if ("2".equals(retCode)) {
            code = "0";
            msg = "流程不存在";
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 导出流程图及流程当前所在位置
     * @param paramsMap
     * @return
     */
    private Map<String, Object> findPicture(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "成功";

        String processInstanceId = paramsMap.get("processInstanceId");

        Map<String, String> tempMap = iMyBizc.findPicture(processInstanceId);

        if ("0".equals(tempMap.get("code"))) {
            code = "0";
            msg = "导出失败";
        } else {
            resultMap.put("img", tempMap.get("img"));
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }
}
