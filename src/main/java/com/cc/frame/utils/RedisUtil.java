package com.cc.frame.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RedisUtil<T> implements RedisSerializer<T> {
	public static final Charset CHARSET = StandardCharsets.UTF_8;
	private Class<T> clazz;

	static final Filter autoTypeFilter = JSONReader.autoTypeFilter(
			// 按需加上需要支持自动类型的类名前缀，范围越小越安全
			"org.springframework.security.core.authority.SimpleGrantedAuthority",
			"com.cc.*"
	);

	public RedisUtil(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public byte[] serialize(T value) throws SerializationException {
		if (value == null) {
			return new byte[0];
		}
		return JSON.toJSONString(value, JSONWriter.Feature.WriteClassName).getBytes(CHARSET);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		String str = new String(bytes, CHARSET);
		return JSON.parseObject(str, clazz, autoTypeFilter);
	}

	protected JavaType getCollectionType(Class<?> clazz) {
		return TypeFactory.defaultInstance().constructParametricType(ArrayList.class, clazz);
	}
}
