package flappy;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Flappy implements ActionListener {

    public static Renderer renderer;
    public int ticks, heightJump = 16, yMotion, score = 0, speed;
    public int pauseSpeed = 0;
    public boolean gameOwer, started, flag = true, speedflag = true;
    public static boolean recovery, pause, jumpflag;
    public ArrayList<MarshMallow> columns;
    public Android android;
    public Background background;
    public Sort sort;

    public Flappy(Renderer renderer) {
        Timer timer = new Timer(20, this);
        File f = new File("for save/file.txt");
        f.delete();
        this.renderer = renderer;
        android = new Android();
        background = new Background();
        columns = new ArrayList<>();

        timer.start();
    }

    /**
     * A method that starts the game process,
     * or Droid makes jump
     * called event keyPressed
     *
     * @see Flappy#jump()
     */
    public void jump() {
        if (!started) {
            started = true;
            speed = 10;
            columns.add(new MarshMallow());
        }
        if (gameOwer) {
            android.x = Renderer.WIDTH / 2 - 245;
            columns.clear();
            yMotion = 0;
            score = 0;
            speed = 10;
            columns.add(new MarshMallow());
            gameOwer = false;
            flag = true;
        } else if (!gameOwer && android.y > Renderer.HEIGHT - 146) {
            yMotion = 0;
            yMotion -= heightJump;
        }
    }

    /**
     * method adjusts the speed
     * of movement of the columns
     *
     * @see Flappy#xIncrement()()
     */
    public void xIncrement() {
        if (score == 10 && !gameOwer) {
            speed = 11;
        } else if (score == 20 && !gameOwer) {
            speed = 12;
        } else if (score == 30 && !gameOwer) {
            speed = 13;
        } else if (score % 15 == 0 && score != 0 && !gameOwer && speedflag) {
            speedflag = false;
            Random rand = new Random();
            speed = 10 + rand.nextInt(3);
        }
        if (score > 30 && score % 15 - 1 == 0)
            speedflag = true;

        for (int i = 0; i < columns.size(); i++) {
            MarshMallow column = columns.get(i);
            column.x -= speed;
        }
    }

    /**
     * The method controls the droid jumps
     * without the participation of the player
     *
     * @see Flappy#autojump()
     */
    public void autojump() {
        if (jumpflag) {
            for (MarshMallow column : columns) {
                if (column.x - android.x < 150 && column.x - android.x > 0) {
                    if (!gameOwer && android.y > Renderer.HEIGHT - 146) {
                        yMotion = 0;
                        yMotion -= heightJump;
                    }
                } else if (column.x - android.x < 200 && column.x - android.x > 20 && speed >= 12) {
                    if (!gameOwer && android.y > Renderer.HEIGHT - 146) {
                        yMotion = 0;
                        yMotion -= heightJump;
                    }
                }
            }
        }
    }

    /**
     * Method pauses the game
     *
     * @see Flappy#autojump()()
     */
    public void pause() {
        if (pause && speed != 0) {
            pauseSpeed = speed;
            speed = 0;
        }
        if (!pause && pauseSpeed != 0 && !gameOwer) {
            speed = pauseSpeed;
        }
    }

    /**
     * A method in which
     * lies the game logic
     *
     * @param e Event class object
     * @see Flappy#actionPerformed(ActionEvent)
     */
    @Override public void actionPerformed(ActionEvent e) {
        ticks++;
        if (started) {
            xIncrement();
            autojump();
            pause();
            if (android.y >= Renderer.HEIGHT - 145) {
                android.y = Renderer.HEIGHT - 145;
            }
            if (ticks % 2 == 0 && yMotion < heightJump) {
                yMotion += 2;
            }
            for (int i = 0; i < columns.size(); i++) {
                MarshMallow column = columns.get(i);
                if (column.x < 450 && column.x >= 0 && flag) {
                    flag = false;
                    columns.add(new MarshMallow());
                    score++;
                }
                if (column.x + column.width < 0) {
                    flag = true;
                    columns.remove(column);
                }
            }

            android.y += yMotion;

            for (MarshMallow column : columns) {
                if (column.intersects(android)) {
                    gameOwer = true;
                    speed = 0;
                    File f = new File("for save/file.txt");
                    f.delete();
                    if (android.x <= column.x) {
                        android.x = column.x - android.width;
                    } else {
                        if (column.y != 0) {
                            android.y = column.y - android.width;
                        } else if (android.y < column.height) {
                            android.y = column.height;
                        }
                    }
                }
            }
        }
    }

    /**
     * Method draws a game screen
     *
     * @param g graphics class object
     * @see Flappy#paint(Graphics)
     */
    public void paint(Graphics g) {
        background.paintBackground(g, speed);
        g.setColor(Color.darkGray);
        g.fillRect(0, Renderer.HEIGHT - 40, Renderer.WIDTH, 20);

        android.paintAndroid(g);
        for (MarshMallow column : columns) {
            column.paintMarshMallow(g);

        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));
        if (gameOwer) {
            g.drawString("Game Over", 100, Renderer.HEIGHT / 2 - 50);
        }
        if (!started) {
            g.drawString("Click to start", 75, Renderer.HEIGHT / 2 - 50);
        }
        if (started && !gameOwer) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 20));
            g.drawString("score:", 15, 50);
            g.drawString(String.valueOf(score), 75, 50);
            if (heightJump == 15) {
                g.drawString("hard", 15, 30);
            }
            if (heightJump == 16) {
                g.drawString("medium", 15, 30);
            }
            if (heightJump == 17) {
                g.drawString("easy", 15, 30);
            }
            g.drawString("4 - save", 645, 30);
            g.drawString("enter - pause", 645, 50);
            g.drawString("ctrl - auto play", 645, 70);
        }
        if (gameOwer || !started) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 20));
            g.drawString("1 - easy", 15, 30);
            g.drawString("2 - medium", 15, 50);
            g.drawString("3 - hard", 15, 70);
            g.drawString("5 - recovery", 645, 30);
            g.drawString("6 - sort in scala", 645, 50);
            g.drawString("7 - sort in java", 645, 70);
            g.drawString("8 - statistic", 645, 90);
        }
    }

    /**
     * inner class
     * interface implements MouseListener
     *
     * @link Flappy#ForMouse
     */
    class ForMouse extends MouseAdapter {

        @Override public void mousePressed(MouseEvent e) {
            jump();
        }

    }


    /**
     * inner class
     * interface implements KeyListener
     *
     * @link Flappy#ForKey
     */
    class ForKey extends KeyAdapter {
        @Override public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jump();
            }
        }

        @Override public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                if (!jumpflag) {
                    jumpflag = true;
                } else {
                    jumpflag = false;
                }
            }
            if (!started || gameOwer) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    heightJump = 17;
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    heightJump = 16;
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    heightJump = 15;
                }
                if (e.getKeyCode() == KeyEvent.VK_5) {
                    if (!recovery) {
                        recovery = true;
                        jumpflag = true;
                        Save.recovery();
                    } else {
                        recovery = false;
                        jumpflag = false;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_6) {
                    sort = new Sort();
                    sort.sortInScala();
                }
                if (e.getKeyCode() == KeyEvent.VK_7) {
                    sort = new Sort();
                    sort.sortInJava();
                }
                if (e.getKeyCode() == KeyEvent.VK_8) {
                    sort = new Sort();
                    sort.getStatistic();
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!pause) {
                        pause = true;
                    } else {
                        pause = false;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_4) {
                    Save.saveNewFile(score);
                }

            }
        }
    }
}

