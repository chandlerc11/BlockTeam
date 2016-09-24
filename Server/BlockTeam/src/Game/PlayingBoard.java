package Game;

import java.util.*;
import java.util.Map.Entry;

import Game.GameConstants.GameMoves;
import Game.Polyominoes.TetrisBlock;



public class PlayingBoard 
{
	private int width;
	private int height;
	private Random rand;
	private TetrisGame game; 
	private Polyominoes current;
	private boolean equilibrium;
	private Polyominoes.TetrisBlock [][] tetrisBoard;
	Queue<GameMoves> moveQueue;
	
	
	public PlayingBoard(int width, int height, TetrisGame game)
	{
		this.width = width;
		this.height = height;
		rand = new Random();
		this.game = game;
		tetrisBoard  =  new Polyominoes.TetrisBlock[width][height];
		equilibrium = true;
		moveQueue = new LinkedList<GameMoves>();
		start();
	}
	
	public void start()
	{
		Thread T = new Thread(new MoveHandler(this, moveQueue));
		T.start();
	}
	
	public void printScreen()
	{
		game.sendDataToGamers();
	}
	
	public synchronized void addMoveToQueue(GameMoves move)
	{
		moveQueue.add(move);
	}
	
	private class MoveHandler implements Runnable
	{
		private PlayingBoard playingBoard;
		private Queue<GameMoves> moveQueue;
		private volatile boolean running;
		
		public MoveHandler(PlayingBoard playingBoard, Queue<GameMoves> moveQueue)
		{
			this.playingBoard = playingBoard;
			this.moveQueue = moveQueue;
			running = true;
		}

		@Override
		public void run() 
		{
			while(running)
			{
				if(!moveQueue.isEmpty())
				{
					GameMoves move = moveQueue.poll();
					if(move == GameMoves.UPDATE)
					{
						playingBoard.update();
						playingBoard.printScreen();
					}
					else if(playingBoard.gamerMove(move))
					{
						playingBoard.printScreen();
					}
				}		
				sleep();
			}			
		}
		
		private void sleep()
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void stop()
		{
			running  = false;
		}
	}
	
	public void createNewPiece()
	{
		current = Polyominoes.createNewPiece(tetrisBoard);
		if(current == null)
		{
			game.gameOver();
		}
	}
	
	public void update()
	{	
		if(current == null)
		{
			createNewPiece();
			return;
		}
		//Move piece down		
		if(!current.action(GameConstants.GameMoves.DOWN))
		{
			equilibrium = false;
			//while !Equilibrium
			while(!equilibrium)
			{
				//check for lines to delete and delete them
				findAndDeleteLines();
				if(equilibrium)
				{
					break;
				}
				//After deleting lines, move all pieces down
				moveAllBlocksDown();
			}
			//create a new piece
			createNewPiece();
		}	
	}
	
	public boolean gamerMove(GameMoves move)
	{
		return current.action(move);
	}
	
	private void moveAllBlocksDown() 
	{
		while(true)
		{
			//Mark Blocks that can be moved down
			Stack<Polyominoes> toDown = markPiecesForDown();
			//If no pieces do not loop, 
			if(toDown.isEmpty())
			{
				return;
			}
			
			//move all pieces marked down 
			while(!toDown.empty())
			{
				toDown.pop().action(GameConstants.GameMoves.DOWN);
			}
			//loop back to top
		}		
	}
	
	private Stack<Polyominoes> markPiecesForDown()
	{
		ArrayList<TetrisBlock> blockList = getAllBlocksOnBoard();
		Iterator<Polyominoes> polyIter = blocksToPolyominoes(blockList.iterator()).iterator();
		Stack<Polyominoes> markedDown = new Stack<Polyominoes>();
		
		while(polyIter.hasNext())
		{
			Polyominoes poly = polyIter.next();
			if(poly.canGoDown())
			{
				markedDown.push(poly);
			}
		}
		return markedDown;
	}
	
	private ArrayList<Polyominoes> blocksToPolyominoes(Iterator<TetrisBlock> iter)
	{
		ArrayList<Polyominoes> poly = new ArrayList<Polyominoes>();
		while(iter.hasNext())
		{
			Polyominoes polyominoe = iter.next().getParentPolyominoe();
			if(!poly.contains(polyominoe))
			{
				poly.add(polyominoe);
			}
		}
		return poly;
	}

	private void findAndDeleteLines() 
	{		
		Iterator<Polyominoes.TetrisBlock> iter = getAllBlocksOnBoard().iterator();
		
		HashMap<Integer, Stack<Polyominoes.TetrisBlock>> linesOnBoard = getBlocksPerLine(iter);
		
		Iterator<Entry<Integer, Stack<TetrisBlock>>> it = linesOnBoard.entrySet().iterator();

		equilibrium = true;
		
		while (it.hasNext()) {
	        Stack<TetrisBlock> line = it.next().getValue();
	        if(line.size() == width)
	        {
	    		equilibrium = false;
	    		removeLine(line);
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }		
	}
	
	private void removeLine(Stack<TetrisBlock> line)
	{
		while(!line.empty())
		{
			line.pop().delete();
		}
	}
	
	private HashMap<Integer, Stack<TetrisBlock>> getBlocksPerLine(Iterator<Polyominoes.TetrisBlock> iter)
	{
		HashMap<Integer, Stack<Polyominoes.TetrisBlock>> linesOnBoard = new HashMap<Integer, Stack<Polyominoes.TetrisBlock>>();
		
		while(iter.hasNext())
		{
			Polyominoes.TetrisBlock block = iter.next();
			int blockHeight = block.getHeight();
			
			if(!linesOnBoard.containsKey(blockHeight))
			{
				Stack<TetrisBlock> line = new Stack<TetrisBlock>();
				linesOnBoard.put(blockHeight, line);
			}
			linesOnBoard.get(blockHeight).push(block);
		}
		return linesOnBoard;
	}
	
	public ArrayList<Polyominoes.TetrisBlock> getAllBlocksOnBoard()
	{
		ArrayList<Polyominoes.TetrisBlock> list = new ArrayList<Polyominoes.TetrisBlock>();
		for(int indexWidth = 0; indexWidth < width; indexWidth++)
		{
			for(int indexHeight = 0; indexHeight < height; indexHeight++)
			{
				Polyominoes.TetrisBlock block = tetrisBoard[indexWidth][indexHeight];
				if(block != null)
				{
					//Updates if the block is able to move down or not
					block.update();
					list.add(block);
				}
			}
		}		
		return list;
	}

	public String currentBoard() 
	{
		StringBuilder SB = new StringBuilder();
		
		Iterator<Polyominoes.TetrisBlock> iter = getAllBlocksOnBoard().iterator();
		
		while(iter.hasNext())
		{
			SB.append(iter.next().data());
			SB.append(";");
		}
			
		return SB.toString();
	}

	public void restart() 
	{
		tetrisBoard = new Polyominoes.TetrisBlock[width][height];
		current = null;
	}
}
