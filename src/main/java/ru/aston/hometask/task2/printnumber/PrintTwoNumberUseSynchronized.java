package ru.aston.hometask.task2.printnumber;

public class PrintTwoNumberUseSynchronized {
    private static final int FIRST_NUMBER = 1;
    private static final int SECOND_NUMBER = 2;

    public static void main(String[] args) {
        NumPrinter numPrinter = new NumPrinter(FIRST_NUMBER);

        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                numPrinter.print(FIRST_NUMBER, SECOND_NUMBER);
            }
        });

        Thread thread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                numPrinter.print(SECOND_NUMBER, FIRST_NUMBER);
            }
        });

        thread1.start();
        thread2.start();
    }


    private static class NumPrinter {
        private final Object object = new Object();
        private int num;

        public NumPrinter(int num) {
            this.num = num;
        }

        public void print(int currentNum, int nextNum) {
            try {
                synchronized (object) {
                    while (num != currentNum) {
                        object.wait();
                    }
                    System.out.println(currentNum);
                    num = nextNum;
                    object.notifyAll();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        public int getNum() {
            return num;
        }
    }
}


