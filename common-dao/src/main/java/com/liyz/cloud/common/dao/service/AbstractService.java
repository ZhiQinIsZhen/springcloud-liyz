package com.liyz.cloud.common.dao.service;

import com.liyz.cloud.common.dao.exception.DaoServiceException;
import com.liyz.cloud.common.dao.mapper.Mapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 注释:基于通用MyBatis Mapper插件的Service接口的实现
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/28 18:04
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    /**
     * 当前泛型真实类型的Class
     */
    private Class<T> modelClass;

    /**
     * 构造方法
     */
    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public int save(T model) {
        return mapper.insertSelective(model);
    }

    @Override
    public int save(List<T> models) {
        return mapper.insertList(models);
    }

    @Override
    public int removeById(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateById(T model) {
        return mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T getById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T getBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            List<T> list = mapper.select(model);
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            return list.get(0);
        } catch (ReflectiveOperationException e) {
            throw new DaoServiceException(e.getMessage(), e);
        }
    }

    @Override
    public T getOne(T model) {
        List<T> list = mapper.select(model);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<T> listByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> listByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> list(T model) {
        return mapper.select(model);
    }

    @Override
    public List<T> listAll() {
        return mapper.selectAll();
    }
}
