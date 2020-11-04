package zeus.idea.filter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zeus.idea.filter.entity.PermissionEnableEntity;
import zeus.idea.filter.entity.FilterUserEntity;
import zeus.idea.filter.entity.UserRoleEntity;

import java.util.List;

/**
 * 接口名：项目启动时，处理权限资源加载
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-04-14 09:23
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface IPermissionEnableDao {
    /**
     * 查询权限关系数据，供服务启动时使用
     * @return
     */
    public List<PermissionEnableEntity> queryPermRelation();

    /**
     * 查询用户角色权限关系，供用户登录时使用
     * @return
     */
    public List<UserRoleEntity> queryUserRelation(@Param("userNo") String userNo);

    /**
     * 查询用户信息
     * @param userName
     * @return
     */
    public FilterUserEntity queryUserInfo(@Param("userName") String userName);
}
