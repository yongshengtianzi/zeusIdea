package zeus.idea.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import zeus.idea.filter.bizc.PermissionEnableBizc;
import zeus.idea.filter.entity.PermissionEnableEntity;
import zeus.idea.filter.entrance.LoginFilter;
import zeus.idea.filter.shiro.ZeusShiroRealm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：web服务启动配置
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-07-20 9:12
 * <p>
 * 修改人：           修改时间：
 */
@Configuration
public class ZeusWebConfig {
    // 在Java类中创建 logger 实例
    private Logger logger = LoggerFactory.getLogger(ZeusWebConfig.class);

    @Autowired
    private EhCacheCacheManager cacheCacheManager;

    @Autowired
    private PermissionEnableBizc permissionEnableBizc;

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    /**
     * 方法功能说明：springboot自带的过滤器
     *
     * @param
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/9/15 21:48
     *
     * 修改人:          修改日期:
     */
//    @Bean
    public FilterRegistrationBean userFilterRegistration() {
        //配置无需过滤的路径或者静态资源，如：css，imgage等
        StringBuffer excludedUriStr = new StringBuffer();
        excludedUriStr.append("/user/usersjmanage");
        excludedUriStr.append(",");
        excludedUriStr.append("/favicon.ico");
        excludedUriStr.append(",");
        excludedUriStr.append("/js/*");

        excludedUriStr.append(",");
        excludedUriStr.append("/my/*");
        excludedUriStr.append(",");
        excludedUriStr.append("/actuator/*");

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter(cacheCacheManager));
        registration.addUrlPatterns("/*");
        registration.addInitParameter("excludedUri", excludedUriStr.toString());
        registration.setName("userFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 初始化shiro过滤器实例化对象
     * @param sm
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager sm) {
        logger.debug("shiro初始化配置，开始！！！");
        ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
        sffb.setSecurityManager(sm);//配置SecurityManager对象，必须要有

        /**
         * 配置登录、登陆成功、未授权的跳转界面
         */
        sffb.setLoginUrl("/zeus/filter/login");//设置登录提醒url
        sffb.setSuccessUrl("/zeus/filter/success");//设置登录成功跳转url
        sffb.setUnauthorizedUrl("/zeus/filter/notperm");//设置未授权跳转url

        /**
         * 配置拦截器
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置例外
        filterChainDefinitionMap.put("/zeus/user/signIn", "anon");
        filterChainDefinitionMap.put("/zeus/user/login", "anon");
        filterChainDefinitionMap.put("/zeus/user/loginOut", "anon");
        filterChainDefinitionMap.put("/zeus/filter/userLogin", "anon");//配置用户登录为例外
        filterChainDefinitionMap.put("/zeus/filter/login", "anon");//配置用户登录提醒为例外
        filterChainDefinitionMap.put("/user/usersjmanage", "anon");//手机端，配置用户登录提醒为例外
        filterChainDefinitionMap.put("/obd/make/**", "anon");//配置用户登录提醒为例外
        filterChainDefinitionMap.put("/zeus/filter/notperm", "authc");//配置用户权限不足提醒
        filterChainDefinitionMap.put("/zeus/filter/success", "authc");//配置用户登录成提醒
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/my/**", "anon");
        filterChainDefinitionMap.put("/actuator/**", "anon");
        filterChainDefinitionMap.put("/index.html", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        //配置监听
        filterChainDefinitionMap.put("/zeus/dbhandle/init", "authc,roles[adminRole]");

        //从数据库获取url及其归属权限
        List<PermissionEnableEntity> permList = permissionEnableBizc.queryPermRelation();
        for (PermissionEnableEntity for_perm : permList) {
            String urlHref = for_perm.getUrlHref();
            if (StringUtils.isEmpty(urlHref)) {
                continue;
            }
            String relationType = for_perm.getRelationType();//关系类型，role角色、perms权限等
            String enName = for_perm.getEnName();//角色名称或权限值
            filterChainDefinitionMap.put(urlHref, "roles[" + enName + "]");
        }

        filterChainDefinitionMap.put("/**", "authc,roles[adminRole]");

        sffb.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return sffb;
    }

    /**
     * 初始化shiro的SecurityManager对象实例
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义的realm
        securityManager.setRealm(zeusShiroRealm());
        //设置自定义的缓存，使用ehcache
        securityManager.setCacheManager(ehCacheManager());
        //设置会话管理
        securityManager.setSessionManager(sessionManager());

        return securityManager;
    }

    /**
     * 配置ehcache缓存供shiro使用
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager manager = new EhCacheManager();
//        manager.setCacheManagerConfigFile(String.valueOf(new ClassPathResource("ehcache-shiro.xml")));
        manager.setCacheManager(ehcache().getObject());
        return manager;
    }
    /**
     * EhCache工厂配置，可以不使用
     */
    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache-shiro.xml"));
        return ehCacheManagerFactoryBean;
    }

    /**
     * 会话管理
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }

    /**
     * 自定义cookie名称
     * @return
     */
    public SimpleCookie sessionIdCookie(){
        SimpleCookie cookie = new SimpleCookie("X-Token");
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        return cookie;
    }

    /**
     * 初始化自定义的shiro的Realm
     * @return
     */
    @Bean
    public ZeusShiroRealm zeusShiroRealm() {
        ZeusShiroRealm zeusShiroRealm = new ZeusShiroRealm();
        zeusShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());//设置凭证匹配器
        zeusShiroRealm.setCacheManager(ehCacheManager());
        return zeusShiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }

    /**
     * 开启shiro的aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

}
