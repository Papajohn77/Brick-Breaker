package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;

    private MapGenerator map;
    private Ball ball;
    private Bar bar;

    // The current state of the game.
    public static boolean play = false;

    private int score = 0;
    private int totalBricks;

    // javax.swing.Timer
    private Timer timer;
    private int delay = 8;

    public Gameplay() {
	map = new MapGenerator(8, 7);
	ball = new Ball();
	bar = new Bar();

	addKeyListener(bar);
	// To restart the game.
	addKeyListener(this);

	setFocusable(true);
	setFocusTraversalKeysEnabled(false);

	totalBricks = map.getShowedRowsLength() * map.getColLength();

	/*
	 * When we create a javax.swing.Timer, we have to specify an ActionListener 
	 * (Gameplay implements ActionListener, so a Gameplay is an ActionListener) to
	 * be notified when the timer "goes off". The actionPerformed method in
	 * this listener should contain the code for whatever task need to be
	 * performed. 
	 */
	timer = new Timer(delay, this);
	/* Starts the Timer, causing it to start sending action events to its
           listener, it fires an action event every "delay" milliseconds.*/
	timer.start();
    }

    public void paint(Graphics g) {

	// Making the background black.
	g.setColor(Color.black);
	g.fillRect(1, 1, 692, 592);

	// Drawing the map
	map.draw((Graphics2D) g);

	// The borders.
	g.setColor(Color.yellow);
	g.fillRect(0, 0, 3, 592);
	g.fillRect(0, 0, 692, 3);
	g.fillRect(682, 0, 3, 592);

	// The score.
	g.setColor(Color.white);
	g.setFont(new Font("serif", Font.BOLD, 25));
	g.drawString(String.valueOf(score), 640, 30);

	// The bar.
	g.setColor(Color.green);
	g.fillRect(bar.getBarX(), Bar.BAR_Y, 100, 8);

	// The ball.
	g.setColor(Color.yellow);
	g.fillOval(ball.getBallHorizontalPos(), ball.getBallVerticalPos(), 20, 20);

	// If we miss the ball then game over!
	if (ball.getBallVerticalPos() > 570) {

            play = false;

            ball.stopBall();

            // Displayed message when we have lost.
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
	g.dispose();
    }

    // Its called repeatedly by the action events thrown from the timer.
    @Override
    public void actionPerformed(ActionEvent e) {

	// If the game have started.
	if (play) {

            /* When the ball hits the bar, make the ball to start moving to the opposite
               direction vertically.*/
            if (new Rectangle(ball.getBallHorizontalPos(), ball.getBallVerticalPos(), 20, 20)
                            .intersects(new Rectangle(bar.getBarX(), Bar.BAR_Y, 100, 8))) {

		/* We need to make sure that we would have already exit the condition before
                   the next event will be thrown from timer, else the ball will get stuck on
                   the bar.*/
		ball.setBallVerticalPos(-2);

		ball.changeDirVertically();
            }

            /* 
             * Loops through the map and for every brick thats not smashed, it checks if the
             * ball is on the same coordinates as the brick. If it is, we destroy the brick
             * and change the direction in which the ball moves based on the angle that it
             * hitted the brick from.
             */
	A: for (int i = 0; i < map.getRealRowLength(); i++) {
		for (int j = 0; j < map.getColLength(); j++) {
                    if(map.getBrickValue(i, j) == 1) {
			int brickX = j * map.getBrickWidth() + 80;
			int brickY = i * map.getBrickHeight();
			int brickWidth = map.getBrickWidth();
			int brickHeight = map.getBrickHeight();

			// Current brick.
			Rectangle brickRect = new Rectangle(brickX, brickY,
                                                        brickWidth, brickHeight);

			// Current position of the ball as a rectangle.
			Rectangle ballRect = new Rectangle(ball.getBallHorizontalPos(),
							ball.getBallVerticalPos(), 20, 20);

			if (ballRect.intersects(brickRect)) {
                            // Destroy the brick.
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            /* Changes the direction of the ball based on where the brick
                               got hitted by the ball.
			       brickRect.x is the left coordinate of the Rectangle
                               that represents the destroyed brick. */
                            if (ball.getBallHorizontalPos() + 19 <= brickRect.x ||
				ball.getBallHorizontalPos() + 1 >= brickRect.x + brickRect.width) {

				/* If the ball hitted the brick from it sides we want
                                   the ball to start moving to the opposite direction
                                   horizontally. */
				ball.changeDirHorizontally();
                            } else {
				/* If the ball hitted the brick from the top or the bottom
                                   in that case we want the ball to start moving
                                   to the opposite direction vertically. */
				ball.changeDirVertically();
                            }

                            // If the ball hitted any of the bricks break the loops.
                            break A;
			}
                    }
		}
            }

            /* When seven of the bricks are broken we add one layer of bricks on the top and
               shift down by one the current layers of the map. */
            // (map.getShowedRowsLength() * 2) is the real row length minus 2.
            if (totalBricks <= (map.getShowedRowsLength() * map.getColLength()) - 7) {
		for (int i = (map.getShowedRowsLength() * 2); i >= 0; i--) {
                    for (int j = 0; j < map.getColLength(); j++) {
			map.setBrickValue(map.getBrickValue(i, j), i + 1, j);
                    }
		}
		for (int j = 0; j < map.getColLength(); j++) {
                    map.setBrickValue(1, 0, j);
		}
		totalBricks = map.getShowedRowsLength() * map.getColLength();
            }

            ball.moveBall();

            /* If the ball hits the left border make it start moving to the opposite
             *  direction horizontally.
             */
            if (ball.getBallHorizontalPos() <= 0) {
		/* We need to make sure that we will exit the condition,
		 * else the ball might get stuck.*/
		ball.setBallHorizontalPos(2);

		ball.changeDirHorizontally();
            }

            /* If the ball hits the top border make it start moving to the opposite
             *  direction vertically.
             */
            if (ball.getBallVerticalPos() <= 0) {
		/* We need to make sure that we would have already exit the condition before
                   the next event will be thrown from timer, else the ball will get stuck on
                   the left boarder.*/
		ball.setBallVerticalPos(2);

		ball.changeDirVertically();
            }

            /* If the ball hits the right border make it start moving to the opposite
             *  direction horizontally.
             */
            if (ball.getBallHorizontalPos() >= 670) {
		/* We need to make sure that we would have already exit the condition before
                   the next event will be thrown from timer, else the ball will get stuck on
                   the right boarder.*/
		ball.setBallHorizontalPos(-2);

		ball.changeDirHorizontally();
            }
	}

	// Calls the paint method to draw everything again.
	repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    // Restart the game
    @Override
    public void keyPressed(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
		play = true;
		ball.initBallPos();
		ball.initBallDir();
		score = 0;
		totalBricks = map.getShowedRowsLength() * map.getColLength();
		map = new MapGenerator(8, 7);
            }
	}
    }
}
