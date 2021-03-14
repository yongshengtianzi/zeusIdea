package zeus.idea.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名：CheckUtil
 * 公司：-----智讯云-----
 * 功能说明：
 * 公共校验
 * <p>
 * 作者：jinyang.wang      创建时间：2021/3/14 17:12
 * <p>
 * 修改人：           修改时间：
 */
public class CheckUtil {

    /**
     * 方法功能说明：校验特殊字符，英文特殊字符
     *
     * @param val
     * @return 如果有特殊字符返回true，否则返回false
     *
     * 作者:jinyang.wang     创建日期:2021/3/14 17:14
     *
     * 修改人:          修改日期:
     */
    public static boolean tszfYW(String val) {
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~@#%&*+|{}]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(val);

        return m.find();
    }

}
