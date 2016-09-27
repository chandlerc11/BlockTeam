package GamePieces;

import java.util.ArrayList;

import Game.*;
import Game.GameConstants.PolyominoeType;
import Game.GameConstants.TetrisBlockColor;

public class SType extends Polyominoes {

	public SType(TetrisBlock[][] tetrisBoard) {
		// TODO Auto-generated constructor stub
		super(PolyominoeType.STYPE, tetrisBoard);
		createNewPieceFromArray(GameConstants.STYPE_START, TetrisBlockColor.GREEN);
	}

	@Override
	protected boolean rotate() 
	{
		ArrayList<Integer[]> rotatePositions = new ArrayList<Integer[]>();
		TetrisBlock block0 = blocks.get(0);
		TetrisBlock block1 = blocks.get(1);
		TetrisBlock block2 = blocks.get(2);
		TetrisBlock block3 = blocks.get(3);
		
		if(blocks.get(1).getHeight() < blocks.get(0).getHeight())
		{
			rotatePositions.add(new Integer[]{block0.getWidth(), block0.getHeight()});
			rotatePositions.add(new Integer[]{block1.getWidth() + 1, block1.getHeight() + 1});
			rotatePositions.add(new Integer[]{block2.getWidth(), block2.getHeight() + 2});
			rotatePositions.add(new Integer[]{block3.getWidth() + 1, block3.getHeight() + 3});
		}
		else
		{
			rotatePositions.add(new Integer[]{block0.getWidth(), block0.getHeight()});
			rotatePositions.add(new Integer[]{block1.getWidth() - 1, block1.getHeight() - 1});
			rotatePositions.add(new Integer[]{block2.getWidth(), block2.getHeight() - 2});
			rotatePositions.add(new Integer[]{block3.getWidth() - 1, block3.getHeight() - 3});
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
}
