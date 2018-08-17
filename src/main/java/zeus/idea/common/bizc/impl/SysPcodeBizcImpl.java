package zeus.idea.common.bizc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.common.bizc.ISysPcodeBizc;
import zeus.idea.common.dao.ISysPcodeDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：获取数据字典
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-03-03 8:26
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class SysPcodeBizcImpl implements ISysPcodeBizc {

    @Autowired
    private ISysPcodeDao iSysPcodeDao;

    /**
     * 获取数据字典key-name列表
     * @param codeType
     * @return
     */
    @Override
    public List<Map<String, String>> pCodeList(String codeType) {
        return iSysPcodeDao.pCodeList(codeType);
    }

    /**
     * 获取数据字典key-name的数据
     * @param codeType
     * @return
     */
    public Map<String, String> pCodeMap(String codeType) {
        Map<String, String> retMap = new HashMap<String, String>();
        List<Map<String, String>> list = iSysPcodeDao.pCodeList(codeType);
        for (Map<String, String> tmp_map : list) {
            retMap.put(tmp_map.get("key"), tmp_map.get("value"));
        }
        return retMap;
    }

    /**
     * 获取key对应的name
     * @param codeType
     * @param key
     * @return
     */
    @Override
    public String showName(String codeType, String key) {
        return iSysPcodeDao.showName(codeType, key);
    }
}
