package zeus.idea.filter.bizc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.filter.dao.IPermissionEnableDao;
import zeus.idea.filter.entity.PermissionEnableEntity;
import zeus.idea.filter.entity.FilterUserEntity;
import zeus.idea.filter.entity.UserRoleEntity;

import java.util.List;

/**
 * 类名：项目启动时，处理权限资源加载
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-04-14 09:20
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class PermissionEnableBizc {

    @Autowired
    private IPermissionEnableDao iPermissionEnableDao;

    /**
     * 查询权限关系数据，供服务启动时使用
     * @return
     */
    public List<PermissionEnableEntity> queryPermRelation() {
        return iPermissionEnableDao.queryPermRelation();
    }

    /**
     * 查询用户角色权限关系，供用户登录时使用
     * @return
     */
    public List<UserRoleEntity> queryUserRelation(String userNo) {
        return iPermissionEnableDao.queryUserRelation(userNo);
    }

    /**
     * 查询用户信息
     * @param userName
     * @return
     */
    public FilterUserEntity queryUserInfo(String userName) {
        return iPermissionEnableDao.queryUserInfo(userName);
    }
}
