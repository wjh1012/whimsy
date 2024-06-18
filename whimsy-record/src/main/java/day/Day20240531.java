package day;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jh.wang
 * @since 2024/5/31
 */
public class Day20240531 {

    @Test
    public void hashMapTest() {
        final HashMap<Object, Object> map = new HashMap<>();

        map.put("key", "value");

        map.get("key");
    }


    @Test
    public void concurrentHashMapTest() {
        final ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        map.get("key");


    }


    @Test
    public void aTest() {
        final int x = 15 & 153;
        System.out.println(x);
    }

    @Test
    public void sortTest() {
        // 输入一句英文（小于1000字符），输出词频最高的单词（不区分单词大小写，且词频同等情况下单词由小到大排序）及其出现的次数。
        // 示例：
        String str = "Five Little Monkeys Jumping on the Bed. It was bedtime. So five little monkeys took a bath. Five little Monkeys put on their pajamas.";

        Arrays.stream(str.split(" "))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().max((c1, c2) -> {
                    if (Objects.equals(c1.getValue(), c2.getValue())) {
                        // 词频次数一致时 取单词大的 a -> z
                        return c2.getKey().compareTo(c1.getKey());
                    }
                    // 根据 词频次数 倒序
                    return c1.getValue().compareTo(c2.getValue());
                }).ifPresent(System.out::println);
    }
}
