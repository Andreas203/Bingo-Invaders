import java.awt.EventQueue;
import javax.swing.JFrame;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;
import javax.sound.sampled.*;

import java.io.IOException;

public class SpaceInvaders extends JFrame implements Commons {

    public SpaceInvaders() {
      try {
      Clip clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(
        SpaceInvaders.class.getResourceAsStream("images/roar.wav"));
      clip.open(inputStream);
      clip.loop(300);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

        initUI();
    }

    private void initUI() {

      try
        {

          String imagePath = "images/logo2.png";
          InputStream imgStream = SpaceInvaders.class.getResourceAsStream(imagePath );
          BufferedImage myImg = ImageIO.read(imgStream);
          setIconImage(myImg);
        } catch (IOException exception)
        {
            exception.printStackTrace();
        }
        add(new Board());
        setTitle("Bingo Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {


        EventQueue.invokeLater(() -> {
            SpaceInvaders ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}
