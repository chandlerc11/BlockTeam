package Game;

import java.util.*;
import Game.GameConstants.*;
import Game.Polyominoes.TetrisBlock;
import GamePieces.*;

public abstract class Polyominoes 
{
	protected TetrisBlock[][] board;
	protected ArrayList<TetrisBlock> blocks;
	protected PolyominoeType type;
	protected boolean isFirstDraw;
	protected HashMap<GameMoves, Boolean> canAction;
	
	public static Polyominoes createNewPiece(TetrisBlock[][]board)
	{
		Random rand = new Random();
		Polyominoes check;
		
		switch(GameConstants.PolyominoeType.values()[rand.nextInt(GameConstants.NUMPIECES)])
		{
			case ITYPE: check = new IType(board);  
				break;
			case JTYPE: check = new JType(board);
				break;
			case LTYPE: check = new LType(board);
				break;
			case OTYPE: check = new OType(board);
				break;
			case STYPE: check = new SType(board);
				break;
			case TTYPE: check = new TType(board);
				break;
			case ZTYPE: check = new ZType(board);
				break;
			default: return null;
		}
		
		if(check.createPiece())
		{
			check.drawPoly();
			return check;
		}
		
		return null;
	}
	
	public Polyominoes(PolyominoeType type, TetrisBlock[][] board)
	{
		this.board = board;
		this.type = type;
		isFirstDraw = true;
		canAction = new HashMap<GameMoves, Boolean>();
		canAction.put(GameMoves.LEFT, false);
		canAction.put(GameMoves.RIGHT, false);
		canAction.put(GameMoves.DOWN, false);
	}

	protected  Integer[] rotateBlock(TetrisBlock toRotate, int rotateWidth, int rotateHeight) 
	{
		// TODO Auto-generated method stub
		return null;	
	}
	
	public boolean action(GameMoves move) 
	{
		switch(move)
		{
			case LEFT: 		return left();
			case RIGHT: 	return right();
			case DOWN: 		return down();
			case ALLDOWN: 	return allDown();
			case ROTATE:	return rotate();
		}
		return false;
	}
	
	protected boolean createPiece()
	{
		return true;
	}
	
	protected void drawPoly()
	{
		Iterator<TetrisBlock> iterDelete = blocks.iterator();
		while(iterDelete.hasNext())
		{
			TetrisBlock block = iterDelete.next();
			if(!isFirstDraw)
			{
				board[block.getOldWidth()][block.getOldHeight()] = null;				
			}
			else
			{
				isFirstDraw = false;
			}
		}
		
		Iterator<TetrisBlock> iterAdd = blocks.iterator();
		while(iterAdd.hasNext())
		{
			TetrisBlock block = iterAdd.next();
			board[block.getWidth()][block.getHeight()] = block;
		}
	}

	
	protected boolean rotate() 
	{
		TetrisBlock rotationBlock = blocks.get(3);
		int rotateWidth = rotationBlock.getWidth();
		int rotateHeight = rotationBlock.getHeight();
		ArrayList<Integer[]> rotatePositions = new ArrayList<Integer[]>();
		
		Iterator<TetrisBlock> iter = blocks.iterator();
		
		while(iter.hasNext())
		{
			
			TetrisBlock toRotate = iter.next();
			rotatePositions.add(rotateBlock(toRotate, rotateWidth, rotateHeight));
		}
		
		if(checkPositions(rotatePositions))
		{
			for(int index = 0; index < blocks.size(); index++)
			{
				Integer[] positions = rotatePositions.get(index);
				blocks.get(index).reDraw(positions[0], positions[1]);
			}
			drawPoly();
			return true;
		}
		
		return false;
	}

	protected boolean allDown()
	{
		while(down());
		return true;
	}

	protected boolean down()
	{
		update();
		if(canAction.get(GameMoves.DOWN))
		{
			Iterator<TetrisBlock> iter = blocks.iterator();
			while(iter.hasNext())
			{
				TetrisBlock block = iter.next();
				block.reDraw(block.getWidth(), block.getHeight() - 1);
			}
			drawPoly();
			return true;
		}
		return false;
	}
	
	protected boolean checkPositions(ArrayList<Integer[]> rotatePositions) {
		// TODO Auto-generated method stub
		return false;
	}

