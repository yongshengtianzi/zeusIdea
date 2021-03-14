package zeus.idea.common.bizc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.common.dao.SysInitDao;

import java.util.List;
import java.util.Map;

/**
 * 类名：SysInitBizc
 * 公司：-----智讯云-----
 * 功能说明：
 * 系统初始化加载的数据，bizc
 * <p>
 * 作者：jinyang.wang      创建时间：2021/2/21 20:59
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class SysInitBizc {

    @Autowired
    private SysInitDao sysInitDao;

    /**
     * 方法功能说明：查询笔记关键字信息
     *
     * @param
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/2/21 21:02
     *
     * 修改人:          修改日期:
     */
    public List<Map<String, String>> queryNoteKey() {
        return sysInitDao.queryNoteKey();
    }

}
