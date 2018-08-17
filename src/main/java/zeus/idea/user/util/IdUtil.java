package zeus.idea.user.util;

import java.util.UUID;

/**
 * 类名：用户模块公共方法类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-07-17 18:48
 * <p>
 * 修改人：           修改时间：
 */
public class IdUtil {
    /**
     * 获取token
     * @return
     */
    public String getToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取UUID
     * @return
     */
    public String getUUId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
