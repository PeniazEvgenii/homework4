package ru.aston.hometask.task1.livelock.example1;

import ru.aston.hometask.task1.dto.BankAccount;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Работа основана на том, что потоки бесконечно уступают друг другу вместо того, чтобы сделать перевод:
 * 1. Поток захватывает лок первого реусурса.
 * 2. Проверяет, что другой поток уже захватил лок.
 * 3. Текущий поток отпускает свой лок, уступая ресурс.
 * Перевод не происходит, пока оба потока продолжают уступать друг другу
 * У каждого потока есть пара флагов:
 *  - isLock - показывает, что текущий поток захватил первый лок
 *  - isOtherLock - показывает, что второй поток захватил первый лок
 */
public class AccountTransferLivelock implements Runnable {
    private final BankAccount accountTo;
    private final BankAccount accountFrom;
    private final AtomicBoolean isLock;
    private final AtomicBoolean isOtherLock;
    private final int transfer;

    public AccountTransferLivelock(BankAccount to, BankAccount from, AtomicBoolean isLock, AtomicBoolean isOtherLock, int transfer) {
        this.accountTo = to;
        this.accountFrom = from;
        this.isLock = isLock;
        this.isOtherLock = isOtherLock;
        this.transfer = transfer;
    }

    @Override
    public void run() {
        int attempt = 0;

        while (!Thread.currentThread().isInterrupted()) {
            attempt++;
            System.out.printf("Поток [%s] - Попытка №%d: захватываю lock %s\n", Thread.currentThread().getName(), attempt, accountFrom.getId());

            if (!accountFrom.getLock().tryLock()) {
                System.out.printf("Поток [%s] не удалось захватить %s\n", Thread.currentThread().getName(), accountFrom.getId());
                continue;
            }

            try {
                isLock.set(true);
                Thread.sleep(100);

                if (isOtherLock.get()) {
                    System.out.printf("Поток [%s]. Проверяю, что другой поток держит lock и уступаю\n", Thread.currentThread().getName());
                    isLock.set(false);
                    continue;
                }

                System.out.printf("Поток [%s]. Пробую захватить второй lock %s\n", Thread.currentThread().getName(), accountTo.getId());
                if (accountTo.getLock().tryLock()) {
                    try {
                        if (accountFrom.reduce(transfer)) {
                            accountTo.add(transfer);
                            System.out.printf("[%s] - Перевод прошел успешно\n", Thread.currentThread().getName());
                        } else {
                            System.out.printf("[%s] - Недостаточно средств\n", Thread.currentThread().getName());
                        }
                        break;
                    } finally {
                        accountTo.getLock().unlock();
                    }
                } else {
                    System.out.printf("Поток [%s]. Аккаунт %s занят, отпускаем %s и пробуем снова\n",
                                Thread.currentThread().getName(), accountTo.getId(), accountFrom.getId());
                    isLock.set(false);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                accountFrom.getLock().unlock();
            }
        }
    }
}

