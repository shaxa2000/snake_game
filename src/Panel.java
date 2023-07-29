
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    Image apple;
    Image snakeSize;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean up = false;
    private boolean right = true;
    private boolean down = false;
    private boolean inGame = true;
    Timer t;

    public Panel(){
        setBackground(Color.black);
        imageSet();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }



    private void imageSet(){
        ImageIcon appl = new ImageIcon(Objects.requireNonNull(MainWindow.class.getResource("apple.png")));
        ImageIcon dott = new ImageIcon(Objects.requireNonNull(MainWindow.class.getResource("dot.png")));
        apple = appl.getImage();
        snakeSize = dott.getImage();

    }

    public void initGame(){
        t = null;
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame){
            g.drawImage(apple,appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(snakeSize,x[i],y[i],this);
            }
        }else{
            String end = "If you are not Shaxa, than you are CHMOOO!";
            g.setColor(Color.WHITE);
            g.drawString(end,100,SIZE/2);
            t = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    inGame = true;
                }
            });
            t.start();
            right = true;
            left = false;
            down = false;
            up = false;
            dots = 3;
            Arrays.fill(x,0);
            Arrays.fill(y,0);
            timer = null;
            t = null;

        }
    }


    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }


    public void checkCollisions(){
        for (int i = dots; i >0 ; i--) {
            if (i >=4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }

        }
        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key  = e.getKeyCode();
            if(key == KeyEvent.VK_A && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_D && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_W && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_S && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}
