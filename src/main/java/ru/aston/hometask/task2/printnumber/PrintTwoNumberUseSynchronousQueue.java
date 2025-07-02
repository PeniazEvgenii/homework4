package ru.aston.hometask.task2.printnumber;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class PrintTwoNumberUseSynchronousQueue {
    private static final int FIRST_NUMBER = 1;
    private static final int SECOND_NUMBER = 2;

    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        Thread numberTwoPrint = new Thread(() -> {
            try {
                queue.put(FIRST_NUMBER);
                while (!Thread.currentThread().isInterrupted()) {
                    Integer num = queue.take();

                    System.out.println(num);
                    TimeUnit.SECONDS.sleep(1L);

                    queue.put(FIRST_NUMBER);
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });

        Thread numberOnePrint = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Integer num = queue.take();

                    System.out.println(num);
                    TimeUnit.SECONDS.sleep(1L);

                    queue.put(SECOND_NUMBER);
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });

        numberOnePrint.start();
        numberTwoPrint.start();
    }
}
