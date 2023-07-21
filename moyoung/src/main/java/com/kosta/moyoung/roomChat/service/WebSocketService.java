package com.kosta.moyoung.roomChat.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

@Service
@ServerEndpoint("/chat/{roomid}")
public class WebSocketService {
    private static Map<String, Set<Session>> sessions = new HashMap<>();
    @OnOpen
    public void onOpen(Session session, @PathParam("roomid")String roomid) throws IOException {
        Set<Session> roomSessionSet = sessions.getOrDefault(roomid, new HashSet<>());
        // 클라이언트 세션을 방 세션 집합에 추가
        roomSessionSet.add(session);
    	System.out.println(session);
    	System.out.println(roomid);

        // 방 세션 집합을 업데이트
    	sessions.put(roomid, roomSessionSet);
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("roomid")String roomid) throws IOException {
    	Set<Session> roomSessionSet = sessions.get(roomid);
    	System.out.println(session);
    	System.out.println(message);
    	System.out.println(roomid);
    	if (roomSessionSet != null) {
             for (Session s : roomSessionSet) {
                 s.getBasicRemote().sendText(message);
             }
         }
    }

    @OnClose
    public void onClose(Session session, @PathParam("roomid")String roomid) {
        Set<Session> roomSessionSet = sessions.get(roomid);
        if (roomSessionSet != null) {
            roomSessionSet.remove(session);
         }
    }
}

