package zeus.idea.filter.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 类名：ZeusAuthorizationFilter
 * 公司：-----智讯云-----
 * 功能说明：
 * 用户角色校验
 * <p>
 * 作者：jinyang.wang      创建时间：2020/12/19 21:29
 * <p>
 * 修改人：           修改时间：
 */
public class ZeusAuthorizationFilter extends AuthorizationFilter {
    public ZeusAuthorizationFilter() {
    }

    private boolean flag = false;

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[])((String[])mappedValue);
        if (rolesArray != null && rolesArray.length != 0) {
            Set<String> tempSet;
            Set<String> roles = CollectionUtils.asSet(rolesArray);
            for (String str_for : roles) {
                tempSet = new HashSet<>();
                tempSet.add(str_for);
                flag = subject.hasAllRoles(tempSet);
                if (flag) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 拦截后返回响应数据
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Map<String, String> retMap = new HashMap<>();
        retMap.put("code", "qx04");
        retMap.put("msg", "权限不足");
        ObjectMapper mapper = new ObjectMapper();//先创建objmapper的对象
        String string = mapper.writeValueAsString(retMap);

        PrintWriter out = response.getWriter();
        out.write(string);
        out.flush();
        out.close();

        return Boolean.FALSE;
    }
}
