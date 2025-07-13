package ru.aston.hometask.task1.deadlock;

import ru.aston.hometask.task1.deadlock.util.DetectorDeadlock;
import ru.aston.hometask.task1.dto.BankAccount;

public class DeadLockRunner {
    private static final int START_BALANCE = 10_000;
    private static final int TRANSFER_AMOUNT = 100;

    public static void main(String[] args) {
        BankAccount bankAccount1 = new BankAccount(START_BALANCE);
        BankAccount bankAccount2 = new BankAccount(START_BALANCE);

        Thread transfer1 = new Thread(new AccountTransferDeadlock(bankAccount1, bankAccount2, TRANSFER_AMOUNT));
        Thread transfer2 = new Thread(new AccountTransferDeadlock(bankAccount2, bankAccount1, TRANSFER_AMOUNT));
        Thread detectDeadlock = new Thread(DetectorDeadlock::detectDeadlock);

        detectDeadlock.setDaemon(true);

        detectDeadlock.start();
        transfer1.start();
        transfer2.start();

        try {
            transfer1.join();
            transfer2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Main прерван", e);
        }

        if (!checkBalance(bankAccount1, bankAccount2)) {
            System.err.println("Баланс не совпал с ожидаемым");
        }
    }

    public static boolean checkBalance(BankAccount bankAccount1, BankAccount bankAccount2) {
        return bankAccount1.getBalance() == START_BALANCE &&
                bankAccount2.getBalance() == START_BALANCE;
    }
}
