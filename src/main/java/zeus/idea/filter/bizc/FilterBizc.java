package zeus.idea.filter.bizc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.filter.dao.IFilterDao;
import zeus.idea.filter.entity.FilterUserEntity;
import zeus.idea.sysuser.entity.UserEntity;
import zeus.idea.sysuser.util.IdUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名：过滤异常处理
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-04-20 21:25
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class FilterBizc {

    @Autowired
    private IFilterDao iFilterDao;

    /**
     * 用户登录查询用户信息
     * @param userName
     * @return
     */
    public FilterUserEntity queryUserInfo(String userName) {
        return iFilterDao.queryUserInfo(userName);
    }

    /**
     * 保存用户登录记录信息
     * @param token
     * @param fue
     * @return
     */
    @Transactional(readOnly = false)
    public String saveUserRe(String token, FilterUserEntity fue) {

        IdUtil iu = new IdUtil();
        UserEntity ue = new UserEntity();
        ue.setRecordId(iu.getUUId());
        ue.setUserId(fue.getUserId());
        ue.setUserNo(fue.getUserNo());
        ue.setPhoneNo(fue.getPhoneNo());
        ue.setEmail(fue.getEmail());
        ue.setUserIP(fue.getIp());
        ue.setToken(token);

        iFilterDao.saveLoginRecord(ue);

        return "1";
    }

    /**
     * 退出登录
     * @return
     */
    public Map<String, String> loginOut() {
        Map<String, String> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        return retMap;
    }
}
