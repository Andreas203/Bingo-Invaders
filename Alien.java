import javax.swing.ImageIcon;

public class Alien extends Sprite {

    private Bomb bomb;
    private Integer number;



    public Alien(int x, int y, Integer n) {
        this.number = n;
        initAlien(x, y, n);
    }

    private void initAlien(int x, int y, Integer n) {

        System.out.println(n);

        this.x = x;
        this.y = y;
        String alienImg = "images/(" + n + ").png";
        ImageIcon ii = new ImageIcon("images/(" + n + ").png");
        setImage(ii.getImage());
        bomb = new Bomb(x, y);




    }

    public Integer getNumber() {
      return number;
    }

    public void act(int direction) {

        this.x += direction;
    }

    public Bomb getBomb() {

        return bomb;
    }

    public class Bomb extends Sprite {

        private final String bombImg = "images/bomb.png";
        private boolean destroyed;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(bombImg);
            setImage(ii.getImage());

        }

        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }
    }
}
