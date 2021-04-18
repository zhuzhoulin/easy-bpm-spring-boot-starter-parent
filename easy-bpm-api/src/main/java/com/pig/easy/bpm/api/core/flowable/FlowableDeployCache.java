package com.pig.easy.bpm.api.core.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.persistence.deploy.DeploymentCache;
import org.flowable.engine.impl.persistence.deploy.ProcessDefinitionCacheEntry;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * todo: 后续可以通过放入redis中
 *
 * @author : pig
 * @date : 2020/3/16 17:28
 */
@Component
@Slf4j
public class FlowableDeployCache implements DeploymentCache<ProcessDefinitionCacheEntry> {

    private ConcurrentHashMap<String, ProcessDefinitionCacheEntry> cachesLocal = new ConcurrentHashMap<String, ProcessDefinitionCacheEntry>();

    @Override
    public ProcessDefinitionCacheEntry get(String id) {
        return cachesLocal.get(id);
    }

    @Override
    public boolean contains(String id) {
        boolean flag = false;
        if (cachesLocal.get(id) != null) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void add(String id, ProcessDefinitionCacheEntry object) {
        log.info("add ### {} , {}", id, object);
        cachesLocal.put(id, object);
    }

    @Override
    public void remove(String id) {
        log.info("remove ### {} , {}", id);
        cachesLocal.remove(id);
    }

    @Override
    public void clear() {
        log.info("clear ### {}");
        cachesLocal.clear();
    }

    /**
     * 对象转byte
     *
     * @param obj
     * @return
     */
    private byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * byte转对象
     *
     * @param bytes
     * @return
     */
    private Object ByteToObject(byte[] bytes) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }
}
