package com.zb.elasticsearch.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: json转换工具类
 * @author: zhangbing
 * @create: 2019-12-03 14:57
 **/
@Log4j2
public class JsonUtils {

    private static ObjectMapper mapper = null;
    private static ObjectMapper mapperWithType = null;
    private static final String WRITE_JSON_ERROR_MESSAGE="write to json string error:";
    private static final String PARSE_JSON_ERROR_MESSAGE="parse json string error:";

    static {
        mapper = new ObjectMapper();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 允许key没有使用双引号的json
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 零时区
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(format);

        mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        mapperWithType = new ObjectMapper();
        mapperWithType.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    private JsonUtils() {
    }


    /**
     * 将对象转换成JSON字符串
     *
     * @param obj
     *            目标对象
     * @return 字符串，转换失败时返回null
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(WRITE_JSON_ERROR_MESSAGE + obj, e);
            return null;
        }
    }
    /**
     * 将对象转换成JSON字符串
     * 过滤字段为null的
     * @param obj
     *            目标对象
     * @return 字符串，转换失败时返回null
     */
    public static String toJsonFilterNull(Object obj) {
        try {
            //对象转为json时 字段为null的不参与序列化
            return mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(WRITE_JSON_ERROR_MESSAGE + obj, e);
            return null;
        }
    }

    /**
     * 将单个键值对转换成JSON字符串，用于返回只有一个键值对json时的便捷方法
     *
     * @return 字符串，转换失败时返回null
     */
    public static String toJson(Object key, Object value) {
        Map<Object, Object> map = new HashMap<>();
        map.put(key, value);
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error(WRITE_JSON_ERROR_MESSAGE + map, e);
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     *
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     *
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
     *
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error(PARSE_JSON_ERROR_MESSAGE + jsonString, e);
            return null;
        }
    }


    /**
     * 反序列化复杂Collection如List<Bean>
     *
     * @param jsonString
     * @param collectionClass
     *            集合类型
     * @param elementClass
     *            集合内元素类型
     * @return 转换失败时返回 null
     */
    public static <L extends Collection<E>, E> L fromJson(String jsonString,
                                                          Class<L> collectionClass, Class<E> elementClass) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            CollectionType type = mapper.getTypeFactory()
                    .constructCollectionType(collectionClass, elementClass);
            return mapper.readValue(jsonString, type);
        } catch (Exception e) {
            log.error(PARSE_JSON_ERROR_MESSAGE + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂类型<br/>
     * 针对复杂类型反序列化时，自动转化为LinkedHashMap问题
     * @param jsonString
     * @param typeRef 自定义解析器类型
     * @return
     */
    public static  <T> T fromJson(String jsonString, TypeReference<T> typeRef) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, typeRef);
        } catch (Exception e) {
            log.error(PARSE_JSON_ERROR_MESSAGE + jsonString, e);
            return null;
        }
    }


    /**
     * 当JSON里只含有Bean的部分属性，更新一个已存在Bean，只覆盖该部分的属性.
     */
    public static void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (IOException e ) {
            log.error("update json string:" + jsonString + " to object:"
                    + object + " error.", e);
        }
    }

    /**
     * 转换JSON对象为ajax的方式<br />
     * ex.{"success":"false","data":{"name":"名称已存在"}}
     *
     * @param obj
     *            错误信息，若用map，Key若为表单项的name，则会自动在对应表单项显示错误信息
     */
    public static String toFormJson(Object obj, boolean success) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("data", obj);
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error(WRITE_JSON_ERROR_MESSAGE + obj, e);
            return null;
        }
    }

    /**
     * 输出JSONP格式数据
     */
    public static String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 将Object转换成Json，维持多态类型.
     * 该Json不能用于rest请求
     * @return
     */
    public static String toJsonAsPolymorphism(Object obj){
        try {
            return mapperWithType.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    /**
     * 将Json转换成Object，维持多态类型.
     * 该转换不能用于rest响应
     * @return
     */
    public static <T> T fromJsonAsPolymorphism(String json,Class<T> clazz){
        try {
            return mapperWithType.readValue(json, clazz);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
