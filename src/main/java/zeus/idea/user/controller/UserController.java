package zeus.idea.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zeus.idea.user.bizc.IUserBizc;
import zeus.idea.user.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
@RequestMapping("/user")
public class UserController {
    // 在Java类中创建 logger 实例
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserBizc iUserBizc;

    /**
     *用户管理控制类
     * @param paramsMap
     * @return
     */
    @RequestMapping(value = "usersjmanage")
    @ResponseBody
    public Map<String, Object> expManage(@RequestBody Map<String, String> paramsMap, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> resultMap = null;

        String ip = request.getRemoteAddr();//获取客户端的ip
        paramsMap.put("ip", ip);

        String requestType = paramsMap.get("requestType");

        if ("login".equals(requestType)) {
            resultMap = this.login(paramsMap);
        } else if("loginOut".equals(requestType)) {
            String token = request.getHeader("token");
            String userNo = request.getHeader("userNo");
            paramsMap.put("userNo", userNo);
            paramsMap.put("token", token);
            resultMap = this.loginOut(paramsMap);
        } else if("signIn".equals(requestType)) {
            resultMap = this.signIn(paramsMap);
        } else {
            resultMap = new HashMap<String,Object>();
            resultMap.put("code", "0");
            resultMap.put("msg", "requestType类型参数无效");
        }

        return resultMap;
    }

    /**
     * 用户注册
     * @param paramsMap
     * @return
     */
    private Map<String, Object> signIn(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "";

        String coolName = paramsMap.get("coolName");
        String phoneNo = paramsMap.get("phoneNo");
        String email = paramsMap.get("email");
        String password = paramsMap.get("password");
        String ip = paramsMap.get("ip");

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
            } else if (iUserBizc.isHaveUser(phoneNo, email)) {
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
                iUserBizc.signIn(ue);
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
    private Map<String, Object> login(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "";

        String phoneNo = paramsMap.get("phoneNo");
        String email = paramsMap.get("email");
        String password = paramsMap.get("password");
        String ip = paramsMap.get("ip");

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
                String retCode = iUserBizc.login(ue);
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
    private Map<String, Object> loginOut(Map<String, String> paramsMap) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String code = "1";
        String msg = "";

        String token = paramsMap.get("token");
        String userNo = paramsMap.get("userNo");

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
                String retCode = iUserBizc.loginOut(userNo, userNo, token, "01");
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
}
