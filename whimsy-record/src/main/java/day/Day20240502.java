package day;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jh.wang
 * @since 2024/5/1
 */

public class Day20240502 extends Thread {
    static final Object lock = new Object();
    static int count = 50;

    @Override
    public void run() {
        synchronized (lock) {
            while (count > 0) {
                final String name = Thread.currentThread().getName();

                if (count % threadMap.size() == threadMap.get(name)) {

                    System.out.printf("thread: %s  count: %s \r\n", name, count);
                    count--;

                    lock.notifyAll();
                }
                try {
                    lock.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    static Map<String, Integer> threadMap = new HashMap<>();

    public static void main(String[] args) {
        int threadCount = 5;
        for (int i = 0; i < threadCount; i++) {
            threadMap.put(String.valueOf((char) (65 + i)), i);
        }

        threadMap.forEach((k, v) -> {
            final Thread tread = new Day20240502();
            tread.setName(k);
            tread.start();
        });
    }

}
