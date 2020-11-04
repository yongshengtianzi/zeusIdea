package zeus.idea.filter.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zeus.idea.filter.bizc.FilterBizc;
import zeus.idea.filter.entity.FilterUserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名：过滤异常处理
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-04-20 11:27
 * <p>
 * 修改人：           修改时间：
 */
@Controller
@RequestMapping("/zeus/filter")
public class FilterController {
    // 在Java类中创建 logger 实例
    private static final Logger logger = LoggerFactory.getLogger(FilterController.class);

    @Autowired
    private FilterBizc filterBizc;

    /**
     * 提醒用户登录
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public Map<String, Object> pleaseLogin() {
        Map<String, Object> resultMap = resultMap = new HashMap<String,Object>();
        resultMap.put("code", "qx03");
        resultMap.put("msg", "请登录");
        return resultMap;
    }

    /**
     * 提醒用户登录成功
     * @return
     */
    @RequestMapping(value = "success")
    @ResponseBody
    public Map<String, Object> success() {
        Map<String, Object> resultMap = resultMap = new HashMap<String,Object>();
        resultMap.put("code", "1");
        resultMap.put("msg", "登录成功");
        return resultMap;
    }

    /**
     * 提醒用户没有权限
     * @return
     */
    @RequestMapping(value = "notperm")
    @ResponseBody
    public Map<String, Object> notperm() {
        Map<String, Object> resultMap = resultMap = new HashMap<String,Object>();
        resultMap.put("code", "qx04");
        resultMap.put("msg", "权限不足");
        return resultMap;
    }

    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = "userLogin")
    @ResponseBody
    public Map<String, Object> userLogin(@RequestBody Map<String, String> paramsMap,
                                         HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = resultMap = new HashMap<String,Object>();
        String code = "1";
        String msg = "";

        String userName = paramsMap.get("username");//用户名
        String password = paramsMap.get("password");//密码

        if (StringUtils.isBlank(userName)) {
            code = "2";
            msg = "用户名不能为空";
        }

        if ("1".equals(code)) {
            String ip = request.getRemoteAddr();
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password, ip);
            try {
                subject.login(token);//使用shiro进行用户校验
                FilterUserEntity fue = (FilterUserEntity) subject.getPrincipal();
                String tokenStr = (String) subject.getSession().getId();
                filterBizc.saveUserRe(tokenStr, fue);//保存登录记录
                //查询该登录用户角色数据，从shiro获取

                msg = "成功";
            } catch (LockedAccountException e) {
                code = "qx02";
                msg = "账号被锁定";
                token.clear();
            }catch (UnknownAccountException e) {
                code = "qx01";
                msg = "用户或密码不正确";
                token.clear();
            } catch (AuthenticationException e) {
                code = "qx01";
                msg = "用户或密码不正确";
                token.clear();
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 用户退出登录
     * @return
     */
    @RequestMapping(value = "loginOut")
    @ResponseBody
    public Map<String, Object> loginOut() {
        Map<String, Object> resultMap = resultMap = new HashMap<String,Object>();
        String code = "1";
        String msg = "";


        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }
}
