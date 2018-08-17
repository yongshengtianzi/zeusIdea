package zeus.idea.user.bizc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.user.bizc.IUserBizc;
import zeus.idea.user.dao.IUserDao;
import zeus.idea.user.entity.UserEntity;
import zeus.idea.user.entity.UserRecordEntity;
import zeus.idea.user.util.IdUtil;
import zeus.idea.user.util.UserEHCacheUtil;

import java.util.List;

/**
 * 类名：用户管理逻辑处理类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-07-15 17:12
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class UserBizcImpl implements IUserBizc {

    // 在Java类中创建 logger 实例
    private static final Logger logger = LoggerFactory.getLogger(UserBizcImpl.class);

    @Autowired
    private IUserDao iUserDao;

    @Autowired
    private EhCacheCacheManager cacheCacheManager;

    /**
     * 获取序列
     * @return
     */
    public String getSEQ() {
        return iUserDao.getSEQ();
    }

    /**
     * 用户是否已经存在，不存在false，存在true
     * @param phoneNo
     * @param email
     * @return
     */
    public boolean isHaveUser(String phoneNo, String email) {
        UserEntity ue = new UserEntity();
        ue.setPhoneNo(phoneNo);
        ue.setEmail(email);
        UserEntity tempUE = iUserDao.queryUser(ue);
        if (tempUE == null || tempUE.getUserNo() == null || "".equals(tempUE.getUserNo())) {
            return false;
        }
        return true;
    }

    /**
     * 用户注册
     * @param ue
     * @return
     */
    @Transactional(readOnly = false)
    public String signIn(UserEntity ue) {
        /*****保存用户注册信息*****/
        String userId = this.getSEQ();
        String userNo = this.getSEQ();
        ue.setUserId(userId);
        ue.setUserNo(userNo);
        iUserDao.saveUser(ue);
        /*****保存用户登录信息*****/
        this.saveUserRe(ue);

        return "1";
    }

    /**
     * 用户登录，0是用户不存在，1是登录成功，2是密码不匹配
     * @param ue
     * @return
     */
    @Transactional(readOnly = false)
    public String login(UserEntity ue) {
        /*****获取用户信息*****/
        UserEntity tempUE = iUserDao.queryUser(ue);
        if (tempUE == null || tempUE.getUserNo() == null || "".equals(tempUE.getUserNo())) {
            return "0"; //用户不存在
        } else {
            String password = tempUE.getPassword();
            if (!ue.getPassword().equals(password)) {
                return "2";
            } else {
                ue.setUserId(tempUE.getUserId());
                ue.setUserNo(tempUE.getUserNo());
                ue.setCoolName(tempUE.getCoolName());

                /*****判断此用户是否已经登录，然后强制下线已经登录的用户*****/
                List<String> tempRe = iUserDao.queryUerLoginRe(tempUE.getUserNo());
                for (String for_str : tempRe) {
                    this.loginOut(tempUE.getUserNo(), tempUE.getUserNo(), for_str, "03");
                }
                /*****保存用户登录信息*****/
                this.saveUserRe(ue);
                return "1";
            }
        }
    }

    /**
     * 保存用户登录记录
     * @param ue
     */
    private void saveUserRe(UserEntity ue) {
        IdUtil iu = new IdUtil();
        String token = iu.getToken();
        ue.setToken(token);
        ue.setRecordId(iu.getUUId());
        iUserDao.saveLoginRecord(ue);
        UserRecordEntity ure = new UserRecordEntity();
        ure.setUserNo(ue.getUserNo());
        ure.setEmail(ue.getEmail());
        ure.setPhoneNo(ue.getPhoneNo());
        ure.setToken(ue.getToken());
        ure.setUserIP(ue.getUserIP());
        UserEHCacheUtil ueu = new UserEHCacheUtil(cacheCacheManager);
        ueu.setCache(ue.getToken(), ure);
    }

    /**
     * 用户退出登录，0是登录记录信息不存在，1是退出登录成功
     * @param userNo
     * @param doUserNo
     * @param token
     * @param loginOutType
     * @return
     */
    @Transactional(readOnly = false)
    public String loginOut(String userNo, String doUserNo, String token, String loginOutType) {
        int isHave = iUserDao.isHaveLoginRe(userNo, token);
        if (isHave > 0) {
            iUserDao.updateLoginRecord(loginOutType, doUserNo, userNo, token);
            UserEHCacheUtil ueu = new UserEHCacheUtil(cacheCacheManager);
            ueu.delCache(token);
            return "1";
        } else {
            return "0";
        }
    }
}
