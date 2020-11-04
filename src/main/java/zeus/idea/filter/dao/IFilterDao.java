package zeus.idea.filter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zeus.idea.filter.entity.FilterUserEntity;
import zeus.idea.sysuser.entity.UserEntity;

/**
 * 类名：过滤异常处理
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-04-20 21:26
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface IFilterDao {
    /**
     * 用户登录查询用户信息
     * @param userName
     * @return
     */
    public FilterUserEntity queryUserInfo(@Param("userName") String userName);

    /**
     * 保存用户登录记录
     * @param ue
     */
    public void saveLoginRecord(@Param("ue") UserEntity ue);
}