package game;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Frame extends JFrame {

    public Frame() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("GameIcon.png"));
        setIconImage(icon.getImage());
        setBounds(10, 10, 700, 600);
	setTitle("Brick Breaker");
	setResizable(false);
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// Creates the Panel and adds it to the Frame.
	Gameplay gameplay = new Gameplay();
	add(gameplay);
    }

    // TODO: remove magic numbers from all classes.
    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}
