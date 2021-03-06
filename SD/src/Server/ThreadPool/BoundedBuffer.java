package Server.ThreadPool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Classe Bounded Buffer generica.
 *
 * @param <T> : tipo de dados do BoundedBuffer
 */

public class BoundedBuffer<T> {
    private ReentrantLock mutex;
    private Condition isEmpty;
    private Condition isFull;
    private T[] buffer;
    private int head, tail, count;

    /**
     * Metodo construtor
     *
     * @param capacity : capacidade do buffer
     */
    public BoundedBuffer(int capacity){
        this.buffer = (T[])new Object[capacity];
        this.mutex = new ReentrantLock();
        this.isEmpty = mutex.newCondition();
        this.isFull  = mutex.newCondition();
        this.head = this.tail = this.count;
    }

    /**
     * @return : devolve o numero de elementos no buffer
     */
    public synchronized boolean isEmpty(){
        return ( this.count == 0 );
    }

    /**
     * @param value : valor a inserir
     * @throws InterruptedException
     */
    public void put(T value) throws InterruptedException{
        mutex.lock();
        try {
            while ( this.count == buffer.length )
                isFull.await();
            this.buffer[tail] = value;
            this.count++;
            if ( ++tail == buffer.length )
                tail = 0;
            isEmpty.signal();
        }finally {
            mutex.unlock();
        }

    }

    /**
     * Remove um elemento do buffer
     *
     * @return : valor removido
     * @throws InterruptedException
     */
    public T get() throws InterruptedException{
        mutex.lock();
        try {
            while ( this.count == 0 )
                this.isEmpty.await();
            T value = this.buffer[head];
            this.count--;
            if ( ++head == buffer.length )
                head = 0;
            this.isFull.signal();
            return value;
        }finally {
            mutex.unlock();
        }
    }
}
