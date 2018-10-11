package com.zhengtoon.framework.web.common;

import com.zhengtoon.framework.exception.WebResCriteriaException;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一处理返回值
 * @author LuCheng.Qi
 * @since 2018-07-10
 * Company:北京思源政务通有限公司
 */
public class WebResCriteria {
    
    private Object single;
    
    private Map<String, Object> object;
    
    /**
     * 添加单值结果
     * @param value 返回值
     */
    public void addSingleResult(Object value) {
        if (null != object) {
            throw new WebResCriteriaException("WebResCriteria map result object already has value.");
        }
        this.single = value;
    }
    
    /**
     * 添加多值结果
     * @param key key
     * @param value value
     * @return WebResCriteria
     */
    public WebResCriteria addMultiResult(String key, Object value) {
        Assert.notNull(key, "MultiResult key can't be null.");
        if (null != single) {
            throw new WebResCriteriaException("WebResCriteria single value result object already has value.");
        }
        if (null == object) {
            object = new HashMap<>(8);
        }
        object.put(key, value);
        return this;
    }
    
    /**
     * 获取结果
     * @return 返回对象
     */
    public Object getResult() {
        if (null != single) {
            return single;
        }
        if (null != object) {
            return object;
        }
        return null;
    }
}