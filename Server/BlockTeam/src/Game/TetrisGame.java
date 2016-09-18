package Game;

import Game.GameConstants.GameMoves;
import Server.GameServer;

public class TetrisGame implements Runnable {
	private GameServer gameServer;
	private boolean gameRunning;
	private volatile boolean running = true;
	private PlayingField PF;
	
	public TetrisGame(GameServer gameServer)
	{
		this.gameServer = gameServer;
	}
	
	public void start()
	{
		run();
	}
	
	public void stop()
	{
		running = false;
	}

	@Override
	public void run() 
	{
		while(running)
		{
			while(gameRunning && running)
			{
				
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public void sendDataToGamers()
	{
		gameServer.sendDataToGamers(PF.currentScreen());
	}

	public void gamersMove(GameMoves move) 
	{
		
		
	}
	
	public void startGame()
	{
		gameRunning = true;
	}
	
	public void pauseGame()
	{
		gameRunning = false;
	}
	
	
}
