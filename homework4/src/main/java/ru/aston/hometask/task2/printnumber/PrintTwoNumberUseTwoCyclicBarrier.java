package ru.aston.hometask.task2.printnumber;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class PrintTwoNumberUseTwoCyclicBarrier {
    private static final long SLEEP_TIME = 100L;
    private static final int FIRST_NUMBER = 1;
    private static final int SECOND_NUMBER = 2;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
        CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(FIRST_NUMBER);
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                    cyclicBarrier1.await();
                    cyclicBarrier2.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    cyclicBarrier1.await();
                    System.out.println(SECOND_NUMBER);
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                    cyclicBarrier2.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread2.start();
        thread1.start();

    }
}
