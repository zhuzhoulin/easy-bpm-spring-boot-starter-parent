package com.pig.easy.bpm.common.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig.easy.bpm.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * todo:
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    public final static Integer DEFAULT_PAGE_INDEX = 1;

    public final static Integer DEFAULT_PAGE_SIZE = 10;

    public final static Integer MAX_PAGE_SIZE = 100000;

    public final static int INVALID_STATE = 0;

    public final static int VALID_STATE = 1;

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}
