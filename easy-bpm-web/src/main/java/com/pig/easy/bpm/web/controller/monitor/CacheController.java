package com.pig.easy.bpm.web.controller.monitor;


import com.pig.easy.bpm.auth.core.controller.BaseController;
import com.pig.easy.bpm.common.utils.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 缓存监控
 *
 * @author pig
 */
@RestController
@RequestMapping("/monitor/cache")
public class CacheController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "查询redis信息", notes = "查询redis信息", produces = "application/json")
    @PostMapping("/getRedisInfo")
    public JsonResult getRedisInfo() throws Exception {

        //Properties info = stringRedisTemplate.getRequiredConnectionFactory().getConnection().info();
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);

        return JsonResult.success(result);
    }

}
