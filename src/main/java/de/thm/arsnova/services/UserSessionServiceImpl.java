package de.thm.arsnova.services;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import de.thm.arsnova.entities.Session;
import de.thm.arsnova.entities.User;
import de.thm.arsnova.events.ARSnovaEvent;
import de.thm.arsnova.events.ARSnovaEvent.Destination;
import de.thm.arsnova.socket.ARSnovaSocketIOServer;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionServiceImpl implements UserSessionService, Serializable {
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Session session;
	private UUID socketId;

	@Override
	public void setUser(User u) {
		this.user = u;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void setSession(Session s) {
		this.session = s;
	}

	@Override
	public Session getSession() {
		return this.session;
	}

	@Override
	public void setSocketId(UUID sId) {
		this.socketId = sId;
	}

	@Override
	public UUID getSocketId() {
		return this.socketId;
	}

	private boolean hasConnectedWebSocket() {
		return getSocketId() != null;
	}

	@Override
	public void sendEventViaWebSocket(ARSnovaSocketIOServer server, ARSnovaEvent event) {
		if (
				event.getDestinationType() == Destination.SESSION
				&& hasConnectedWebSocket()
				&& event != null && session != null
				&& event.getSessionKey().equals(session.getKeyword())
		) {
			server.sendToClient(getSocketId(), event);
		}
		
		if (
				event.getDestinationType() == Destination.USER
				&& hasConnectedWebSocket()
				&& event != null && user != null
				&& event.getRecipient().getUsername().equals(user.getUsername())
		) {
			server.sendToClient(getSocketId(), event);
		}
	}
}
