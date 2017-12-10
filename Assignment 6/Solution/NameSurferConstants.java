import java.awt.Color;

/*
 * File: NameSurferConstants.java
 * ------------------------------
 * This file declares several constants that are shared by the
 * different modules in the NameSurfer application.  Any class
 * that implements this interface can use these constants.
 */

public interface NameSurferConstants {

/** The width of the application window */
	public static final int APPLICATION_WIDTH = 800;

/** The height of the application window */
	public static final int APPLICATION_HEIGHT = 600;

/** The name of the file containing the data */
	public static final String NAMES_DATA_FILE = "names-data.txt";

/** The first decade in the database */
	public static final int START_DECADE = 1900;

/** The number of decades */
	public static final int NDECADES = 11;

/** The maximum rank in the database */
	public static final int MAX_RANK = 1000;

/** The number of pixels to reserve at the top and bottom */
	public static final int GRAPH_MARGIN_SIZE = 20;
	
	//Offset of years GLabels from NORTH and WEST 
	public static final int OFF = 5;
	//Difference between years
	public static final int DIF = 10;
	//Size of JTextField
	public static final int SIZE = 10;
	//Minimum rank
	public static final int MIN_RANK = 0;
	//Colors
	public static final Color COL_1 = Color.BLACK;
	public static final Color COL_2 = Color.RED;
	public static final Color COL_3 = Color.BLUE;
	public static final Color COL_4 = Color.YELLOW;
}
