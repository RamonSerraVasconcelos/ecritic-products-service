package com.icritic.ecritic_products_service.dataprovider.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvalidatePaginationCache {

    private final Jedis jedis;

    public void execute(CacheKeys cacheKeys) {
        ScanParams scanParams = new ScanParams();
        scanParams.match(cacheKeys.getKey().split(":")[0].concat("*"));

        Set<String> keysToDelete = new HashSet<>();
        String nextCursor = "0";

        do {
            ScanResult<String> scanResult = jedis.scan(nextCursor, scanParams);
            List<String> keys = scanResult.getResult();
            keysToDelete.addAll(keys);
            nextCursor = scanResult.getCursor();
        } while (!nextCursor.equals("0"));

        if(!keysToDelete.isEmpty()) {
            jedis.del(keysToDelete.toArray(new String[0]));
        }
    }
}
