package ru.aston.hometask.task1.resolved;

import ru.aston.hometask.task1.dto.BankAccount;

import java.util.concurrent.locks.Lock;

public class AccountTransferDeadLockResolved implements Runnable {
    private static final int COUNT_TRANSFERS = 100;

    private final BankAccount accountTo;
    private final BankAccount accountFrom;
    private final int transfer;

    public AccountTransferDeadLockResolved(BankAccount to, BankAccount from, int transfer) {
        this.accountTo = to;
        this.accountFrom = from;
        this.transfer = transfer;
    }

    @Override
    public void run() {

        for (int i = 0; i < COUNT_TRANSFERS; i++) {
            getLocks();
            try {
                System.out.printf("[%s] - получил lock обоих аккаунтов\n", Thread.currentThread().getName());
                if (accountFrom.reduce(transfer)) {
                    accountTo.add(transfer);
                    System.out.printf("[%s] - Аккаунт %s перевел на аккаунт %s\n",
                            Thread.currentThread().getName(),
                            accountFrom.getId(),
                            accountTo.getId());
                }
            } finally {
                accountTo.getLock().unlock();
                accountFrom.getLock().unlock();
                System.out.printf("[%s] - отпустил locks двух аккаунтов\n", Thread.currentThread().getName());
            }
        }
    }

    private void getLocks() {
        Lock lockAccountFrom = accountFrom.getLock();
        Lock lockAccountTo = accountTo.getLock();

        while (true) {
            boolean isLockTo = lockAccountTo.tryLock();
            boolean isLockFrom = lockAccountFrom.tryLock();

            if (isLockTo && isLockFrom) {
                return;
            }

            if (isLockTo) {
                lockAccountTo.unlock();
            } else if (isLockFrom) {
                lockAccountFrom.unlock();
            }
        }
    }
}
