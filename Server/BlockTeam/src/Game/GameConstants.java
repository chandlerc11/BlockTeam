package Game;

public class GameConstants {
	public enum GameMoves {
	    START, PAUSE, LEFT, RIGHT, DOWN, ALLDOWN, ROTATE, UPDATE
	}
	
	public enum TetrisBlockColor{
		CYAN, BLUE, ORANGE, YELLOW, GREEN, PURPLE, RED
	}
	
	public enum PolyominoeType{
		ITYPE, JTYPE, LTYPE, OTYPE, STYPE, TTYPE, ZTYPE  
	}
	
	public static int[][] ITYPE_START = {{5, 19}, {5, 18}, {5, 17}, {5, 16}};
	public static int[][] JTYPE_START = {{5, 19}, {5, 18}, {5, 17}, {4, 17}};
	public static int[][] LTYPE_START = {{4, 19}, {4, 18}, {4, 17}, {5, 17}};
	public static int[][] OTYPE_START = {{5, 19}, {4, 19}, {5, 18}, {4, 18}};
	public static int[][] STYPE_START = {{4, 19}, {4, 18}, {5, 18}, {5, 17}};
	public static int[][] TTYPE_START = {{4, 18}, {5, 19}, {5, 18}, {6, 18}};
	public static int[][] ZTYPE_START = {{5, 19}, {5, 18}, {4, 18}, {4, 17}};	
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;
	public static final int NUMPIECES = 7;
	public static final long RATE = 1000;
}
