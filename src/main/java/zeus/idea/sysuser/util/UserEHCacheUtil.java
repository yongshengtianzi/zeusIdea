package zeus.idea.sysuser.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.cache.ehcache.EhCacheCacheManager;

/**
 * 类名：用户管理缓存公共处理类
 * 公司：-----智讯云-----
 * 功能说明：
 * <p>
 * <p>
 * <p>
 * 作者：jinyang.wang      创建时间：2018-07-18 19:11
 * <p>
 * 修改人：           修改时间：
 */
public class UserEHCacheUtil {

    private CacheManager cacheManager;
    private EhCacheCacheManager cacheCacheManager;

    public UserEHCacheUtil(EhCacheCacheManager cacheCacheManager) {
        this.cacheCacheManager = cacheCacheManager;
        this.cacheManager = cacheCacheManager.getCacheManager();
    }

    /**
     * 设置缓存对象
     * @param key
     * @param object
     */
    public void setCache(String key, Object object){
        Cache cache = cacheManager.getCache("userCache");
        Element element = new Element(key,object);
        cache.put(element);
    }
    /**
     * 从缓存中取出对象
     * @param key
     * @return
     */
    public Object getCache(String key){
        Object object = null;
        Cache cache = cacheManager.getCache("userCache");
        if(cache.get(key) != null && !"".equals(cache.get(key))){
            object = cache.get(key).getObjectValue();
        }
        return object;
    }

    /**
     * 删除缓存中的数据
     * @param key
     */
    public void delCache(String key) {
        Cache cache = cacheManager.getCache("userCache");
        if(cache.get(key)!=null && !"".equals(cache.get(key))){
            cache.remove(key);
        }
    }
}
