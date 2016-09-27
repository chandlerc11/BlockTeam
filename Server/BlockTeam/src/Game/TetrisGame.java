package Game;

import Game.GameConstants.*;
import Server.GameServer;

/**
 * TetrisGame creates the playingBoard which the game represents as well as a thread to constantly update the game without
 * gamer interaction
 *
 */
public class TetrisGame  
{
	private GameServer gameServer;
	private boolean gameRunning;
	private boolean canUpdate;
	private PlayingBoard PB;
	private Thread gameUpdater;
	
	/**
	 * Constructor for TetrisGame
	 * @param gameServer The server on which the TetrisGame was instantiated from
	 */
	public TetrisGame(GameServer gameServer)
	{
		this.gameServer = gameServer;
		PB = new PlayingBoard(GameConstants.WIDTH, GameConstants.HEIGHT, this);
		gameRunning = false;
		canUpdate = true;
		start();
	}
	
	/**
	 * Creates and starts the thread used to update the game without gamer interaction
	 */
	public void start()
	{
		gameUpdater = new Thread(new RunningGame(this));
		gameUpdater.start();
	}	
	
	/**
	 * Sends the current TetrisBoard to all players connected to the server
	 */
	public void sendDataToGamers()
	{
		gameServer.sendDataToGamers(PB.currentBoard());
	}

	/**
	 * Receives GameMoves from gamers connected to the server
	 * @param move The specific GameMove a gamer has performed
	 */
	public synchronized void gamersMove(GameMoves move) 
	{
		if(gameRunning)
		{
			if(GameMoves.PAUSE == move)
			{
				pauseGame();
			}
			else
			{
				PB.addMoveToQueue(move);			
			}	
		}
		else if(move == GameMoves.START)
		{
			startGame();
		}
	}
	
	/**
	 * Starts the Game/Contiunes if the game was paused
	 */
	public void startGame()
	{
		gameRunning = true;
	}
	
	/**
	 * Pauses the Game
	 */
	public void pauseGame()
	{
		gameRunning = false;
	}

	/**
	 * Restarts the Game. Will be called if Game Over or all players leave the server
	 */
	public void restart() 
	{
		PB.restart();
		pauseGame();
	}
	
	/**
	 * @return If the game is currently running or not.
	 */
	public boolean isGameRunning()
	{
		return gameRunning;
	}

	/**
	 * Called if the game reaches a GameOver, resets the TetrisBoard and anounces to players
	 */
	public void gameOver() 
	{
		restart();
		gameServer.sendDataToGamers("Game Over");
	}
	
	public void pauseUpdate()
	{
		canUpdate = false;
	}
	
	public void startUpdate()
	{
		canUpdate = true;
	}
	
	public boolean canUpdate() 
	{
		return canUpdate;
	}
	
	/**
	 * Class used to update the TetrisBoard at the refresh rate
	 *
	 */
	private class RunningGame implements Runnable
	{
		private TetrisGame tetrisGame;
		private volatile boolean running;
		
		/**
		 * Constructor for RunningGame
		 * @param tetrisGame
		 */
		public RunningGame(TetrisGame tetrisGame)
		{
			this.tetrisGame = tetrisGame; 
		}		
		
		/**
		 * The method that is run while the GameServer is running. Updates the TetrisBoard at the refresh rate
		 */
		@Override
		public void run() 
		{
			running = true;
			while(running)
			{
				while(tetrisGame.isGameRunning())
				{
					while(!tetrisGame.canUpdate())
					{
						sleep();
					}
					System.out.println("Updating Game");
					long startTime = System.currentTimeMillis(); //fetch starting time
					tetrisGame.gamersMove(GameMoves.UPDATE);
					while((System.currentTimeMillis() - startTime) < GameConstants.RATE )
					{
						sleep();
					}
				}			
				sleep();
			}		
		}
		
		/**
		 * Pauses the thread for 1 ms to not put as much stress on the processor 
		 */
		public void sleep()
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				stop();
			}
		}
		
		/**
		 * Will stop and terminate this thread
		 */
		public void stop()
		{
			running = false;
		}
	}

		
}
