package zeus.idea.common.utils;

import java.util.UUID;

/**
 * 类名：生成UUID类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-03-26 19:00
 * <p>
 * 修改人：           修改时间：
 */
public class UuidUtil {
    /**
     * 获取带“-”的UUID
     * @return
     */
    public static String getUUIDLine() {
        UUID uuid= UUID.randomUUID();
        return uuid.toString();
    }
}
