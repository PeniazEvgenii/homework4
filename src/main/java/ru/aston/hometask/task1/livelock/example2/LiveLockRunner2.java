package ru.aston.hometask.task1.livelock.example2;

import ru.aston.hometask.task1.dto.BankAccount;

import static ru.aston.hometask.task1.deadlock.DeadLockRunner.checkBalance;

public class LiveLockRunner2 {
    private static final int START_BALANCE = 10_000;
    private static final int TRANSFER_AMOUNT = 100;

    public static void main(String[] args) {
        BankAccount bankAccount1 = new BankAccount(START_BALANCE);
        BankAccount bankAccount2 = new BankAccount(START_BALANCE);

        Thread thread1 = new Thread(new AccountTransferLivelock2(bankAccount1, bankAccount2, TRANSFER_AMOUNT));
        Thread thread2 = new Thread(new AccountTransferLivelock2(bankAccount2, bankAccount1, TRANSFER_AMOUNT));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!checkBalance(bankAccount1, bankAccount2)) {
            System.err.println("Баланс не совпал с ожидаемым");
        }
    }
}
