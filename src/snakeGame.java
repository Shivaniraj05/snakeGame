import javax.swing.*;

public class snakeGame extends JFrame {

    private gameBoard board;
    public snakeGame(){
        board = new gameBoard();
        add(board);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main (String[] args){
        JFrame snakeGame = new snakeGame();
    }
}