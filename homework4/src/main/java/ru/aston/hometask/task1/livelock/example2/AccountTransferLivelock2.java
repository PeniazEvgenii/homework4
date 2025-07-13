package ru.aston.hometask.task1.livelock.example2;

import ru.aston.hometask.task1.dto.BankAccount;

/**
 * Два потока пытаются захватить по два лока.
 * Но у каждый поток захватывает первый лок и не отпускает, и дальше оба потока бесконечно пытаются захватить второй лок.
 */
public class AccountTransferLivelock2 implements Runnable {
    private final BankAccount accountTo;
    private final BankAccount accountFrom;
    private final int transfer;

    public AccountTransferLivelock2(BankAccount to, BankAccount from, int transfer) {
        this.accountTo = to;
        this.accountFrom = from;
        this.transfer = transfer;
    }

    @Override
    public void run() {
        int attempt = 0;
        boolean isLockTo = false;
        boolean isLockFrom = false;

        try {
            while (!isLockTo || !isLockFrom) {
                attempt++;

                System.out.printf("Поток [%s] - попытка №%d: пробуем захватить lock %s\n", Thread.currentThread().getName(), attempt, accountFrom.getId());
                isLockTo = accountTo.getLock().tryLock();
                Thread.currentThread().sleep(10);

                System.out.printf("Поток [%s] - попытка №%d: пробуем захватить lock %s\n", Thread.currentThread().getName(), attempt, accountTo.getId());
                isLockFrom = accountFrom.getLock().tryLock();
                Thread.currentThread().sleep(10);
            }

            System.out.printf("Поток [%s] - оба лока получены, переводим %d%n", Thread.currentThread().getName(), 1000);
            if (accountFrom.reduce(transfer)) {
                accountTo.add(transfer);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            accountTo.getLock().unlock();
            accountFrom.getLock().unlock();
        }
    }
}
