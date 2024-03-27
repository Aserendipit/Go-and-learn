package com.buptwanggong.studytour.service;

import org.springframework.stereotype.Service;


public interface CacheService {

    Object get(String key);

    void set(String key, Object value);

    void delete(String key);

    boolean exists(String key);

    void clear();
}
