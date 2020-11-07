package zeus.idea.obd.entity;

/**
 * 类名：ObdCarEntity
 * 公司：-----智讯云-----
 * 功能说明：
 * 车辆信息
 * <p>
 * 作者：jinyang.wang      创建时间：2020/11/3 10:41
 * <p>
 * 修改人：           修改时间：
 */
public class ObdCarEntity {
    private String id;
    private String carNum;//车牌号，表主键
    private String carJia;//车架号
    private String carPeople;//车主姓名
    private String carPhoneNum;//电话号码
    private String carNumType;//号牌种类
    private String carType;//车辆类型
    private String manufactureDate;//出厂日期
    private String register;//注册日期
    private String peopleType;//所属类型
    private String carOutLevel;//排放等级
    private String carQux;//所属区县
    private String carEngineType;//发动机型号
    private String carDrive;//发动机号
    private String carBrand;//车辆品牌
    private String carVehicleType;//车辆型号
    private String carManufacturer;//生产厂商
    private String carPail;//排量
    private String carKg;//总质量，KG
    private String carNiaos;//尿素箱容积
    private String carFent;//车辆分类
    private String carGongl;//额定功率
    private String carYongt;//车辆用途
    private String carMaxNiu;//最大基准扭矩
    private String carNiu;//参考扭矩
    private String carRanl;//燃料类型
    private String carFee;//自/免费
    private String carNumColor;//车牌颜色
    private String carPopleNum;//总客数
    private String carMaxKg;//列车最大总质量
    private String carHuj;//车籍地
    private String carDanw;//所属单位名称
    private String carJier;//接入地
    private String createTime;//创建时间
    private String createUser;//创建人，用户id
    private String updateTime;//修改时间
    private String updateUser;//修改人，用户id

    private String isMake;//1已经被预约，0未被预约

    public String getIsMake() {
        return isMake;
    }

    public void setIsMake(String isMake) {
        this.isMake = isMake;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarJia() {
        return carJia;
    }

    public void setCarJia(String carJia) {
        this.carJia = carJia;
    }

    public String getCarPeople() {
        return carPeople;
    }

    public void setCarPeople(String carPeople) {
        this.carPeople = carPeople;
    }

    public String getCarPhoneNum() {
        return carPhoneNum;
    }

    public void setCarPhoneNum(String carPhoneNum) {
        this.carPhoneNum = carPhoneNum;
    }

    public String getCarNumType() {
        return carNumType;
    }

    public void setCarNumType(String carNumType) {
        this.carNumType = carNumType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public String getCarOutLevel() {
        return carOutLevel;
    }

    public void setCarOutLevel(String carOutLevel) {
        this.carOutLevel = carOutLevel;
    }

    public String getCarQux() {
        return carQux;
    }

    public void setCarQux(String carQux) {
        this.carQux = carQux;
    }

    public String getCarEngineType() {
        return carEngineType;
    }

    public void setCarEngineType(String carEngineType) {
        this.carEngineType = carEngineType;
    }

    public String getCarDrive() {
        return carDrive;
    }

    public void setCarDrive(String carDrive) {
        this.carDrive = carDrive;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarVehicleType() {
        return carVehicleType;
    }

    public void setCarVehicleType(String carVehicleType) {
        this.carVehicleType = carVehicleType;
    }

    public String getCarManufacturer() {
        return carManufacturer;
    }

    public void setCarManufacturer(String carManufacturer) {
        this.carManufacturer = carManufacturer;
    }

    public String getCarPail() {
        return carPail;
    }

    public void setCarPail(String carPail) {
        this.carPail = carPail;
    }

    public String getCarKg() {
        return carKg;
    }

    public void setCarKg(String carKg) {
        this.carKg = carKg;
    }

    public String getCarNiaos() {
        return carNiaos;
    }

    public void setCarNiaos(String carNiaos) {
        this.carNiaos = carNiaos;
    }

    public String getCarFent() {
        return carFent;
    }

    public void setCarFent(String carFent) {
        this.carFent = carFent;
    }

    public String getCarGongl() {
        return carGongl;
    }

    public void setCarGongl(String carGongl) {
        this.carGongl = carGongl;
    }

    public String getCarYongt() {
        return carYongt;
    }

    public void setCarYongt(String carYongt) {
        this.carYongt = carYongt;
    }

    public String getCarMaxNiu() {
        return carMaxNiu;
    }

    public void setCarMaxNiu(String carMaxNiu) {
        this.carMaxNiu = carMaxNiu;
    }

    public String getCarNiu() {
        return carNiu;
    }

    public void setCarNiu(String carNiu) {
        this.carNiu = carNiu;
    }

    public String getCarRanl() {
        return carRanl;
    }

    public void setCarRanl(String carRanl) {
        this.carRanl = carRanl;
    }

    public String getCarFee() {
        return carFee;
    }

    public void setCarFee(String carFee) {
        this.carFee = carFee;
    }

    public String getCarNumColor() {
        return carNumColor;
    }

    public void setCarNumColor(String carNumColor) {
        this.carNumColor = carNumColor;
    }

    public String getCarPopleNum() {
        return carPopleNum;
    }

    public void setCarPopleNum(String carPopleNum) {
        this.carPopleNum = carPopleNum;
    }

    public String getCarMaxKg() {
        return carMaxKg;
    }

    public void setCarMaxKg(String carMaxKg) {
        this.carMaxKg = carMaxKg;
    }

    public String getCarHuj() {
        return carHuj;
    }

    public void setCarHuj(String carHuj) {
        this.carHuj = carHuj;
    }

    public String getCarDanw() {
        return carDanw;
    }

    public void setCarDanw(String carDanw) {
        this.carDanw = carDanw;
    }

    public String getCarJier() {
        return carJier;
    }

    public void setCarJier(String carJier) {
        this.carJier = carJier;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
