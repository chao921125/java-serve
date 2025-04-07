package com.cc.frame.utils;

import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtil extends BeanUtils {

	/**
	 * 单个类转换
	 *
	 * @param sourceObj
	 * @param targetClass
	 * @param <T>
	 * @return
	 */
	public static <T> T convert(Object sourceObj, Class<T> targetClass) {
		if (sourceObj == null || targetClass == null) {
			return null;
		}
		try {
			T targetObj = targetClass.getDeclaredConstructor().newInstance();
			BeanUtils.copyProperties(sourceObj, targetObj);
			return targetObj;
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
				 InvocationTargetException e) {
			return null;
		}
	}

	/**
	 * List之间转换
	 *
	 * @param sourceList
	 * @param targetClass
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> convertList(List<?> sourceList, Class<T> targetClass) {
		if (CollectionUtils.isEmpty(sourceList) || targetClass == null) {
			return Collections.emptyList();
		}
		return sourceList.stream().map(sourceObj -> convert(sourceObj, targetClass)).collect(Collectors.toList());
	}
}
