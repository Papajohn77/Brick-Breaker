package game;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {

    private int map[][];

    // Each brick's width.
    private int brickWidth;
    // Each brick's height.
    private int brickHeight;

    // The number of the rows that we 
    private int showedRowsLength;

    public MapGenerator(int row, int col) {
	map = new int[row][col];

	/* We initialize only half of the rows, to create space to shift the rows
	   down, in order to be able to repair the map.*/
	showedRowsLength = (map.length / 2) - 1;

	for (int i = 0; i < showedRowsLength; i++) {
            for (int j = 0; j < map[0].length; j++) {
		// When the value of a map element is one, the brick isn't broken yet.
		map[i][j] = 1;
            }
	}

	/* We divide the pixels that we have available for the bricks with the number of 
           bricks that the map contains.*/
	brickWidth = 540/col;
	brickHeight = 350/row;
    }

    public void setBrickValue(int value, int row, int col) {
	map[row][col] = value;
    }

    public int getBrickValue(int row, int col) {
	return map[row][col];
    }

    public int getRealRowLength() {
	return map.length;
    }

    public int getShowedRowsLength() {
	return showedRowsLength;
    }

    public int getColLength() {
	return map[0].length;
    }

    public int getBrickWidth() {
	return brickWidth;
    }

    public int getBrickHeight() {
	return brickHeight;
    }

    /* Draws the bricks of the map that haven't broke yet.
       A not broken brick has value one. */
    public void draw(Graphics2D g) {
	for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
		if (map[i][j] == 1) {
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight,
						brickWidth, brickHeight);

                    // That represents the thickness of the outline that the rectangles will have.
                    g.setStroke(new BasicStroke(3));
                    /* Draws for each of the white filled rectangles that are created above 
                       its outline with black color. */
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight,
                                                brickWidth, brickHeight);
		}
            }
	}
    }
}
