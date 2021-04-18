package com.pig.easy.bpm.api.core.event;

import java.util.EventListener;

/**
 * todo: 监听best bpm 事件
 *
 * @author : pig
 * @date : 2020/5/20 11:11
 */
public interface BpmEventListener extends EventListener {

    void onEvent(BpmEvent event);
}
