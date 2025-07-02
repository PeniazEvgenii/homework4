package ru.aston.hometask.task1.deadlock;

import ru.aston.hometask.task1.dto.BankAccount;

import java.util.concurrent.locks.Lock;

public class AccountTransferDeadlock implements Runnable {
    private static final int COUNT_TRANSFERS = 100;

    private final BankAccount accountTo;
    private final BankAccount accountFrom;
    private final int transfer;

    public AccountTransferDeadlock(BankAccount to, BankAccount from, int transfer) {
        this.accountTo = to;
        this.accountFrom = from;
        this.transfer = transfer;
    }

    @Override
    public void run() {
        Lock lockTo = accountTo.getLock();
        Lock lockFrom = accountFrom.getLock();

        for (int i = 0; i < COUNT_TRANSFERS; i++) {
            try {
                lockFrom.lock();
                System.out.printf("[%s] - получил lock %s\n", Thread.currentThread().getName(), accountFrom.getId());

                try {
                    lockTo.lock();
                    System.out.printf("[%s] - получил lock %s\n", Thread.currentThread().getName(), accountTo.getId());

                    if (!accountFrom.reduce(transfer)) {
                        throw new IllegalStateException("Недостаточно средств на балансе " + accountFrom.getId());
                    }

                    accountTo.add(transfer);
                    System.out.printf("[%s] - Перевод прошел успешно\n", Thread.currentThread().getName());
                } finally {
                    lockTo.unlock();
                    System.out.printf("[%s] - отпустил lock %s\n", Thread.currentThread().getName(), accountTo.getId());
                }
            } catch (IllegalStateException e) {
                System.err.printf("[%s] - ошибка при переводе перевода: %s\n", Thread.currentThread().getName(), e.getMessage());
            } finally {
                lockFrom.unlock();
                System.out.printf("[%s] отпустил lock %s\n", Thread.currentThread().getName(), accountFrom.getId());
            }
        }
    }
}
