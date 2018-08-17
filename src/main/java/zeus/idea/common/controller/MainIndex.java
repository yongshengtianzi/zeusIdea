package zeus.idea.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类名：主界面入口类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-03-05 22:45
 * <p>
 * 修改人：           修改时间：
 */
@Controller
@RequestMapping("/common")
public class MainIndex {
    @RequestMapping(value = "index")
    public String index(ModelMap map) {
        return "index";
    }
}
