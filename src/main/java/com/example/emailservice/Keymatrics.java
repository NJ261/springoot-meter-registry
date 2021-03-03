package com.example.emailservice;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Keymatrics {

    private Timer requestTime;

    public Keymatrics(){

    }

    public void getTotalTime(final MeterRegistry meterRegistry, final long startTime){
        this.requestTime = Timer.builder("request-time").publishPercentileHistogram().register(meterRegistry);
        this.requestTime.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);

        System.out.println("\n\n" + this.requestTime.totalTime(TimeUnit.NANOSECONDS) + "\n\n" + this.requestTime.takeSnapshot());
    }


}
