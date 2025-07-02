package ru.aston.hometask.task1.dto;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private final Lock lock = new ReentrantLock();
    private final UUID id;
    private int balance;

    public BankAccount(int balance) {
        this.id = UUID.randomUUID();
        this.balance = balance;
    }

    public void add(int transfer) {
        balance += transfer;
    }

    public boolean reduce(int transfer) {
        if (transfer <= balance) {
            balance -= transfer;
            return true;
        }
        return false;
    }

    public Lock getLock() {
        return lock;
    }

    public UUID getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
