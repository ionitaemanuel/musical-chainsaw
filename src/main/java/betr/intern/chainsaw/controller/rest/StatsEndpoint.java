package betr.intern.chainsaw.controller.rest;

import betr.intern.chainsaw.event.UserViewsUpdatedEvent;
import betr.intern.chainsaw.model.User;
import betr.intern.chainsaw.model.ViewRecord;
import betr.intern.chainsaw.service.UserService;
import betr.intern.chainsaw.service.UserStatsService;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@ServerEndpoint("/testin-stats")
public class StatsEndpoint {
    private Session session;
    private static Set<StatsEndpoint> sessions = new CopyOnWriteArraySet<>();
    private final UserService userService;
    private final UserStatsService userStatsService;

    public StatsEndpoint(UserService userService, UserStatsService userStatsService) {
        this.userService = userService;
        this.userStatsService = userStatsService;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sessions.add(this);
        broadcast("Connected: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        final Map<UUID, ViewRecord> map = userStatsService.getListUserByIdEndpointAccessMap();
        final Map<String, ViewRecord> asd = userService.findAllById(map.keySet()).stream()
                .collect(Collectors.toMap(User::getName, user -> map.get(user.getId())));
        broadcast("Received: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(this);
        broadcast("Disconnected: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private static void broadcast(String message) {
        sessions.forEach(endpoint -> {
            synchronized (endpoint) {
                endpoint.session.getAsyncRemote().sendText(message);
            }
        });
    }

    @EventListener
    public void handleUserViewsUpdated(UserViewsUpdatedEvent event) {
        try {
            String jsonPayload = event.getUserViews().toString();
            TextMessage textMessage = new TextMessage(jsonPayload);

            synchronized (sessions) {
                for (StatsEndpoint endpoint : sessions) {
                    if (endpoint.session.isOpen()) {
                        endpoint.session.getAsyncRemote().sendText(String.valueOf(textMessage));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erorare la trimiterea update-ului prin WebSocket: " + e.getMessage());
        }
    }
}
