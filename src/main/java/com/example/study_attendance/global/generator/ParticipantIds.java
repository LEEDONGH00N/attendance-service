package com.example.study_attendance.global.generator;

import java.util.concurrent.atomic.AtomicLong;

public class ParticipantIds {
    private static final AtomicLong id =  new AtomicLong(1);

    public static Long generateId(){
        return id.getAndIncrement();
    }
}
