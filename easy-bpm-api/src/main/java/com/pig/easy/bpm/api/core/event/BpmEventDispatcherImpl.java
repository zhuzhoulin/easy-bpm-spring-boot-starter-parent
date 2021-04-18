package com.pig.easy.bpm.api.core.event;


import com.pig.easy.bpm.common.entityError.EntityError;
import com.pig.easy.bpm.common.execption.BpmException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/20 15:12
 */
@Slf4j
public class BpmEventDispatcherImpl implements BpmEventDispatcher {

    /**
     * 全局事件监听器
     */
    private List<BpmEventListener> eventListeners;
    /**
     * 类型事件监听器
     */
    private Map<BpmEventType, List<BpmEventListener>> typedListeners;

    private boolean enabled = true;

    public BpmEventDispatcherImpl() {
        eventListeners = new CopyOnWriteArrayList<>();
        typedListeners = new ConcurrentHashMap<>();
    }

    @Override
    public void addEventListener(BpmEventListener listenerToAdd) {
        if (listenerToAdd == null) {
            throw new BpmException(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), "Listener cannot be null."));
        }
        if (!eventListeners.contains(listenerToAdd)) {
            eventListeners.add(listenerToAdd);
        }
    }

    @Override
    public void addEventListener(BpmEventListener listenerToAdd, BpmEventType... types) {
        if (listenerToAdd == null) {
            throw new BpmException(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), "Listener cannot be null."));
        }

        if (types == null || types.length == 0) {
            addEventListener(listenerToAdd);

        } else {
            for (BpmEventType type : types) {
                addTypedEventListener(listenerToAdd, type);
            }
        }
    }

    @Override
    public void removeEventListener(BpmEventListener listenerToRemove) {
        eventListeners.remove(listenerToRemove);

        for (List<BpmEventListener> listeners : typedListeners.values()) {
            listeners.remove(listenerToRemove);
        }
    }

    @Override
    public void dispatchEvent(BpmEvent event) {

        if (event == null
                || event.getBpmSourceDTO().getEventType() == null) {
            throw new BpmException(new EntityError(EntityError.ILLEGAL_ARGUMENT_ERROR.getCode(), "Event cannot be null."));
        }

        // Call global listeners
        if (!eventListeners.isEmpty()) {
            for (BpmEventListener listener : eventListeners) {
                dispatchEvent(event, listener);
            }
        }

        // Call typed listeners, if any
        List<BpmEventListener> typed = typedListeners.get(event.getBpmSourceDTO().getEventType());
        if (typed != null && !typed.isEmpty()) {
            for (BpmEventListener listener : typed) {
                dispatchEvent(event, listener);
            }
        }

    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    private void dispatchEvent(BpmEvent event, BpmEventListener listener) {
        dispatchNormalEventListener(event, listener);
    }

    private void dispatchNormalEventListener(BpmEvent event, BpmEventListener listener) {
        try {
            CompletableFuture.runAsync(() -> {
                listener.onEvent(event);
            });
        } catch (Throwable t) {
            // Ignore the exception and continue notifying remaining listeners. The listener
            // explicitly states that the exception should not bubble up
            log.warn("Exception while executing event-listener, which was ignored", t);
        }
    }

    private synchronized void addTypedEventListener(BpmEventListener listener, BpmEventType type) {
        List<BpmEventListener> listeners = typedListeners.get(type);
        if (listeners == null) {
            // Add an empty list of listeners for this type
            listeners = new CopyOnWriteArrayList<>();
            typedListeners.put(type, listeners);
        }

        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

}
