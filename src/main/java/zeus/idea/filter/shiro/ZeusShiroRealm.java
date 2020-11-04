package zeus.idea.filter.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import zeus.idea.filter.bizc.PermissionEnableBizc;
import zeus.idea.filter.entity.FilterUserEntity;
import zeus.idea.filter.entity.UserRoleEntity;

import java.util.List;

/**
 * 类名：shiroRealm处理类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2019-04-16 22:11
 * <p>
 * 修改人：           修改时间：
 */
public class ZeusShiroRealm extends AuthorizingRealm {
    @Autowired
    private PermissionEnableBizc permissionEnableBizc;

    /**
     * 配置用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        FilterUserEntity ue = (FilterUserEntity) principalCollection.getPrimaryPrincipal();//获取用户名称，会把认证时传入的用户信息entity带过来
        List<UserRoleEntity> userRoleList = permissionEnableBizc.queryUserRelation(ue.getUserNo());
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (UserRoleEntity for_entity : userRoleList) {
            if ("role".equals(for_entity.getRelationType())) {
                info.addRole(for_entity.getEnName());
            } else if ("perms".equals(for_entity.getRelationType())) {
                info.addStringPermission(for_entity.getEnName());
            }
        }

        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        FilterUserEntity ue = permissionEnableBizc.queryUserInfo(userName);
        //获取ip地址，并设置ip
        String host = ((UsernamePasswordToken) authenticationToken).getHost();
        if (ue != null && StringUtils.isNotBlank(host)) {
            ue.setIp(host);
        }

        if (ue == null || StringUtils.isBlank(ue.getUserNo())) {
            throw new UnknownAccountException();//未知的用户
        }
        if ("0".equals(ue.getUserStatue())) {
            throw new LockedAccountException();//账号被锁定
        }

        SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(
                ue, ue.getPassword(), ByteSource.Util.bytes(ue.getUserNo()), getName());
        //验证通过后，把用户信息放到session管理中
        Session sn = SecurityUtils.getSubject().getSession();
        sn.setAttribute(sn.getId(), authenticationToken);
        return ai;
    }
}
