package zeus.idea.note.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zeus.idea.note.entity.NoteEntity;
import zeus.idea.note.entity.NoteKeyEntity;
import zeus.idea.note.entity.NoteKeyReEntity;
import zeus.idea.note.entity.SelectEntity;

import java.util.List;

/**
 * 类名：NoteDao
 * 公司：-----智讯云-----
 * 功能说明：
 * 笔记数据管理处理Dao
 * <p>
 * 作者：jinyang.wang      创建时间：2020/12/27 8:52
 * <p>
 * 修改人：           修改时间：
 */
@Mapper
public interface NoteDao {

    /**
     * 方法功能说明：查询笔记信息
     *
     * @param se
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/27 18:45
     *
     * 修改人:          修改日期:
     */
    List<NoteEntity> findList(@Param("se") SelectEntity se);

    /**
     * 方法功能说明：查询笔记总数
     *
     * @param se
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/27 18:48
     *
     * 修改人:          修改日期:
     */
    int findListCount(@Param("se") SelectEntity se);

    /**
     * 方法功能说明：根据关键字查询关键字信息
     *
     * @param nodeKeyName
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/28 20:10
     *
     * 修改人:          修改日期:
     */
    List<NoteKeyEntity> queryNodeKey(@Param("nodeKeyName") String nodeKeyName);

    /**
     * 方法功能说明：保存笔记信息
     *
     * @param se
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/27 19:02
     *
     * 修改人:          修改日期:
     */
    void saveNote(@Param("se") NoteEntity se);

    /**
     * 方法功能说明：保存关键字数据
     *
     * @param ke
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/30 18:02
     *
     * 修改人:          修改日期:
     */
    void saveNoteKey(@Param("ke") NoteKeyEntity ke);

    /**
     * 方法功能说明：保存笔记与关键字关系
     *
     * @param datas
     * @return
     *
     * 作者:jinyang.wang     创建日期:2020/12/30 19:11
     *
     * 修改人:          修改日期:
     */
    void saveNoteKeyRe(@Param("datas") List<NoteKeyReEntity> datas);
    
    /**
     * 方法功能说明：删除笔记与关键字关系
     *
     * @param nodeInfoId
     * @return 
     *
     * 作者:jinyang.wang     创建日期:2021/1/4 18:39
     *
     * 修改人:          修改日期:
     */
    void delNoteKeyRe(@Param("nodeInfoId") String nodeInfoId);

    /**
     * 方法功能说明：更新笔记信息
     *
     * @param se
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/9 21:15
     *
     * 修改人:          修改日期:
     */
    void updNote(@Param("se") NoteEntity se);

    /**
     * 方法功能说明：删除笔记信息
     *
     * @param nodeInfoId
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/9 21:32
     *
     * 修改人:          修改日期:
     */
    void delNote(@Param("nodeInfoId") String nodeInfoId);

}
