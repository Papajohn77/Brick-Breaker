package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Bar implements KeyListener {

    // Horizontal position of the bar.
    private int barX = 310;

    // Vertical position of the bar, its constant because the bar its only moving horizontally.
    public static final int BAR_Y = 550;

    public int getBarX() {
	return barX;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

	if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (barX >= 600) {
		barX = 600;
            } else {
		moveRight();
            }
	}

	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (barX < 10) {
		barX = 10;
            } else {
		moveLeft();
            }
	}
    }

    public void moveRight() {
	Gameplay.play = true;
	barX += 25;
    }

    public void moveLeft() {
	Gameplay.play = true;
	barX -= 25;
    }
}
