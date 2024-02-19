import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Delayed;

public class gameBoard extends JPanel implements ActionListener {
    int height = 400;
    int width = 400;
    int x[] = new int[height * width];
    int y[] = new int[height * width];
    int dots;
    int apple_x = 100;
    int apple_y = 100;
    int size = 10;
    Image apple;
    Image head;
    Image body;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean topDirection = false;
    boolean bottomDirection = false;
    Timer timer;
    int delay = 300;
    int  RAND_POS = 39;
    boolean inGame = true;


    public gameBoard(){
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        loadImages();

        initGame();
    }

    public void initGame(){
        dots = 3;
        for(int i=0;i<dots;i++){
            x[i] = 150 + size*i;
            y[i] = 150;
        }
        timer = new Timer(delay, this);
        timer.start();
    }

    private void loadImages(){
        ImageIcon image_apple = new ImageIcon("src/resources/apple.png");
        apple = image_apple.getImage();

        ImageIcon image_head = new ImageIcon("src/resources/head.png");
        head = image_head.getImage();

        ImageIcon image_body = new ImageIcon("src/resources/dot.png");
        body = image_body.getImage();

    }

    @Override
    public void paintComponent(Graphics graphics){
        if (inGame){
            super.paintComponent(graphics);
            graphics.drawImage(apple, apple_x, apple_y, this);
            for(int i=0;i<dots;i++){
                if(i==0){
                    graphics.drawImage(head, x[0], y[0], this);
                }
                else{
                    graphics.drawImage(body, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(graphics);
        }
    }

    private void move(){
        for(int i=dots-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        if(leftDirection){
            x[0] -= size;
        }
        if(rightDirection){
            x[0] += size;
        }
        if(topDirection){
            y[0] -= size;
        }
        if(bottomDirection){
            y[0] += size;
        }
    }

    private void locateApple () {
        int r = (int) (Math.random()*(RAND_POS));
        apple_x = r*size;

        r = (int) (Math.random()*(RAND_POS));
        apple_y = r*size;
    }

    private void checkApple () {
        if(x[0]== apple_x && y[0] == apple_y){
            dots++;
            locateApple();
        }
    }

    private void checkCollision() {
        if(x[0] < 0 || x[0] >= width || y[0] < 0 || y[0] >= height){
            inGame = false;
        }

        for(int i=size-1; i>=3; i--){
            if(x[0] == x[i] && y[0] == y[i]){
                inGame = false;
                break;
            }
        }
    }

    private void gameOver(Graphics graphics){
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metrics = getFontMetrics(small);
        graphics.setColor(Color.WHITE);
        graphics.setFont(small);
        graphics.drawString(msg, (width-metrics.stringWidth(msg))/2, height/2);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if((key == keyEvent.VK_LEFT) && (!rightDirection)){
                leftDirection = true;
                topDirection = false;
                bottomDirection = false;
            }
            if((key == keyEvent.VK_RIGHT) && (!leftDirection)){
                rightDirection = true;
                topDirection = false;
                bottomDirection = false;
            }
            if((key == keyEvent.VK_UP) && (!bottomDirection)){
                leftDirection = false;
                topDirection = true;
                rightDirection = false;
            }
            if((key == keyEvent.VK_DOWN) && (!topDirection)){
                leftDirection = false;
                rightDirection = false;
                bottomDirection = true;
            }
        }
    }

}
