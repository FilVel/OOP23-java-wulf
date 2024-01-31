package javawulf.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * CommandListener is a class whose purpose is to communicate
 * to the controller when a key related to the game has been pressed
 * or not.
 */
public class CommandListener implements KeyListener {

    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    private boolean attack = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
            System.out.println("Pressed UP");
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
            System.out.println("Pressed DOWN");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
            System.out.println("Pressed LEFT");
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
            System.out.println("Pressed RIGHT");
        }
        if (e.getKeyCode() == KeyEvent.VK_COMMA) {
            attack = true;
            System.out.println("Pressed ATTACK");
        }
        communicateToController();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
            System.out.println("Released UP");
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
            System.out.println("Released DOWN");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
            System.out.println("Released LEFT");
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
            System.out.println("Released RIGHT");
        }
        if (e.getKeyCode() == KeyEvent.VK_COMMA) {
            attack = false;
            System.out.println("Released ATTACK");
        }
        communicateToController();
    }

    private void communicateToController() {
        //controller.update(up,down,left,right,attack);
    }

}