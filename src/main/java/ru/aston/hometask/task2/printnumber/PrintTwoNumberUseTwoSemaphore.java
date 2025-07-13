package ru.aston.hometask.task2.printnumber;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class PrintTwoNumberUseTwoSemaphore {
    private static final long SLEEP_TIME = 100L;
    private static final int FIRST_NUMBER = 1;
    private static final int SECOND_NUMBER = 2;

    public static void main(String[] args) {
        Semaphore semaphore0 = new Semaphore(0);
        Semaphore semaphore1 = new Semaphore(1);

        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    semaphore1.acquire();
                    System.out.println(FIRST_NUMBER);
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                    semaphore0.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    semaphore0.acquire();
                    System.out.println(SECOND_NUMBER);
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                    semaphore1.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread2.start();
        thread1.start();

    }
}
