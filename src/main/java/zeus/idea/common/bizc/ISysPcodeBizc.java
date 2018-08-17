package zeus.idea.common.bizc;

import java.util.List;
import java.util.Map;

/**
 * 类名：获取数据字典
 * 公司：-----智讯云-----
 * 功能说明：查询数据字典
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-03-03 8:21
 * <p>
 * 修改人：           修改时间：
 */
public interface ISysPcodeBizc {
    /**
     * 获取数据字典key-name列表
     * @param codeType
     * @return
     */
    public List<Map<String, String>> pCodeList(String codeType);

    /**
     * 获取数据字典key-name的数据
     * @param codeType
     * @return
     */
    public Map<String, String> pCodeMap(String codeType);

    /**
     * 获取key对应的name
     * @param codeType
     * @param key
     * @return
     */
    public String showName(String codeType, String key);
}