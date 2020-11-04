package zeus.idea.sysuser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zeus.idea.sysuser.bizc.UserBizc;
import zeus.idea.sysuser.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：用户管理类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-07-15 16:42
 * <p>
 * 修改人：           修改时间：
 */
@Controller
@RequestMapping("/zeus/user")
public class UserController {
    // 在Java类中创建 logger 实例
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserBizc userBizc;

    /**
     * 用户注册
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "signIn")
    @ResponseBody
    public Map<String, Object> signIn(@RequestBody Map<String, String> paramsMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "";

        String ip = request.getRemoteAddr();//获取客户端的ip
        String coolName = paramsMap.get("coolName");
        String phoneNo = paramsMap.get("phoneNo");
        String email = paramsMap.get("email");
        String password = paramsMap.get("password");

        if (coolName == null || "".equals(coolName)) {
            code = "2";
            msg = msg + "用户昵称不能为空；";
        } else {
            if (coolName.length() > 21) {
                code = "2";
                msg = msg + "用户昵称长度不能超过21位；";
            }
        }
        if ((phoneNo == null || "".equals(phoneNo)) && (email == null || "".equals(email))) {
            code = "2";
            msg = msg + "手机号或Email不能都为空；";
        } else {
            if (email != null && !"".equals(email) && email.length() > 30) {
                code = "2";
                msg = msg + "Email长度不能超过32位；";
            } else if (userBizc.isHaveUser(phoneNo, email)) {
                code = "2";
                msg = msg + "Email或手机号已经注册；";
            }
        }
        if (password == null || "".equals(password)) {
            code = "2";
            msg = msg + "密码不能为空；";
        } else {
            if (password.length() > 21) {
                code = "2";
                msg = msg + "密码长度不能超过30位；";
            }
        }

        if ("1".equals(code)) {
            UserEntity ue = new UserEntity();
            ue.setCoolName(coolName);
            ue.setPhoneNo(phoneNo);
            ue.setEmail(email);
            ue.setPassword(password);
            ue.setUserIP(ip);
            try {
                userBizc.signIn(ue);
                resultMap.put("token", ue.getToken());
                resultMap.put("userNo", ue.getUserNo());
                resultMap.put("coolName", coolName);
                msg = "成功";
            } catch (Exception e) {
                logger.error(e.getMessage());
                code = "0";
                msg = "保存用户信息失败！";
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 用户登录
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, String> paramsMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "";

        String ip = request.getRemoteAddr();//获取客户端的ip
        String phoneNo = paramsMap.get("phoneNo");
        String email = paramsMap.get("email");
        String password = paramsMap.get("password");

        if ((phoneNo == null || "".equals(phoneNo)) && (email == null || "".equals(email))) {
            code = "2";
            msg = msg + "手机号或Email不能都为空；";
        }
        if (password == null || "".equals(password)) {
            code = "2";
            msg = msg + "密码不能为空；";
        }

        if ("1".equals(code)) {
            UserEntity ue = new UserEntity();
            ue.setPhoneNo(phoneNo);
            ue.setEmail(email);
            ue.setPassword(password);
            ue.setUserIP(ip);
            try {
                String retCode = userBizc.login(ue);
                if ("1".equals(retCode)) {
                    resultMap.put("token", ue.getToken());
                    resultMap.put("userNo", ue.getUserNo());
                    resultMap.put("coolName", ue.getCoolName());
                    msg = "成功";
                } else {
                    code = "2";
                    msg = "账号密码不匹配或账号不存在！";
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                code = "0";
                msg = "用户登录失败！";
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 用户退出
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "loginOut")
    @ResponseBody
    public Map<String, Object> loginOut(@RequestBody Map<String, String> paramsMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "";

        String ip = request.getRemoteAddr();//获取客户端的ip
        paramsMap.put("ip", ip);
        String token = request.getHeader("token");
        String userNo = request.getHeader("userNo");

        if (token == null || "".equals(token)) {
            code = "2";
            msg = "用户登录记录信息不存在！";
        }
        if (userNo == null || "".equals(userNo)) {
            code = "2";
            msg = "用户登录记录信息不存在！";
        }

        if ("1".equals(code)) {
            try {
                String retCode = userBizc.loginOut(userNo, userNo, token, "01");
                if ("1".equals(retCode)) {
                    msg = "成功";
                } else {
                    code = "2";
                    msg = "用户登录记录信息不存在！";
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                code = "0";
                msg = "用户退出登录失败！";
            }
        }

        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 方法功能说明：查询所有用户
     *
     * @param paramsMap
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/9/13 10:53
     *
     * 修改人:          修改日期:
     */
    @RequestMapping(value = "queryList")
    @ResponseBody
    public Map<String, Object> queryList(@RequestBody Map<String, String> paramsMap) {
        Map<String, Object> resultMap = new HashMap<>();
        String code = "1";
        String msg = "";

        String coolName = paramsMap.get("coolName");
        String userNo = paramsMap.get("userNo");

        try {
            List<UserEntity> listData = userBizc.queryList(coolName, userNo);
            resultMap.put("listData", listData);
            code = "1";
            msg = "成功";
        } catch (Exception e) {
            logger.error("查询用户信息异常：", e);
            code = "0";
            msg = "失败";
        }
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }
}
