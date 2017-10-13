import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/TeamChat")
public class TeamChatServer {
	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
		
	@OnOpen
    public void open(Session session) {
		peers.add(session);
	}

	@OnClose
    public void close(Session session) {
		peers.remove(session);
	}

	@OnError
    public void onError(Throwable error) {
	}

	@OnMessage
    public void handleMessage(String message, Session session) {
		for(Session peer:peers){
			try {
				peer.getBasicRemote().sendText(message);
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}

}
