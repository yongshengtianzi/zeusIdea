package zeus.idea.expbase.bizc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.common.bizc.ISysPcodeBizc;
import zeus.idea.expbase.bizc.IExpBaseBizc;
import zeus.idea.expbase.dao.ExpBaseDao;
import zeus.idea.expbase.entity.ExpBaseEntity;

import java.util.List;

/**
 * 类名：花费记录逻辑处理类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-02-23 09:30
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class ExpBaseBizcImpl implements IExpBaseBizc {

    @Autowired
    private ExpBaseDao expBaseDao;

    /**
     * 获取花费记录列表数据
     * @param ee
     * @return
     */
    public List<ExpBaseEntity> findList(ExpBaseEntity ee) {
        return expBaseDao.findList(ee);
    }
    /**
     * 查询总条数
     * @param ee
     * @return
     */
    public int findListCount(ExpBaseEntity ee) {
        return expBaseDao.findListCount(ee);
    }

    /**
     * 获取序列
     * @return
     */
    public String getSEQ() {
        return expBaseDao.getSEQ();
    }

    /**
     * 保存花费记录数据
     * @param ee
     * @return
     */
    @Transactional(readOnly = false)
    public String saveExp(ExpBaseEntity ee) {
        String expId = expBaseDao.getSEQ();
        ee.setExpUserNo(ee.getExpUserNo());
        ee.setOperatorUser(ee.getExpUserNo());
        ee.setExpId(expId);
        ee.setExpNo(expId);
        expBaseDao.saveExp(ee);
        return "1";
    }

    /**
     * 手机端，查询消费记录
     * @param ee
     * @return
     */
    public List<ExpBaseEntity> sjQuery(ExpBaseEntity ee) {
        return expBaseDao.sjQuery(ee);
    }
}
