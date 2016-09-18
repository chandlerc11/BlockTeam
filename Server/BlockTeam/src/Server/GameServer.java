package Server;

import java.util.ArrayList;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import Game.GameConstants.GameMoves;
import Game.TetrisGame;

public class GameServer {
	private static final GameServer gameServer = new GameServer();
	private ArrayList<GamerSocket> gamerList = new ArrayList<GamerSocket>();
	private static TetrisGame mainGame;
	
	public static void main(String[] args) throws Exception 
	{
		Server server = new Server(8080);
	    WebSocketHandler wsHandler = new WebSocketHandler() {
	    	@Override
	        public void configure(WebSocketServletFactory factory) {
	    		factory.register(GamerSocket.class);
	        }
	    };
	    
	    server.setHandler(wsHandler);
	    server.start();
	    server.join();
	    
	    Thread t = new Thread(new TetrisGame(gameServer));
	    t.start();
	    while(t.isAlive());
	    	
	}
	
	public static GameServer getGamerServer()
	{
		return gameServer;
	}
	
	public synchronized void addGamerToList(GamerSocket gs)
	{
		gamerList.add(gs);
	}
	
	public synchronized void removeGamer(GamerSocket gs)
	{
		gamerList.remove(gs);
	}
	
	public synchronized void sendDataToGamers(String data)
	{
		for(GamerSocket gs: gamerList)
		{
			gs.sendMessage(data);
		}
	}
	
	public synchronized void gamerInput(GameMoves move)
	{
		mainGame.gamersMove(move);
	}
	
	public synchronized void startGame()
	{
		String message = "Starting Game";
		System.out.println(message);
		sendDataToGamers(message);
	}
	
	public synchronized void rotateClockwise()
	{
		String message = "Rotate Clockwise";
		System.out.println(message);
		sendDataToGamers(message);
	}
	
	public synchronized void rotateCounterClockwise()
	{
		String message = "Rotate Counter Clockwise";
		System.out.println(message);
		sendDataToGamers(message);
	}
	
	public synchronized void left()
	{
		String message = "Going Left";
		System.out.println(message);
		sendDataToGamers(message);
	}
	
	public synchronized void right()
	{
		String message = "Going Right";
		System.out.println(message);
		sendDataToGamers(message);
	}
	
	public synchronized void down()
	{
		String message = "Moving Down";
		System.out.println(message);
		sendDataToGamers(message);
	}
	
	public synchronized void allDown()
	{
		String message = "Moving all the way down.";
		System.out.println(message);
		sendDataToGamers(message);
	}
	
	public synchronized void pause()
	{
		String message = "Pausing Game";
		System.out.println(message);
		sendDataToGamers(message);
	}
	
	

}
