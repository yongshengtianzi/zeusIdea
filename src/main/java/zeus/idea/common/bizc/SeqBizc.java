package zeus.idea.common.bizc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.common.dao.ISeqDao;

/**
 * 类名：SeqBizc
 * 公司：-----智讯云-----
 * 功能说明：
 * 序列处理bizc
 * <p>
 * 作者：jinyang.wang      创建时间：2020/9/13 15:49
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class SeqBizc {
    // 在Java类中创建 logger 实例
    private static final Logger logger = LoggerFactory.getLogger(SeqBizc.class);

    @Autowired
    private ISeqDao iSeqDao;

    /**
     * 方法功能说明：获取用户序列
     *
     * @param
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/9/13 15:57
     *
     * 修改人:          修改日期:
     */
    public String getUserSEQ() {
        return iSeqDao.getUserSEQ();
    }
}
