package zeus.idea.obd.bizc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.common.utils.UuidUtil;
import zeus.idea.obd.dao.ObdMakeDao;
import zeus.idea.obd.entity.ObdCarEntity;
import zeus.idea.obd.entity.ObdCunEntity;
import zeus.idea.obd.entity.ObdMakeEntity;
import zeus.idea.obd.entity.ObdMakeZongEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        return obdMakeDao.queryMakeInfo(carNum, null, null, null);
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
     * @param flag 1为多车预约，0为单车预约
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/3 16:03
     *
     * 修改人:          修改日期:
     */
    @Transactional(readOnly = false)
    public synchronized Map<String, String> insMakeInfo(ObdMakeEntity ome, String flag) {
        Map<String, String> retMap = new HashMap<>();
        String code = "1";
        String msg = "";

        if ("0".equals(flag)) {
            //单车预约
            //判断当前车辆是否已经预约
            List<ObdMakeEntity> tempMakeList = obdMakeDao.queryMakeInfo(ome.getCarNum(), null,
                    null, null);
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
            tempMakeList = obdMakeDao.queryMakeInfo(null, ome.getQuX(), ome.getExpTime(), null);
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
            msg = "预约成功，请按照预约日期当天去安装点安装，错过后将无法安装且不可重复预约！";
            retMap.put("code", code);
            retMap.put("msg", msg);
            return retMap;
        } else {
            //多车预约
            //判断当前车辆是否已经预约
            List<ObdMakeEntity> tempMakeList;
            for (ObdCarEntity for_entity : ome.getCars()) {
                tempMakeList = obdMakeDao.queryMakeInfo(for_entity.getCarNum(), null, null, null);
                if (tempMakeList.size() > 0) {
                    code = "2";
                    msg = "序号为" + for_entity.getId() + "的车已经预约安装，请勿重复预约！";
                    retMap.put("code", code);
                    retMap.put("msg", msg);
                    return retMap;
                }
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

            tempMakeList = obdMakeDao.queryMakeInfo(null, ome.getQuX(), ome.getExpTime(), null);
            int tempCunNum = Integer.parseInt(tempCunList.get(0).getCunNum());
            if (tempMakeList.size() >= tempCunNum) {
                //预约已满处理
                code = "2";
                msg = ome.getQuX() + "在" + ome.getExpTime() + "安装预约已满员，请更换日期预约！";
                retMap.put("code", code);
                retMap.put("msg", msg);
                return retMap;
            }
            if (tempCunNum + 20 - tempMakeList.size() < ome.getCars().size()) {
                //预约名额不足处理
                code = "2";
                msg = ome.getQuX() + "在" + ome.getExpTime() + "预约名额不足，还差" + (ome.getCars().size() - (tempCunNum + 20 - tempMakeList.size())) + "个名额，请更换日期预约！";
                retMap.put("code", code);
                retMap.put("msg", msg);
                return retMap;
            }
            //保存预约信息
            List<ObdMakeEntity> tempMakeEntityList = new ArrayList<>();
            ObdMakeEntity tempMakeEntity;
            String uuid = UuidUtil.getUUIDNoLine();
            for (ObdCarEntity for_entity : ome.getCars()) {
                tempMakeEntity = new ObdMakeEntity();
                tempMakeEntity.setCarNum(for_entity.getCarNum());
                tempMakeEntity.setMakePeople(ome.getMakePeople());
                tempMakeEntity.setPhoneNum(ome.getPhoneNum());
                tempMakeEntity.setQuX(ome.getQuX());
                tempMakeEntity.setAnZhDi(ome.getAnZhDi());
                tempMakeEntity.setExpTime(ome.getExpTime());
                tempMakeEntity.setExpMark(ome.getExpMark());
                tempMakeEntity.setAddr(ome.getAddr());
                tempMakeEntity.setTel(ome.getTel());
                tempMakeEntity.setDuoFlag(uuid);
                tempMakeEntityList.add(tempMakeEntity);
                if (tempMakeEntityList.size() > 50) {
                    obdMakeDao.insObdMakeAll(tempMakeEntityList);
                    tempMakeEntityList = new ArrayList<>();
                }
            }
            if (tempMakeEntityList.size() > 0) {
                obdMakeDao.insObdMakeAll(tempMakeEntityList);
            }
            msg = "预约成功，请按照预约日期当天去安装点安装，错过后将无法安装且不可重复预约！";
            retMap.put("code", code);
            retMap.put("msg", msg);
            return retMap;
        }

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

    /**
     * 方法功能说明：查询车辆信息，并显示是否被预约
     *
     * @param carNum
     * @param carJia
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/6 15:45
     *
     * 修改人:          修改日期:
     */
    public List<ObdCarEntity> queryNoMakeCarInfo(String carNum, String carJia) {
        List<ObdCarEntity> tempDataList = obdMakeDao.queryObdCar(carNum, null, carJia);
        if (tempDataList != null && tempDataList.size() == 1) {
            //寻找该数据是否已经被预约
            ObdCarEntity entity = tempDataList.get(0);
            List<ObdMakeEntity> tempMakeEntityList = obdMakeDao.queryMakeInfo(entity.getCarNum(), null, null, null);
            if (tempMakeEntityList != null && tempMakeEntityList.size() > 0) {
                entity.setIsMake("1");//已经被预约
            } else {
                entity.setIsMake("0");//已经被预约
            }

        }
        return tempDataList;
    }

    /**
     * 方法功能说明：汇总预约数量
     *
     * @param nowDate
     * @return 
     *
     * 作者:jinyang.wang     创建日期:2020/11/8 9:13
     *
     * 修改人:          修改日期:
     */
    public List<ObdMakeZongEntity> queryMakeNowDate(String nowDate) {
        return obdMakeDao.queryMakeZong(nowDate);
    }

    /**
     * 方法功能说明：查询字典
     *
     * @param
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/8 21:26
     *
     * 修改人:          修改日期:
     */
    public Map<String, Map<String, Map<String, String>>> queryDict() {
        List<Map<String, String>> tempList = obdMakeDao.queryDict();

        Map<String, Map<String, Map<String, String>>> retMap = new HashMap();
        Map<String, Map<String, String>> tempMap;
        Map<String, String> tempMap2;
        String tempStr = null;
        for (Map<String, String> for_map : tempList) {
            tempStr = for_map.get("quX");
            tempMap = retMap.get(tempStr);
            if (tempMap == null) {
                tempMap = new HashMap<>();
                tempMap2 = new HashMap<>();
                tempMap2.put("addr", for_map.get("addr"));
                tempMap2.put("tel", for_map.get("tel"));

                tempMap.put(for_map.get("anZhDi"), tempMap2);

                retMap.put(tempStr, tempMap);
            } else {
                tempMap2 = new HashMap<>();
                tempMap2.put("addr", for_map.get("addr"));
                tempMap2.put("tel", for_map.get("tel"));

                tempMap.put(for_map.get("anZhDi"), tempMap2);
            }
        }

        return retMap;
    }

    /**
     * 方法功能说明：查询预约信息，供导出使用
     *
     * @param expTime
     * @param quX
     * @param anZhDi
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/11/9 10:44
     *
     * 修改人:          修改日期:
     */
    public List<ObdMakeEntity> handleQuery(String expTime, String quX, String anZhDi) {
        return obdMakeDao.queryMakeInfo(null, quX, expTime, anZhDi);
    }

}
