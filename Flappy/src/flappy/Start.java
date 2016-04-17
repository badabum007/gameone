package flappy;

import javax.swing.*;

/**
 * Flappy Droid
 *
 * @author Александр
 * @version 1.0
 */

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Renderer();
            }
        });
    }
}
