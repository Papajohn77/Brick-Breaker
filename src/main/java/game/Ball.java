package game;

import java.util.Random;

public class Ball {

    private static Random random = new Random();

    // This is the starting position of the ball.
    private int ballPosX = 120;
    private int ballPosY = 350;

    // Those are going to affect the direction of the ball movement.
    private int ballXdir = - (1 + random.nextInt(2));
    private int ballYdir = - (2 + random.nextInt(1));

    public int getBallVerticalPos() {
	return ballPosY;
    }

    public void setBallVerticalPos(int x) {
	ballPosY += x;
    }

    public int getBallHorizontalPos() {
	return ballPosX;
    }

    public void setBallHorizontalPos(int x) {
	ballPosX += x;
    }

    public void moveBall() {
	ballPosX += ballXdir;
	ballPosY += ballYdir;
    }

    public void initBallPos() {
	ballPosX = 120;
	ballPosY = 350;
    }

    public void initBallDir() {
	// When we restart the game we want the ball to start move again.
	ballXdir = - (1 + random.nextInt(2));
	ballYdir = - (2 + random.nextInt(1));
    }

    public void stopBall() {
	// Stops the movement of the ball when we lose.
	ballXdir = 0;
	ballYdir = 0;
    }

    public void changeDirHorizontally() {
	// We want to start move the ball to the opposite direction horizontally.
	if (ballXdir > 0) {
            ballXdir = - (1 + random.nextInt(2));
	} else {
            ballXdir = (1 + random.nextInt(2));
	}
    }

    public void changeDirVertically() {
	// We want to start move the ball to the opposite direction vertically.
	if (ballYdir > 0) {
            ballYdir = - (2 + random.nextInt(1));
	} else {
            ballYdir = (2 + random.nextInt(1));
	}
    }
}
