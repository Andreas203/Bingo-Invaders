

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable, Commons {

    private Dimension d;
    private ArrayList<Alien> aliens;
    public ArrayList<Integer> playerCard = new ArrayList<>();
    public ArrayList<Integer> deadAliens = new ArrayList<>();
    private Player player;
    private Shot shot;

    private final int ALIEN_INIT_X = 150;
    private final int ALIEN_INIT_Y = 5;
    private int direction = -1;
    private int deaths = 0;

    private boolean ingame = true;
    private final String explImg = "images/explosion.png";
    private String message = "Game Over";

    private Thread animator;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.yellow);
        System.out.println("you reach");


        setDoubleBuffered(true);
    }

    @Override
    public void addNotify() {

        super.addNotify();
        gameInit();
    }

    public void gameInit() {

        aliens = new ArrayList<>();
        Integer[] bingoNumbers = new Integer[75];

        for (int i = 0; i < bingoNumbers.length; i++) {
          bingoNumbers[i] = i+1;
        }

        ArrayList<Integer> l = new ArrayList<Integer>(Arrays.asList(bingoNumbers));
        ArrayList<Integer> l2 = new ArrayList<Integer>(Arrays.asList(bingoNumbers));
        //List<Integer> l = Arrays.asList(bingoNumbers);
        Collections.shuffle(l);
        Collections.shuffle(l2);
        for (int i = 0; i<25; i++) {
          playerCard.add(l2.get(i));
        }
        for (int i = 0; i<playerCard.size(); i++) {
          System.out.print(playerCard.get(i));
        }
        System.out.println(l);
        System.out.println("yeaboi");


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 15; j++) {
                Integer label = l.get(0);
                System.out.println(label);
                Alien alien = new Alien(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i, label);
                l.remove(0);
                aliens.add(alien);
            }
        }

        player = new Player();
        shot = new Shot();

        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {

        Iterator it = aliens.iterator();

        for (Alien alien: aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            ingame = false;
        }
    }

    public void drawShot(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    public void drawBombing(Graphics g) {

        for (Alien a : aliens) {

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawLine(0, 300, BOARD_WIDTH, 300);
            String numberToString = "";
            int y = 0;
            int x = 0;
            for (int i = 0; i<playerCard.size(); i ++) {

              if (i<5) {
                y = 350;
              }
              else if (i>=5 && i<10) {
                y = 375;
              }
              else if (i>=10 && i<15) {
                y = 400;
              }
              else if (i>=15 && i<20) {
                y = 425;
              }
              else if (i>=20 && i<25) {
                y = 450;
              }
              if (i == 0 || i == 5 || i == 10 || i == 15 || i == 20) {
                x = 50;
              }
              else if (i == 1 || i == 6 || i == 11 || i == 16 || i == 21){
                x = 100;
              }
              else if (i == 2 || i == 7 || i == 12 || i == 17 || i == 22){
                x = 150;
              }
              else if (i == 3 || i == 8 || i == 13 || i == 18 || i == 23){
                x = 200;
              }
              else if (i == 4 || i == 9 || i == 14 || i == 19 || i == 24){
                x = 250;
              }

              if (deadAliens.contains(playerCard.get(i))) {
                g.setColor(Color.red);
                g.drawString(String.valueOf(playerCard.get(i)),x+120, y);
              }
              else {
                g.setColor(Color.green);
                g.drawString(String.valueOf(playerCard.get(i)),x+120, y);
              }
              //numberToString = numberToString + ",\n " + String.valueOf(playerCard.get(i));
              //if (i == 4 || i == 9 || i == 14 || i == 19 || i == 24) {
              //  numberToString = numberToString + "\n";
              //}
            }
            g.drawString(numberToString, 0, 350);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);


    }

    public void animationCycle() {
        int counter = 0;
        for (int i = 0; i < playerCard.size(); i ++) {
          if (deadAliens.contains(playerCard.get(i))) {
            counter ++;
          }
        }
        if (counter == 25) {
          ingame = false;
          message = "Game Won!";
        }
        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {

            ingame = false;
            message = "Game won!";
        }

        // player
        player.act();

        // shot
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien: aliens) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + ALIEN_HEIGHT)) {
                        ImageIcon ii
                                = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        int alienNumber = alien.getNumber();
                        deadAliens.add(alienNumber);
                        for (int i = 0; i<deadAliens.size(); i ++) {
                          System.out.println(deadAliens.get(i));
                        }
                        //System.out.println(alien.getNumber());
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens

        for (Alien alien: aliens) {

            int x = alien.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {

                direction = -1;
                Iterator i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {

                direction = 1;

                Iterator i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Alien a = (Alien) i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }

        Iterator it = aliens.iterator();

        while (it.hasNext()) {

            Alien alien = (Alien) it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    ingame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        // bombs
        Random generator = new Random();

        for (Alien alien: aliens) {

            int shot = generator.nextInt(15);
            Alien.Bomb b = alien.getBomb();

            if (shot == CHANCE && alien.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(alien.getX());
                b.setY(alien.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + PLAYER_HEIGHT)) {
                    ImageIcon ii
                            = new ImageIcon(explImg);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    b.setDestroyed(true);
                }
            }

            if (!b.isDestroyed()) {

                b.setY(b.getY() + 1);

                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {

            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }

        gameOver();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (ingame) {
                    if (!shot.isVisible()) {
                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }
}
