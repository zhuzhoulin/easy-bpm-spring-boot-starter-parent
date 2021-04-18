package com.pig.easy.bpm.api.core.event;

import java.util.EventObject;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/20 15:35
 */
public class BpmEvent extends EventObject {

    private static final long serialVersionUID = 420795188944736778L;
    /**
     * The object on which the Event initially occurred.
     */
    protected transient BpmSourceDTO bpmSourceDTO;

    /**
     * Constructs a prototypical Event.
     *
     * @param bpmSourceDTO The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public BpmEvent(BpmSourceDTO bpmSourceDTO) {
        super(bpmSourceDTO);
        this.bpmSourceDTO = bpmSourceDTO;
    }

    /**
     * depreacated , please replace with  getBpmSourceDTO
     *
     */
    @Deprecated
    @Override
    public BpmSourceDTO getSource() {
        return this.bpmSourceDTO;
    }

    public BpmSourceDTO getBpmSourceDTO() {
        return this.bpmSourceDTO;
    }

    @Override
    public String toString() {
        return bpmSourceDTO.toString();
    }
}
