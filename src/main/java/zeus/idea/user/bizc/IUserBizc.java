package zeus.idea.user.bizc;

import zeus.idea.user.entity.UserEntity;

/**
 * 接口名：用户管理逻辑处理类-接口
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-07-15 17:09
 * <p>
 * 修改人：           修改时间：
 */
public interface IUserBizc {
    /**
     * 获取序列
     * @return
     */
    public String getSEQ();

    /**
     * 用户是否已经存在，不存在false，存在true
     * @param phoneNo
     * @param email
     * @return
     */
    public boolean isHaveUser(String phoneNo, String email);

    /**
     * 用户注册
     * @param ue
     * @return
     */
    public String signIn(UserEntity ue);

    /**
     * 用户登录，0是用户不存在，1是登录成功，2是密码不匹配
     * @param ue
     * @return
     */
    public String login(UserEntity ue);

    /**
     * 用户退出登录，0是登录记录信息不存在，1是退出登录成功
     * @param userNo
     * @param doUserNo
     * @param token
     * @param loginOutType
     * @return
     */
    public String loginOut(String userNo, String doUserNo, String token, String loginOutType);
}
