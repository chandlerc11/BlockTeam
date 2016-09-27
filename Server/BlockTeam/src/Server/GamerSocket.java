package Server;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import Game.GameConstants.GameMoves;

@WebSocket
public class GamerSocket 
{
	private Session session;
	private GameServer gameServer;
	@OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
        gameServer.removeGamer(this);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
    	this.session = session;
        System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        /*try {
            session.getRemote().sendString("Hello Webbrowser");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        
        gameServer = GameServer.getGamerServer();
        gameServer.addGamerToList(this);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
    	switch(message)
    	{
    		case "rt":gameServer.gamerInput(GameMoves.RIGHT);
    				break;
    		case "lf": gameServer.gamerInput(GameMoves.LEFT);
    				break;
    		case "r": gameServer.gamerInput(GameMoves.ROTATE);
    				break;
    		case "st": gameServer.gamerInput(GameMoves.START);
    				break;
    		case "d": gameServer.gamerInput(GameMoves.DOWN);
    				break;
    		case "aD": gameServer.gamerInput(GameMoves.ALLDOWN);
    				break;
    		case "p": gameServer.gamerInput(GameMoves.PAUSE);
    				break;
    	}
    }
    
    public void sendMessage(String message)
    {
    	try {
			session.getRemote().sendString(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
