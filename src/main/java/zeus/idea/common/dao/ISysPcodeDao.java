package zeus.idea.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 接口名：查询数据字典dao层
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-03-03 8:31
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface ISysPcodeDao {
    /**
     * 获取某个数据字典的所有内容
     * @param codeType
     * @return
     */
    public List<Map<String, String>> pCodeList(@Param("codeType") String codeType);

    /**
     * 获取数据字典值对应的中文
     * @param codeType
     * @param key
     * @return
     */
    public String showName(@Param("codeType") String codeType, @Param("key")  String key);
}
