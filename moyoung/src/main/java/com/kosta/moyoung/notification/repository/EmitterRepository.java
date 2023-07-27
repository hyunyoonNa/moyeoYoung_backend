package com.kosta.moyoung.notification.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

    private Map<String, SseEmitter> emitterMap = new HashMap<>();

    public SseEmitter save(Long receiverId, SseEmitter emitter) {
        final String key = getKey(receiverId);
        emitterMap.put(key, emitter);
        return emitter;
    }
    
    public void delete(Long receiverId) {
        emitterMap.remove(getKey(receiverId));
    }

    public Optional<SseEmitter> get(Long receiverId) {
        SseEmitter result = emitterMap.get(getKey(receiverId));
        return Optional.ofNullable(result);
    }

    private String getKey(Long receiverId) {
        return "emitter:UID:" + receiverId;
    }
    
    
    

}

