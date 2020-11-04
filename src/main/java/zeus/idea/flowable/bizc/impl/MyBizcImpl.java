package zeus.idea.flowable.bizc.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.flowable.bizc.IMyBizc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：天子流程测试bizc
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-12-01 10:15
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class MyBizcImpl implements IMyBizc {

    // 在Java类中创建 logger 实例
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBizcImpl.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    /**
     * 发起流程
     */
    @Transactional(readOnly = false)
    public String startProcess(String taskUser, String money) {
        Map<String, Object> map = new HashMap<>();
        map.put("taskUser", taskUser);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return processInstance.getId();
    }

    /**
     * 下发流程
     */
    @Transactional(readOnly = false)
    public String sendProcess(String taskId, String money) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return "2";//流程不存在
        }

        Map<String, Object> map = new HashMap<>();
        map.put("money", money);
        taskService.complete(taskId, map);
        return "1";
    }

    /**
     * 获取任务号
     * @param assignee
     * @return
     */
    @Transactional(readOnly = false)
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    /**
     * 查询已办任务信息
     * @param assignee
     * @return
     */
    public List<HistoricTaskInstance> getHistoryTasks(String assignee) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).list();
        return list;
    }

    /**
     *查询所有流程-判断流程是否暂停有用
     * @param assignee
     * @return
     */
    public List<ProcessDefinition> getProcessDefinition(String assignee) {
        List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery().list();
        return list;
    }

    /**
     * 终止流程，1是成功，0是失败，2是流程不存在
     * @param processInstanceId
     * @param reason
     * @return
     */
    @Transactional(readOnly = false)
    public String delProcess(String processInstanceId, String reason) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (task == null) {
            return "2";//流程不存在
        }

        runtimeService.deleteProcessInstance(processInstanceId, reason);
        return "1";
    }

    /**
     * 暂停流程任务
     * @param processInstanceId
     * @return
     */
    @Transactional(readOnly = false)
    public String suspendProcess(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (task == null) {
            return "2";//流程不存在
        }

        runtimeService.suspendProcessInstanceById(processInstanceId);
        return "1";
    }

    /**
     * 重新开启挂起的流程
     * @param processInstanceId
     * @return
     */
    @Transactional(readOnly = false)
    public String activateProcess(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (task == null) {
            return "2";//流程不存在
        }

        runtimeService.activateProcessInstanceById(processInstanceId);
        return "1";
    }

    /**
     * 判断流程状态，0是流程判断出异常，1是在途，2是结束，3是挂起，4是终止
     * @param processInstanceId
     * @return
     */
    public Map<String, String> processStatue(String processInstanceId) {
        String code;
        String msg;
        try {
            List<ProcessInstance> piList = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .list();
            if (piList == null || piList.size() <= 0) {
                List<HistoricProcessInstance> hpiList = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .deleted()
                        .list();
                if (hpiList == null || hpiList.size() <= 0) {
                    code = "2";//流程已经结束
                    msg = "流程已经结束";
                } else {
                    HistoricProcessInstance hpi = hpiList.get(0);
                    code = "4";//流程已经结束
                    msg = hpi.getDeleteReason();
                }
            } else {
                ProcessInstance pi = piList.get(0);
                List<ProcessDefinition> pdList = repositoryService.createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).list();

                //流程未结束，判断流程是否挂起
                //挂起判断现在不准确需要调整
                boolean flag = pdList.get(0).isSuspended();
                code = "1";//流程在途
                msg = "流程在途";
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            code = "0";//流程判断出异常
            msg = "流程判断出异常";
        }

        Map<String, String> retMap = new HashMap<String, String>();
        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 审批任务
     * @param taskId
     * @param outcome
     * @return
     */
    @Transactional(readOnly = false)
    public String apply(String taskId, String outcome) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return "2";//流程不存在
        }

        Map<String, Object> map = new HashMap<>();
        map.put("outcome", outcome);
        taskService.complete(taskId, map);
        return "1";
    }

    /**
     * 签收任务，1是成功，2是流程不存在，3是流程已经被签收
     * @param taskId
     * @param userId
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, String> claim(String taskId, String userId) {
        String code;
        String msg;
        List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();
        if (taskList == null || taskList.size() <= 0) {
            code = "2";//流程不存在
            msg = "流程不存在";
        } else {
            String tempUserId = taskList.get(0).getAssignee();
            if (StringUtils.isBlank(tempUserId)) {
                taskService.claim(taskId, userId);
                code = "1";
                msg = "成功";
            } else {
                code = "3";
                msg = tempUserId;
            }
        }

        Map<String, String> retMap = new HashMap<>();
        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 导出流程图及流程当前所在位置
     * @param processInstanceId
     * @return
     */
    public Map<String, String> findPicture(String processInstanceId) {
        Map map=new HashMap();
        try {
            HistoricProcessInstance historicProcessInstance=historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

            BpmnModel bpmnModel=repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());

            List<String> activityIds = new ArrayList<>();
            //判断流程实例是否已经结束
            if (historicProcessInstance.getEndTime()==null){
                //获取该流程实例的当前活动节点
                //在得到的流程图片上会以红框高亮显示
                activityIds= runtimeService.getActiveActivityIds(processInstanceId);
            }
            //获取流程图信息
            InputStream inputStream = processEngineConfiguration.getProcessDiagramGenerator().generateDiagram(bpmnModel, "png", activityIds, false);
            //转换方便页面展示图拍呢
            byte[] bytes = IOUtils.toByteArray(inputStream);
            String img=new String(Base64.encodeBase64(bytes));
            //获取该流程实例的所有历史活动，即经历的各个审批节点
            List<HistoricActivityInstance> list=historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
            map.put("code","1");
            map.put("img",img);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("code","0");
        map.put("img",null);
        return map;
    }
}
