package cn.wangjiahang.controller;

import cn.wangjiahang.entity.TestDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jh.wang
 * @since 2024/5/7
 */
@Slf4j
@RestController
public class TestController {
    /**
     * 为了 findAll 能有数据
     */
    public ConcurrentHashMap<String, TestDo> cache = new ConcurrentHashMap<>();
    public AtomicInteger atomicInteger =new AtomicInteger(1);

    @RequestMapping("getOne")
    @Cacheable(cacheNames = "user", key = "#id", condition = "#result != null")
    public TestDo getOne(String id) {
        return cache.get(id);
    }

    @RequestMapping("getAll")
    @Cacheable(cacheNames = "users", keyGenerator = "keyGenerator")
    public List<TestDo> getAll() {
        return cache.values().stream().toList();
    }

    @RequestMapping("create")
    @Caching(put = @CachePut(cacheNames = "user", key = "#result.id"),
            evict = @CacheEvict(cacheNames = "users", allEntries = true))
    public TestDo create() {
        final String key = atomicInteger.getAndIncrement() + "";

        final TestDo value = new TestDo().setId(key).setName(UUID.randomUUID().toString());
        cache.put(key, value);

        log.info(value.toString());
        return value;
    }
}
