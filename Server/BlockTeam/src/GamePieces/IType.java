package GamePieces;

import java.util.*;

import Game.*;
import Game.GameConstants.GameMoves;
import Game.GameConstants.PolyominoeType;
import Game.GameConstants.TetrisBlockColor;

public class IType extends Polyominoes {

	public IType(TetrisBlock[][] tetrisBoard) {
		// TODO Auto-generated constructor stub
		super(PolyominoeType.ITYPE, tetrisBoard);		
	}	

	@Override
	protected boolean createPiece() 
	{
		createNewPieceFromArray(GameConstants.ITYPE_START, TetrisBlockColor.CYAN, this);
		return checkIfGameOver();
	}
}
