package zeus.idea.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zeus.idea.user.entity.UserEntity;

import java.util.List;

/**
 * 接口名：用户管理数据层类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-07-15 17:22
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface IUserDao {
    /**
     * 查询用户信息
     * @param ue
     * @return
     */
    public UserEntity queryUser(@Param("ue") UserEntity ue);

    /**
     * 保存用户信息
     * @param ue
     */
    public void saveUser(@Param("ue") UserEntity ue);

    /**
     * 获取序列
     * @return
     */
    public String getSEQ();

    /**
     * 保存用户登录记录
     * @param ue
     */
    public void saveLoginRecord(@Param("ue") UserEntity ue);

    /**
     * 判断用户记录是否存在
     * @param userNo
     * @param token
     * @return
     */
    public int isHaveLoginRe(@Param("userNo") String userNo, @Param("token") String token);

    /**
     * 更新用户登录记录
     * @param loginOutType
     * @param loginOutUser
     * @param userNo
     * @param token
     */
    public void updateLoginRecord(@Param("loginOutType") String loginOutType, @Param("loginOutUser") String loginOutUser, @Param("userNo") String userNo, @Param("token") String token);

    /**
     * 获取未退出用户的token值list集合
     * @param userNo
     * @return
     */
    public List<String> queryUerLoginRe(@Param("userNo") String userNo);
}
