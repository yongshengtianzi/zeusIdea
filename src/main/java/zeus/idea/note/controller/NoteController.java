package zeus.idea.note.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zeus.idea.common.utils.CheckUtil;
import zeus.idea.common.utils.UserUtil;
import zeus.idea.note.bizc.NoteBizc;
import zeus.idea.note.entity.NoteEntity;
import zeus.idea.note.entity.SelectEntity;

import java.util.*;

/**
 * 类名：NoteController
 * 公司：-----智讯云-----
 * 功能说明：
 * 笔记显示处理controller
 * <p>
 * 作者：jinyang.wang      创建时间：2020/12/27 8:36
 * <p>
 * 修改人：           修改时间：
 */
@Controller
@RequestMapping("/note/manage")
public class NoteController {
    // 在Java类中创建 logger 实例
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private NoteBizc noteBizc;

    @Autowired
    private StringRedisTemplate redisTemplate0;//保存笔记信息的Redis库

    /**
     * 方法功能说明：查询笔记列表数据
     *
     * @param entity
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/11 19:12
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "querynotelist")
    @ResponseBody
    public Map<String, Object> queryNoteList(@RequestBody SelectEntity entity) {
        Map<String, Object> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        if ("1".equals(code)) {
            try {
                if (entity.getNodeInfoKey() != null && entity.getNodeInfoKey().size() == 0) {
                    entity.setNodeInfoKey(null);
                }
                entity.setCreateUserId(UserUtil.getUserId());
                int count = noteBizc.findListCount(entity);
                if (count > 0) {
                    List<NoteEntity> data = noteBizc.findList(entity);
                    retMap.put("dataList", data);
                    retMap.put("tableTotal", count);
                    msg = "查询成功";
                } else {
                    code = "3";//未查到数据
                    msg = "未查到笔记数据";
                }
            } catch (Exception e) {
                logger.error("查询笔记信息异常：" + e.getMessage());
                code = "0";
                msg = "查询笔记信息异常！";
            }
        }

        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：保存笔记数据
     *
     * @param entity
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/11 19:38
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "savenoteinfo")
    @ResponseBody
    public Map<String, Object> saveNoteInfo(@RequestBody NoteEntity entity) {
        Map<String, Object> resultMap = new HashMap<>();
        String code = "1";
        String msg = "";

        if (StringUtils.isBlank(entity.getNodeInfoTitle())) {
            code = "2";
            msg = "笔记主题不能为空；";
        }
        if (entity.getNodeInfoKey() == null || entity.getNodeInfoKey().isEmpty()) {
            code = "2";
            msg = msg + "笔记关键字不能为空；";
        }
        if (StringUtils.isBlank(entity.getNodeInfoContent())
                && StringUtils.isBlank(entity.getNodeInfoUrl())) {
            code = "2";
            msg = msg + "笔记内容和原文链接不能都为空；";
        }

        if ("1".equals(code)) {
            try {
                noteBizc.saveUpdNote(entity, "1", UserUtil.getUserEntity());
                msg = "笔记保存成功";
            } catch (Exception e) {
                logger.error("笔记，保存笔记信息异常：", e);
                code = "0";
                msg = "保存笔记信息失败";
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 方法功能说明：更新笔记数据
     *
     * @param entity
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/11 19:55
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "updnoteinfo")
    @ResponseBody
    public Map<String, Object> updNoteInfo(@RequestBody NoteEntity entity) {
        Map<String, Object> resultMap = new HashMap<>();
        String code = "1";
        String msg = "";

        if (StringUtils.isBlank(entity.getNodeInfoTitle())) {
            code = "2";
            msg = "笔记主题不能为空；";
        }
        if (entity.getNodeInfoKey() == null || entity.getNodeInfoKey().isEmpty()) {
            code = "2";
            msg = msg + "笔记关键字不能为空；";
        }
        if (StringUtils.isBlank(entity.getNodeInfoContent())
                && StringUtils.isBlank(entity.getNodeInfoUrl())) {
            code = "2";
            msg = msg + "笔记内容和原文链接不能都为空；";
        }

        if ("1".equals(code)) {
            try {
                noteBizc.saveUpdNote(entity, "2", UserUtil.getUserEntity());
                msg = "笔记更新成功";
            } catch (Exception e) {
                logger.error("笔记，更新笔记信息异常：", e);
                code = "0";
                msg = "更新笔记信息失败";
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 方法功能说明：删除笔记数据
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/11 20:12
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "delnoteinfo")
    @ResponseBody
    public Map<String, Object> delNoteInfo(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> resultMap = new HashMap<>();
        String code = "1";
        String msg = "";

        String nodeInfoId = paramsMap.get("nodeInfoId");
        if (StringUtils.isBlank(nodeInfoId)) {
            code = "2";
            msg = "请选择要删除的笔记！";
        }

        if ("1".equals(code)) {
            try {
                noteBizc.delNote(nodeInfoId);
                msg = "删除笔记信息成功";
            } catch (Exception e) {
                logger.error("笔记，删除笔记信息异常：", e);
                code = "0";
                msg = "删除笔记信息失败";
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 方法功能说明：查询
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/3/14 16:33
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "querynotekey")
    @ResponseBody
    public Map<String, Object> queryNoteKey(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> resultMap = new HashMap<>();
        String code = "1";
        String msg = "";

        String quKey = paramsMap.get("key");

        if (StringUtils.isBlank(quKey)) {
            code = "1";
            msg = "成功";
        } else {
            //校验特殊字符
            if (CheckUtil.tszfYW(quKey)) {
                code = "2";
                msg = "关键字中不能包含特殊字符，如英文标点符号；";
            } else {
                try {
                    Set<String> keys = redisTemplate0.keys("*" + quKey + "*");
                    resultMap.put("dataList", keys);
                } catch (Exception e) {
                    logger.error("查询笔记关键字信息异常：" + e.getMessage());
                    code = "0";
                    msg = "查询笔记关键字信息异常！";
                }
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

}
