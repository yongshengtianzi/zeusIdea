package zeus.idea.filter.entrance;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import zeus.idea.user.entity.UserRecordEntity;
import zeus.idea.user.util.UserEHCacheUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名：用户登录过滤类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-07-19 21:33
 * <p>
 * 修改人：           修改时间：
 */
public class LoginFilter implements Filter {
    // 在Java类中创建 logger 实例
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private EhCacheCacheManager cacheCacheManager;

    private String[] excludedUris;

    public LoginFilter(EhCacheCacheManager cacheCacheManager) {
        this.cacheCacheManager = cacheCacheManager;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedUris = filterConfig.getInitParameter("excludedUri").split(",");
        System.out.print("过滤执行---------1");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("用户是否登陆验证：-----开始-----");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getServletPath();
        logger.info("用户的uri：" + uri);
        if (isExcludedUri(uri)){
            filterChain.doFilter(request, response);
            logger.info("用户是否登陆验证结果：-----例外-----");
        } else {
            logger.info("用户是否登陆验证结果：-----验证用户是否登陆-----");
            String token = request.getHeader("token");
            UserEHCacheUtil ueu = new UserEHCacheUtil(cacheCacheManager);
            if (token == null || "".equals(token) || ueu.getCache(token) == null ) {
                Map<String, String> retMap = new HashMap<String, String>();
                retMap.put("code", "-5");
                retMap.put("msg", "请先登录！");
                ObjectMapper objectMapper=new ObjectMapper();
                String retJson = objectMapper.writeValueAsString(retMap);
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    out.append(retJson);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            } else {
                UserRecordEntity ure = (UserRecordEntity) ueu.getCache(token);
                request.setAttribute("user", ure);
                filterChain.doFilter(request, response);
            }
        }
        logger.info("用户是否登陆验证：-----结束-----");
    }

    @Override
    public void destroy() {
        System.out.print("过滤执行---------3");
    }

    /**
     * 范围为url判断
     * @param uri
     * @return
     */
    private boolean isExcludedUri(String uri) {
        if (excludedUris == null || excludedUris.length <= 0) {
            return false;
        }
        for (String ex : excludedUris) {
            uri = uri.trim();
            ex = ex.trim();
            if (uri.toLowerCase().matches(ex.toLowerCase().replace("*",".*"))) {
                return true;
            }
        }
        return false;
    }
}
