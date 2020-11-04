package zeus.idea.obd.bizc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.obd.dao.ObdMakeDao;
import zeus.idea.obd.entity.ObdCarEntity;
import zeus.idea.obd.entity.ObdCunEntity;
import zeus.idea.obd.entity.ObdMakeEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：ObdMakeBizc
 * 公司：-----智讯云-----
 * 功能说明：
 * obd终端安装预约bizc
 * <p>
 * 作者：jinyang.wang      创建时间：2020/11/3 10:37
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class ObdMakeBizc {

    @Autowired
    private ObdMakeDao obdMakeDao;

    /**
     * 方法功能说明：查询车辆信息
     *
     * @param carNum
     * @param carPeople
     * @param carJia
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 11:32
     *
     * 修改人:          修改日期:
     */
    public List<ObdCarEntity> queryObdCar(String carNum, String carPeople, String carJia) {
        return obdMakeDao.queryObdCar(carNum, carPeople, carJia);
    }

    /**
     * 方法功能说明：根据车牌号查询预约信息
     *
     * @param carNum
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 20:13
     *
     * 修改人:          修改日期:
     */
    public List<ObdMakeEntity> queryObdMake(String carNum) {
        return obdMakeDao.queryMakeInfo(carNum, null, null);
    }

    /**
     * 方法功能说明：更新车辆信息
     *
     * @param oce
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 15:42
     *
     * 修改人:          修改日期:
     */
    @Transactional(readOnly = false)
    public void updCarInfo(ObdCarEntity oce) {
        obdMakeDao.updCarInfo(oce);
    }

    /**
     * 方法功能说明：保存预约信息
     *
     * @param ome
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 16:03
     *
     * 修改人:          修改日期:
     */
    @Transactional(readOnly = false)
    public synchronized Map<String, String> insMakeInfo(ObdMakeEntity ome) {
        Map<String, String> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        //判断当前车辆是否已经预约
        List<ObdMakeEntity> tempMakeList = obdMakeDao.queryMakeInfo(ome.getCarNum(), null, null);
        if (tempMakeList.size() > 0) {
            code = "2";
            msg = "当前车辆已经预约安装，请勿重复预约！";
            retMap.put("code", code);
            retMap.put("msg", msg);
            return retMap;
        }
        //判断当前区县、日期下是否还有预约名额
        List<ObdCunEntity> tempCunList = obdMakeDao.queryCunInfo(ome.getQuX(), ome.getExpTime(),
                null);
        if (tempCunList == null || tempCunList.size() == 0) {
            code = "2";
            msg = ome.getQuX() + "在" + ome.getExpTime() + "没有开放安装预约！";
            retMap.put("code", code);
            retMap.put("msg", msg);
            return retMap;
        }
        tempMakeList = obdMakeDao.queryMakeInfo(null, ome.getQuX(), ome.getExpTime());
        int tempCunNum = Integer.parseInt(tempCunList.get(0).getCunNum());
        if (tempMakeList.size() >= tempCunNum) {
            //预约已满处理
            code = "2";
            msg = ome.getQuX() + "在" + ome.getExpTime() + "安装预约已满员，请更换日期预约！";
            retMap.put("code", code);
            retMap.put("msg", msg);
            return retMap;
        }
        //保存预约信息
        obdMakeDao.saveObdMake(ome);
        msg = "预约成功！";
        retMap.put("code", code);
        retMap.put("msg", msg);
        return retMap;
    }

    /**
     * 方法功能说明：查询可预约的日期
     *
     * @param quX
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 17:44
     *
     * 修改人:          修改日期:
     */
    public List<ObdCunEntity> queryCunDateList(String quX) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        String date = localDate.format(formatter);
        List<ObdCunEntity> tempCunList = obdMakeDao.queryCunInfo(quX, null,
                date);
        return tempCunList;
    }

}
