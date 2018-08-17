package zeus.idea.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zeus.idea.filter.entrance.LoginFilter;

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

    @Autowired
    private EhCacheCacheManager cacheCacheManager;

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean userFilterRegistration() {
        //配置无需过滤的路径或者静态资源，如：css，imgage等
        StringBuffer excludedUriStr = new StringBuffer();
        excludedUriStr.append("/user/usersjmanage");
        excludedUriStr.append(",");
        excludedUriStr.append("/favicon.ico");
        excludedUriStr.append(",");
        excludedUriStr.append("/js/*");

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter(cacheCacheManager));
        registration.addUrlPatterns("/*");
        registration.addInitParameter("excludedUri", excludedUriStr.toString());
        registration.setName("userFilter");
        registration.setOrder(1);
        return registration;
    }
}
