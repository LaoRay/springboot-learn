package com.zhengtoon.framework.utils;

import com.zhengtoon.framework.exception.BusinessException;
import com.zhengtoon.framework.exception.CoreExceptionCodes;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 功能描述: BeanCopier简单对象赋值
 * 
 * @date 2016年7月25日 下午2:03:46
 * @version 2.2.0
 * @author llh
 */
public class CachedBeanCopier {

	static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap<>();

	private CachedBeanCopier(){}


	/**
	 * 功能描述:当源和目标类的属性类型及字段相同时(两个对象中有一个为空时不处理)，才能拷贝
	 * 
	 * @param srcObj
	 * @param destObj
	 *            void
	 * @date 2016年7月25日 下午2:04:15
	 * @version 2.2.0
	 * @author llh
	 */
	public static void copy(Object srcObj, Object destObj) {
		if (null == srcObj || null == destObj) {
			return;
		} else {
			String key = genKey(srcObj.getClass(), destObj.getClass());
			BeanCopier copier = null;
			if (BEAN_COPIERS.containsKey(key)) {
				copier = BEAN_COPIERS.get(key);
			} else {
				copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
				BEAN_COPIERS.put(key, copier);

			}
			copier.copy(srcObj, destObj, null);
		}
	}

	/**
	 * 功能描述: 当源和目标类的属性类型不同字段相同时(两个对象中有一个为空时destObj为null)，通过实现Converter接口转换拷贝，
	 * 性能相对简单对象拷贝低。
	 * 
	 * @param srcObj
	 * @param destObj
	 *            void
	 * @date 2016年7月25日 下午2:04:15
	 * @version 2.2.0
	 * @author llh
	 */
	@SuppressWarnings("all")
	public static void copyAndCoverter(Object srcObj, Object destObj) {
		if (null == srcObj || null == destObj) {
			destObj = null;
			return;
		} else {
			String key = genKey(srcObj.getClass(), destObj.getClass());
			BeanCopier copier = null;
			if (BEAN_COPIERS.containsKey(key)) {
				copier = BEAN_COPIERS.get(key);
			} else {
				copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), true);
				BEAN_COPIERS.put(key, copier);

			}
			copier.copy(srcObj, destObj, new Converter() {
				public Object convert(Object sourceValue, Class targetClass, Object methodName) {
					if (sourceValue instanceof Integer) {
						return (Integer) sourceValue;
					} else if (sourceValue instanceof Timestamp) {
						Timestamp date = (Timestamp) sourceValue;
						return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
					} else if (sourceValue instanceof BigDecimal) {
						BigDecimal bd = (BigDecimal) sourceValue;
						return bd.toPlainString();
					}
					return sourceValue;
				}
			});
		}
	}

	/**
	 * 功能描述: 当源和目标类的属性类型及字段相同时，才能拷贝(集合)；当目标对象的子集为空时，依然拷贝操作
	 * 
	 * @param srcCollection
	 * @param destCollection
	 * @param destClazz
	 *            有无参构造方法的对象，否则会报错 void
	 * @date 2016年7月25日 下午2:55:51
	 * @version 2.2.0
	 * @author llh
	 * @throws BusinessException
	 *             CACHE_BEAN_FAIL
	 */
	public static <E, T> void copyList(Collection<E> srcCollection, Collection<T> destCollection, Class<T> destClazz) {
		try {
			T deskObj = null;
			for (E obj : srcCollection) {
				deskObj = destClazz.newInstance();
				CachedBeanCopier.copy(obj, deskObj);
				destCollection.add(deskObj);
			}
		} catch (Exception e) {
			throw new BusinessException(CoreExceptionCodes.CACHE_BEAN_FAIL);
		}
	}
	
	/**
	 * 功能描述: 当源和目标类的属性类型及字段相同时，才能拷贝(集合)；当目标对象的子集为空时，不拷贝
	 * 
	 * @param srcCollection
	 * @param destCollection
	 * @param destClazz
	 *            有无参构造方法的对象，否则会报错 void
	 * @date 2016年7月25日 下午2:55:51
	 * @version 2.2.0
	 * @author llh
	 * @throws BusinessException
	 *             CACHE_BEAN_FAIL
	 */
	public static <E, T> void copyAndFilterList(Collection<E> srcCollection, Collection<T> destCollection, Class<T> destClazz) {
		try {
			T deskObj = null;
			for (E obj : srcCollection) {
				if(null == obj){
					continue;
				}
				deskObj = destClazz.newInstance();
				CachedBeanCopier.copy(obj, deskObj);
				destCollection.add(deskObj);
			}
		} catch (Exception e) {
			throw new BusinessException(CoreExceptionCodes.CACHE_BEAN_FAIL);
		}
	}

	/**
	 * 功能描述: 当源和目标类的属性类型及字段相同时，才能拷贝(集合)；当目标对象的子集为空时，依然拷贝操作
	 * 
	 * @param srcList
	 * @param destClazz
	 *            必须有无参构造方法的对象，否则会报错 
	 * @return 拷贝后的数组           
	 * @date 2016年7月25日 下午2:55:51
	 * @version 2.2.0
	 * @author llh
	 * @throws BusinessException
	 *             CACHE_BEAN_FAIL
	 */
	public static <E, T> List<T> copyList(List<E> srcList, Class<T> destClazz) {
		List<T> destList = new ArrayList<>();
		try {
			T deskObj = null;
			for (E obj : srcList) {
				deskObj = destClazz.newInstance();
				CachedBeanCopier.copy(obj, deskObj);
				destList.add(deskObj);
			}
		} catch (Exception e) {
			throw new BusinessException(CoreExceptionCodes.CACHE_BEAN_FAIL);
		}
		return destList;
	}
	/**
	 * 功能描述: 当源和目标类的属性类型及字段相同时，才能拷贝(集合)；当目标对象的子集为空时，不拷贝
	 * 
	 * @param srcList
	 * @param destClazz
	 *            必须有无参构造方法的对象，否则会报错 void
	 * @date 2016年7月25日 下午2:55:51
	 * @version 2.2.0
	 * @author llh
	 * @param <E>
	 * @throws BusinessException
	 *             CACHE_BEAN_FAIL
	 */
	public static <E, T> List<T> copyAndFilterList(List<E> srcList, Class<T> destClazz) {
		List<T> destList = new ArrayList<>();
		try {
			T deskObj = null;
			for (E obj : srcList) {
				if(null == obj){
					continue;
				}
				deskObj = destClazz.newInstance();
				CachedBeanCopier.copy(obj, deskObj);
				destList.add(deskObj);
			}
		} catch (Exception e) {
			throw new BusinessException(CoreExceptionCodes.CACHE_BEAN_FAIL);
		}
		return destList;
	}

	/**
	 * 功能描述: 当源和目标类的属性类型不同字段相同时拷贝，性能相对简单对象拷贝低。(集合)；当目标对象的子集为空时，进行拷贝
	 * 
	 * @param srcList
	 * @param destList
	 * @param destClazz
	 *            必须有无参构造方法的对象，否则会报错 void
	 * @date 2016年7月25日 下午2:04:15
	 * @version 2.2.0
	 * @author llh
	 * @param <E>
	 * @throws BusinessException
	 *             CACHE_BEAN_FAIL
	 */
	@SuppressWarnings("all")
	public static <E, T> void copyAndCoverterList(Collection<E> srcCollection, Collection<T> destCollection,
			Class<T> destClazz) {
		try {
			T deskObj = null;
			for (E obj : srcCollection) {
				deskObj = destClazz.newInstance();
				CachedBeanCopier.copyAndCoverter(obj, deskObj);
				destCollection.add(deskObj);
			}
		} catch (Exception e) {
			throw new BusinessException(CoreExceptionCodes.CACHE_BEAN_FAIL);
		}
	}
	/**
	 * 功能描述: 当源和目标类的属性类型不同字段相同时拷贝，性能相对简单对象拷贝低。(集合)；当目标对象的子集为空时，不进行拷贝
	 * 
	 * @param srcList
	 * @param destList
	 * @param destClazz
	 *            必须有无参构造方法的对象，否则会报错 void
	 * @date 2016年7月25日 下午2:04:15
	 * @version 2.2.0
	 * @author llh
	 * @param <E>
	 * @throws BusinessException
	 *             CACHE_BEAN_FAIL
	 */
	@SuppressWarnings("all")
	public static <E, T> void copyAndFilterAndCoverterList(Collection<E> srcCollection, Collection<T> destCollection,
			Class<T> destClazz) {
		try {
			T deskObj = null;
			for (E obj : srcCollection) {
				if(null == obj){
					continue;
				}
				deskObj = destClazz.newInstance();
				CachedBeanCopier.copyAndCoverter(obj, deskObj);
				destCollection.add(deskObj);
			}
		} catch (Exception e) {
			throw new BusinessException(CoreExceptionCodes.CACHE_BEAN_FAIL);
		}
	}

	/**
	 * 功能描述: 当源和目标类的属性类型不同字段相同时拷贝，性能相对简单对象拷贝低。(集合)；当目标对象的子集为空时，进行拷贝
	 * 
	 * @param srcList
	 * @param destClazz 必须有无参构造方法的对象，否则会报错 void
	 *            void
	 * @date 2016年7月25日 下午2:04:15
	 * @version 2.2.0
	 * @author llh
	 * @param <E>
	 * @param <T>
	 * @throws BusinessException
	 *             CACHE_BEAN_FAIL
	 */
	public static <E, T> List<T> copyAndCoverterList(List<E> srcList, Class<T> destClazz) {
		List<T> destList = new ArrayList<>();
		try {
			T deskObj = null;
			for (E obj : srcList) {
				deskObj = destClazz.newInstance();
				CachedBeanCopier.copyAndCoverter(obj, deskObj);
				destList.add(deskObj);
			}
		} catch (Exception e) {
			throw new BusinessException(CoreExceptionCodes.CACHE_BEAN_FAIL);
		}
		return destList;
	}
	/**
	 * 功能描述: 当源和目标类的属性类型不同字段相同时拷贝，性能相对简单对象拷贝低。(集合)；当目标对象的子集为空时，不进行拷贝
	 * 
	 * @param srcList
	 * @param destClazz 必须有无参构造方法的对象，否则会报错 void
	 *            void
	 * @date 2016年7月25日 下午2:04:15
	 * @version 2.2.0
	 * @author llh
	 * @param <E>
	 * @param <T>
	 * @throws BusinessException
	 *             CACHE_BEAN_FAIL
	 */
	public static <E, T> List<T> copyAndFilterAndCoverterList(List<E> srcList, Class<T> destClazz) {
		List<T> destList = new ArrayList<>();
		try {
			T deskObj = null;
			for (E obj : srcList) {
				if(null == obj){
					continue;
				}
				deskObj = destClazz.newInstance();
				CachedBeanCopier.copyAndCoverter(obj, deskObj);
				destList.add(deskObj);
			}
		} catch (Exception e) {
			throw new BusinessException(CoreExceptionCodes.CACHE_BEAN_FAIL);
		}
		return destList;
	}

	private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
		return srcClazz.getName() + destClazz.getName();
	}

}