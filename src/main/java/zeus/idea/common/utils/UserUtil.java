package zeus.idea.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import zeus.idea.filter.entity.FilterUserEntity;

/**
 * 类名：UserUtil
 * 公司：-----智讯云-----
 * 功能说明：
 * 用户公共使用类
 * <p>
 * 作者：jinyang.wang      创建时间：2021/1/9 10:38
 * <p>
 * 修改人：           修改时间：
 */
public class UserUtil {

    /**
     * 方法功能说明：获取用户id
     *
     * @param
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/18 15:45
     *
     * 修改人:          修改日期:
     */
    public static String getUserId() {
        Subject subject = SecurityUtils.getSubject();
        FilterUserEntity fue = (FilterUserEntity) subject.getPrincipal();
        String userId;
        if (fue == null) {
            userId = "";
        } else {
            userId = fue.getUserId();
        }

        return userId;
    }

    /**
     * 方法功能说明：获取用户编号
     *
     * @param
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/11 19:46
     *
     * 修改人:          修改日期:
     */
    public static String getUserNo() {
        Subject subject = SecurityUtils.getSubject();
        FilterUserEntity fue = (FilterUserEntity) subject.getPrincipal();
        String userNo;
        if (fue == null) {
            userNo = "";
        } else {
            userNo = fue.getUserNo();
        }

        return userNo;
    }

    /**
     * 方法功能说明：获取用户信息FilterUserEntity
     *
     * @param
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/18 15:46
     *
     * 修改人:          修改日期:
     */
    public static FilterUserEntity getUserEntity() {
        Subject subject = SecurityUtils.getSubject();
        FilterUserEntity fue = (FilterUserEntity) subject.getPrincipal();

        return fue;
    }

}
