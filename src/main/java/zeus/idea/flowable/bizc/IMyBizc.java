package zeus.idea.flowable.bizc;

import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.List;
import java.util.Map;

/**
 * 接口名：天子流程测试service
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-12-01 10:14
 * <p>
 * 修改人：           修改时间：
 */
public interface IMyBizc {

    /**
     * 发起流程
     */
    public String startProcess(String taskUser, String money);

    /**
     * 下发流程
     */
    public String sendProcess(String taskId, String money);

    /**
     * 获取任务号
     * @param assignee
     * @return
     */
    public List<Task> getTasks(String assignee);

    /**
     * 查询已办任务信息
     * @param assignee
     * @return
     */
    public List<HistoricTaskInstance> getHistoryTasks(String assignee);

    /**
     *查询所有流程
     * @param assignee
     * @return
     */
    public List<ProcessDefinition> getProcessDefinition(String assignee);

    /**
     * 判断流程状态，1是在途，2是结束，3是挂起，4是终止
     * @param processInstanceId
     * @return
     */
    public Map<String, String> processStatue(String processInstanceId);

    /**
     * 终止流程，1是成功，0是失败，2是流程不存在
     * @param processInstanceId
     * @param reason
     * @return
     */
    public String delProcess(String processInstanceId, String reason);

    /**
     * 暂停流程任务
     * @param processInstanceId
     * @return
     */
    public String suspendProcess(String processInstanceId);

    /**
     * 重新开启挂起的流程
     * @param processInstanceId
     * @return
     */
    public String activateProcess(String processInstanceId);

    /**
     * 审批任务
     * @param taskId
     * @return
     */
    public String apply(String taskId, String outcome);

    /**
     * 签收任务
     * @param taskId
     * @param userId
     * @return
     */
    public Map<String, String> claim(String taskId, String userId);

    /**
     * 导出流程图及流程当前所在位置
     * @param processInstanceId
     * @return
     */
    public Map<String, String> findPicture(String processInstanceId);

}
