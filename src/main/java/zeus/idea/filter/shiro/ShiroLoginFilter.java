package zeus.idea.filter.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ShiroLoginFilter extends FormAuthenticationFilter {

    /**
     * 当shiro校验用户未登录时，返回JSON数据代替原有的跳转到登录界面
     * @param servletRequest
     * @param servletResponse
     * @return true继续往下执行 false结束执行返回信息
     * @throws IOException
     */
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if (isLoginRequest(servletRequest, servletResponse)) {
            if (isLoginSubmission(servletRequest, servletResponse)) {
                return executeLogin(servletRequest, servletResponse);
            } else {
                return true;
            }
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setStatus(200);

            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            Map<String, String> retMap = new HashMap<>();
            retMap.put("code", "qx03");
            retMap.put("msg", "请登录");
            ObjectMapper mapper = new ObjectMapper();//先创建objmapper的对象
            String string = mapper.writeValueAsString(retMap);

            PrintWriter out = httpServletResponse.getWriter();
            out.write(string);
            out.flush();
            out.close();
            return false;
        }
    }

}
