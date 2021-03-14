package zeus.idea.note.bizc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeus.idea.common.bizc.SeqBizc;
import zeus.idea.filter.entity.FilterUserEntity;
import zeus.idea.note.dao.NoteDao;
import zeus.idea.note.entity.NoteEntity;
import zeus.idea.note.entity.NoteKeyEntity;
import zeus.idea.note.entity.NoteKeyReEntity;
import zeus.idea.note.entity.SelectEntity;

import java.util.*;

/**
 * 类名：NoteBizc
 * 公司：-----智讯云-----
 * 功能说明：
 * 笔记逻辑处理bizc
 * <p>
 * 作者：jinyang.wang      创建时间：2020/12/27 8:35
 * <p>
 * 修改人：           修改时间：
 */
@Service
@Transactional(readOnly = true)
public class NoteBizc {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private SeqBizc seqBizc;

    @Autowired
    private StringRedisTemplate redisTemplate0;//保存笔记信息的Redis库

    /**
     * 方法功能说明：查询笔记信息列表
     *
     * @param se
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/4 18:55
     *
     * 修改人:          修改日期:
     */
    public List<NoteEntity> findList(SelectEntity se) {
        List<NoteEntity> retList = noteDao.findList(se);
        List<String> tempList;
        for (NoteEntity entity_for : retList) {
            if (StringUtils.isNotEmpty(entity_for.getNodeInfoKeys())) {
                tempList = new ArrayList<String>(Arrays.asList(entity_for.getNodeInfoKeys().split(",")));
                entity_for.setNodeInfoKey(tempList);
            }
        }
        return retList;
    }

    /**
     * 方法功能说明：查询笔记总数
     *
     * @param se
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/4 18:59
     *
     * 修改人:          修改日期:
     */
    public int findListCount(SelectEntity se) {
        return noteDao.findListCount(se);
    }

    /**
     * 方法功能说明：保存、修改笔记信息
     *
     * @param se
     * @param flag 1新增保存，2修改保存
     * @param fue
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/9 20:25
     *
     * 修改人:          修改日期:
     */
    @Transactional(readOnly = false)
    public void saveUpdNote(NoteEntity se, String flag, FilterUserEntity fue) {
        List<NoteKeyEntity> tempKeyList;
        List<String> keyStrList = new ArrayList<>();
        String tempKeyId;//笔记关键字主键
        NoteKeyEntity tempKeyEntity;
        String tempNoteId;//笔记主键

        Map<String, String> keyMap = new HashMap<>();

        /**********第1步，处理关键字**********/
        List<String> tempListStr = se.getNodeInfoKey();
        for (String for_str : tempListStr) {
            tempKeyList = noteDao.queryNodeKey(for_str);
            if (tempKeyList == null || tempKeyList.isEmpty()) {
                tempKeyId = seqBizc.getKeySEQ();
                tempKeyEntity = new NoteKeyEntity();
                tempKeyEntity.setNodeKeyId(tempKeyId);
                tempKeyEntity.setNodeKeyName(for_str);
                tempKeyEntity.setCreateUserId(fue.getUserId());
                tempKeyEntity.setCreateUserNo(fue.getUserNo());
                tempKeyEntity.setUpdateUserId(fue.getUserId());
                tempKeyEntity.setUpdateUserNo(fue.getUserNo());
                noteDao.saveNoteKey(tempKeyEntity);//新增关键字
                keyStrList.add(tempKeyId);//将关键字放入集合，用于笔记和关键字关系建立
                keyMap.put(tempKeyId, for_str);//将新增的关键字放入该map，下边放入Redis
            } else {
                keyStrList.add(tempKeyList.get(0).getNodeKeyId());//将关键字放入集合，用于笔记和关键字关系建立
            }
        }

        /**********第2步，保存数据**********/
        se.setNodeInfoKeys(StringUtils.join(tempListStr, ","));
        se.setCreateUserId(fue.getUserId());
        se.setCreateUserNo(fue.getUserNo());
        se.setUpdateUserId(fue.getUserId());
        se.setUpdateUserNo(fue.getUserNo());
        if ("1".equals(flag)) {
            //新增笔记
            tempNoteId = seqBizc.getNoteSEQ();//获取笔记主键
            se.setNodeInfoId(tempNoteId);
            se.setNodeInfoNo(tempNoteId);
            noteDao.saveNote(se);//保存笔记信息
        } else {
            //修改笔记
            tempNoteId = se.getNodeInfoId();//获取笔记主键
            //删除笔记与关键字关系
            noteDao.delNoteKeyRe(tempNoteId);
            noteDao.updNote(se);
        }

        //保存笔记与关键字关系
        List<NoteKeyReEntity> tempReList = new ArrayList<>();
        NoteKeyReEntity tempRe;
        for (String for_str : keyStrList) {
            tempRe = new NoteKeyReEntity();
            tempRe.setNodeKeyRelationId(seqBizc.getKeySEQ());
            tempRe.setNodeInfoId(tempNoteId);
            tempRe.setNodeKeyId(for_str);
            tempRe.setCreateUserId(fue.getUserId());
            tempRe.setCreateUserNo(fue.getUserNo());
            tempRe.setUpdateUserId(fue.getUserId());
            tempRe.setUpdateUserNo(fue.getUserNo());
            tempReList.add(tempRe);
        }
        noteDao.saveNoteKeyRe(tempReList);

        //将新增的key关键字放入Redis
        ValueOperations vo = redisTemplate0.opsForValue();
        for (String for_str : keyMap.keySet()) {
            vo.set(keyMap.get(for_str), for_str);
        }
    }

    /**
     * 方法功能说明：删除笔记
     *
     * @param noteId
     * @return
     *
     * 作者:jinyang.wang     创建日期:2021/1/9 21:35
     *
     * 修改人:          修改日期:
     */
    @Transactional(readOnly = false)
    public void delNote(String noteId) {
        noteDao.delNoteKeyRe(noteId);//删除笔记与关键字关系
        noteDao.delNote(noteId);//删除笔记信息
    }

}
