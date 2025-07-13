package ru.aston.hometask.task1.deadlock.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public final class DetectorDeadlock {
    private DetectorDeadlock() {
    }

    public static void detectDeadlock() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                long[] deadlocked = threadMXBean.findDeadlockedThreads();
                if (deadlocked != null && deadlocked.length > 0) {
                    System.err.println("deadlock is detected");
                }
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
