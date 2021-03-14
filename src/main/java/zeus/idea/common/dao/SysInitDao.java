package zeus.idea.common.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 接口名：SysInitDao
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * 系统初始化加载的数据，dao
 * <p>
 * 作者：jinyang.wang      创建时间：2021/2/21 20:53
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface SysInitDao {
    
    /**
     * 方法功能说明：查询笔记关键字信息
     *
     * @param
     * @return 
     *
     * 作者:jinyang.wang     创建日期:2021/2/21 20:57
     *
     * 修改人:          修改日期:
     */
    List<Map<String, String>> queryNoteKey();
    
}
