package ru.aston.hometask.task2.printnumber;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class TwoExchanger {
    private static final long SLEEP_TIME = 300L;
    private static final int FIRST_NUMBER = 1;
    private static final int SECOND_NUMBER = 2;

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        Thread thread1 = new Thread(() -> {
            Integer exchange = FIRST_NUMBER;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(exchange);
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                    exchanger.exchange(SECOND_NUMBER);
                    exchange = exchanger.exchange(null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Integer exchange = exchanger.exchange(null);
                    System.out.println(exchange);
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                    exchanger.exchange(FIRST_NUMBER);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread2.start();
        thread1.start();
    }
}
