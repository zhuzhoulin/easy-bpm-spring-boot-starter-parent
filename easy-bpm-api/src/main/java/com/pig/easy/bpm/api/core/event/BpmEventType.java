package com.pig.easy.bpm.api.core.event;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/20 15:07
 */
public enum BpmEventType {

    AFTER_START_PROCESS,

    BEFOR_START_PROCESS,
    /**
     * New entity is created.
     */
    ENTITY_CREATED,

    /**
     * New entity has been created and all properties have been set.
     */
    ENTITY_INITIALIZED,

    /**
     * Existing entity us updated.
     */
    ENTITY_UPDATED,

    /**
     * Existing entity is deleted.
     */
    ENTITY_DELETED,

    /**
     * Existing entity has been suspended.
     */
    ENTITY_SUSPENDED,

    /**
     * Existing entity has been activated.
     */
    ENTITY_ACTIVATED,

    /**
     * A Timer has been scheduled.
     */
    TIMER_SCHEDULED,

    /**
     * Timer has been fired successfully.
     */
    TIMER_FIRED,

    /**
     * Timer has been cancelled (e.g. user task on which it was bounded has been completed earlier than expected)
     */
    JOB_CANCELED,

    /**
     * A job has been successfully executed.
     */
    JOB_EXECUTION_SUCCESS,


    JOB_EXECUTION_FAILURE,

    /**
     * The retry-count on a job has been decremented.
     */
    JOB_RETRIES_DECREMENTED,

    /**
     * The job has been rescheduled.
     */
    JOB_RESCHEDULED,

    /**
     * An event type to be used by custom events. These types of events are never thrown by the engine itself, only be an external API call to dispatch an event.
     */
    CUSTOM,

    /**
     * The process-engine that dispatched this event has been created and is ready for use.
     */
    ENGINE_CREATED,

    /**
     * The process-engine that dispatched this event has been closed and cannot be used anymore.
     */
    ENGINE_CLOSED,

    /**
     * An activity is starting to execute. This event is dispatched right before an activity is executed.
     */
    ACTIVITY_STARTED,

    /**
     * An activity has been completed successfully.
     */
    ACTIVITY_COMPLETED,

    /**
     * An activity has been cancelled because of boundary event.
     */
    ACTIVITY_CANCELLED,

    /**
     * A multi-instance activity is starting to execute. This event is dispatched right before an activity is executed.
     */
    MULTI_INSTANCE_ACTIVITY_STARTED,

    /**
     * A multi-instance activity has been completed successfully.
     */
    MULTI_INSTANCE_ACTIVITY_COMPLETED,

    /**
     * A multi-instance activity has met its condition and completed successfully.
     */
    MULTI_INSTANCE_ACTIVITY_COMPLETED_WITH_CONDITION,

    /**
     * A multi-instance activity has been cancelled.
     */
    MULTI_INSTANCE_ACTIVITY_CANCELLED,

    /**
     * A boundary, intermediate, or subprocess start signal catching event has started.
     */
    ACTIVITY_SIGNAL_WAITING,

    /**
     * An activity has received a signal. Dispatched after the activity has responded to the signal.
     */
    ACTIVITY_SIGNALED,

    /**
     * An activity is about to be executed as a compensation for another activity. The event targets the activity that is about to be executed for compensation.
     */
    ACTIVITY_COMPENSATE,

    /**
     * A boundary, intermediate, or subprocess start message catching event has started.
     */
    ACTIVITY_MESSAGE_WAITING,


    ACTIVITY_MESSAGE_RECEIVED,

    /**
     * A boundary, intermediate, or subprocess start message catching event has been cancelled.
     */
    ACTIVITY_MESSAGE_CANCELLED,


    ACTIVITY_ERROR_RECEIVED,


    HISTORIC_ACTIVITY_INSTANCE_CREATED,


    HISTORIC_ACTIVITY_INSTANCE_ENDED,


    SEQUENCEFLOW_TAKEN,

    /**
     * A new variable has been created.
     */
    VARIABLE_CREATED,

    /**
     * An existing variable has been updated.
     */
    VARIABLE_UPDATED,

    /**
     * An existing variable has been deleted.
     */
    VARIABLE_DELETED,

    /**
     * A task has been created. This is thrown when task is fully initialized (before TaskListener.EVENTNAME_CREATE).
     */
    TASK_CREATED,

    /**
     * A task as been assigned. This is thrown alongside with an {@link #ENTITY_UPDATED} event.
     */
    TASK_ASSIGNED,

    /**
     * A task has been completed. Dispatched before the task entity is deleted ( {@link #ENTITY_DELETED}). If the task is part of a process, this event is dispatched before the process moves on, as a
     * result of the task completion. In that case, a {@link #ACTIVITY_COMPLETED} will be dispatched after an event of this type for the activity corresponding to the task.
     */
    TASK_COMPLETED,

    /**
     * A task owner has been changed. This is thrown alongside with an {@link #ENTITY_UPDATED} event.
     */
    TASK_OWNER_CHANGED,

    /**
     * A task priority has been changed. This is thrown alongside with an {@link #ENTITY_UPDATED} event.
     */
    TASK_PRIORITY_CHANGED,

    /**
     * A task dueDate has been changed. This is thrown alongside with an {@link #ENTITY_UPDATED} event.
     */
    TASK_DUEDATE_CHANGED,

    /**
     * A task name has been changed. This is thrown alongside with an {@link #ENTITY_UPDATED} event.
     */
    TASK_NAME_CHANGED,

    /**
     * A process instance has been created. All basic properties have been set, but variables not yet.
     */
    PROCESS_CREATED,

    /**
     * A process instance has been started. Dispatched when starting a process instance previously created. The event PROCESS_STARTED is dispatched after the associated event ENTITY_INITIALIZED and
     * after the variables have been set.
     */
    PROCESS_STARTED,

    /**
     * A process has been completed. Dispatched after the last activity is ACTIVITY_COMPLETED. Process is completed when it reaches state in which process instance does not have any transition to
     * take.
     */
    PROCESS_COMPLETED,

    /**
     * A process has been completed with a terminate end event.
     */
    PROCESS_COMPLETED_WITH_TERMINATE_END_EVENT,

    /**
     * A process has been completed with an error end event.
     */
    PROCESS_COMPLETED_WITH_ERROR_END_EVENT,

    PROCESS_CANCELLED,

    HISTORIC_PROCESS_INSTANCE_CREATED,

    HISTORIC_PROCESS_INSTANCE_ENDED;
}