	protected boolean right()
	{
		update();
		if(canAction.get(GameMoves.RIGHT))
		{
			Iterator<TetrisBlock> iter = blocks.iterator();
			while(iter.hasNext())
			{
				TetrisBlock block = iter.next();
				block.reDraw(block.getWidth() + 1, block.getHeight());
			}
			drawPoly();
			return true;
		}
		return false;
	}

	protected boolean left()
	{
		update();
		if(canAction.get(GameMoves.LEFT))
		{
			Iterator<TetrisBlock> iter = blocks.iterator();
			while(iter.hasNext())
			{
				TetrisBlock block = iter.next();
				block.reDraw(block.getWidth() - 1, block.getHeight());
			}
			drawPoly();
			return true;
		}
		return false;
	}
	
	public void deleteBlock(TetrisBlock block)
	{
		board[block.getWidth()][block.getHeight()] = null;
		blocks.remove(block);
	}
	
	protected void createNewPieceFromArray(int[][] startArray, TetrisBlockColor color, Polyominoes poly)
	{
		blocks = new ArrayList<TetrisBlock>();
		
		for(int index = 0; index < startArray.length; index++)
		{
			blocks.add(new TetrisBlock(startArray[index][0], startArray[index][1], color, poly));
		}
	}


	protected boolean checkIfGameOver() 
	{
		for(int index = 0; index < blocks.size(); index++)
		{
			if(!checkIfBlockNotTaken(blocks.get(index)))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean checkIfBlockNotTaken(TetrisBlock block)
	{
		return board[block.getWidth()][block.getHeight()] == null;
	}

	public void update() 
	{
		canAction.clear();
		canAction.put(GameMoves.DOWN, canGoDown());
		canAction.put(GameMoves.LEFT, canGoLeft());
		canAction.put(GameMoves.RIGHT, canGoRight());
	}
	
	private boolean canAction(int indexW, int indexH)
	{
		int width = 0;
		int height = 0;
		for(int index = 0; index < blocks.size(); index++)
		{
			TetrisBlock currentBlock = blocks.get(index);
			width = currentBlock.getOldWidth() + indexW;
			height = currentBlock.getHeight() + indexH;
			
			if(width < 0 || width > GameConstants.WIDTH - 1 || height < 0)
			{
				return false;
			}
			TetrisBlock block = board[width][height];
				
			if(block != null && !block.getParentPolyominoe().equals(currentBlock.getParentPolyominoe()))
			{
				return false;
			}
		}
		return true;
	}
	
	private Boolean canGoRight() 
	{
		return canAction(1, 0);
	}

	private Boolean canGoLeft() {
		return canAction(-1, 0);
	}

	public boolean canGoDown()
	{
		return canAction(0, -1);		
	}
	
	public static class TetrisBlock
	{
		private int indexWidth, indexHeight, oldIndexWidth, oldIndexHeight;
		private TetrisBlockColor color;
		private Polyominoes parent;
		public TetrisBlock(int indexWidth, int indexHeight, TetrisBlockColor color, Polyominoes parent)
		{
			this.indexWidth = indexWidth;
			this.indexHeight = indexHeight;
			this.color = color;
			this.parent = parent;
			oldIndexHeight = 23;
			oldIndexWidth = 5;
		}
		
		public String data() 
		{
			StringBuilder SB = new StringBuilder();
			SB.append("{");
			SB.append(indexWidth);
			SB.append(",");
			SB.append(indexHeight);
			SB.append("}:");
			SB.append(color.toString());
			return SB.toString();
		}
		
		public void reDraw(int newIndexWidth, int newIndexHeight)
		{
			oldIndexWidth = indexWidth;
			oldIndexHeight = indexHeight;
			indexWidth = Math.max(newIndexWidth, 0);
			indexWidth = Math.min(GameConstants.WIDTH - 1, indexWidth);
			indexHeight = Math.max(newIndexHeight, 0);
			indexHeight = Math.min(GameConstants.HEIGHT - 1, indexHeight);
		}
		
		public int getOldHeight()
		{
			return oldIndexHeight;
		}
		
		public int getOldWidth()
		{
			return oldIndexWidth;
		}

		public int getHeight() 
		{
			return indexHeight;
		}
		
		public int getWidth()
		{
			return indexWidth;
		}

		public void delete() 
		{
			parent.deleteBlock(this);			
		}

		public void update() 
		{
			parent.update();			
		}

		public Polyominoes getParentPolyominoe() 
		{
			return parent;
		}
		
	}
}
