package com.devh.common.interfaces;

import com.devh.common.constant.ScheduleStatus;

public interface IScheduler {
    void scheduleStart();
    ScheduleStatus getScheduleStatus();
}
