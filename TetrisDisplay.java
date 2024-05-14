import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel {
    TetrisGame game;
    private int start_x = 40;
    private int start_y = 30;
    private int cell_size = 10;
    private int speed = 100;
    private boolean pause;

    private Timer timer;
    private Color[] colors = {Color.black,Color.CYAN, Color.YELLOW, Color.GREEN, Color.MAGENTA,
                                Color.blue, Color.RED, Color.orange};

    public TetrisDisplay(TetrisGame game) {
        this.game = game;
        pause = false;
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                translateKey(e);
            }
        });
        setFocusable(true);

        timer = new Timer(speed, new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                cycleMove();
                repaint();
            }
        });
       timer.start();


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font myFont = new Font ("Arial", 1, 17);
        drawWell(g);
        drawBrick(g);
        drawBackground(g);
        g.setFont(myFont);
        g.setColor(Color.red);
        g.drawString("Score: "+ game.getScore(), 0, cell_size*2);
        myFont = new Font ("Arial", 1, 20);
        g.setFont(myFont);
        g.setColor(Color.red);
        if(game.getState() == 0){
            g.drawString("Game Over", cell_size*5, cell_size *15);
        }
    }
    public void drawWell(Graphics g){
        //left well
        g.fillRect(start_x - cell_size, start_y , cell_size, cell_size * game.getRows());
        //Right well
        g.fillRect(start_x + cell_size * game.getCols(), start_y , cell_size,
                    cell_size * game.getRows());
        //Butom well
        g.fillRect(start_x - cell_size, start_y - cell_size + cell_size * game.getRows() + cell_size,
                cell_size * game.getCols() + 2 * cell_size, cell_size);

        g.setColor(Color.lightGray);
        g.fillRect(start_x, start_y, cell_size * game.getCols(), cell_size*game.getRows());
    }
    private void drawBackground(Graphics g){
        for (int row = 0; row < game.getRows(); row++) {
            for (int col = 0; col < game.getCols(); col++) {
                if (game.fetchBoardPosition(row, col) != 0) {
                    g.setColor(colors[game.fetchBoardPosition(row, col)]);
                    g.fillRect(start_x+col*cell_size,start_y+row*cell_size, cell_size, cell_size);
                    g.setColor(colors[0]);
                    g.drawRect(start_x+col*cell_size, start_y+row*cell_size, cell_size, cell_size);
                }
            }
        }
    }
    public void drawBrick(Graphics g){

        for(int seg = 0; seg < game.getNumSegs(); seg++){
            g.setColor(colors[game.getFallingBrickColor()]);
            g.fillRect(start_x+game.getSegCols(seg)*cell_size, start_y+game.getSegRows(seg)*cell_size, cell_size, cell_size);
            g.setColor(colors[0]);
            g.drawRect(start_x+game.getSegCols(seg)*cell_size, start_y+game.getSegRows(seg)*cell_size, cell_size, cell_size);

        }
    }
    private void translateKey(KeyEvent e){
        int key_Code = e.getKeyCode();
        switch (key_Code){
            case KeyEvent.VK_UP:
                game.makeMove('S');
                break;
            case KeyEvent.VK_RIGHT:
                game.makeMove('R');
                break;
            case KeyEvent.VK_LEFT:
                game.makeMove('L');
                break;
            case KeyEvent.VK_N:
                game.newGame();
                break;
            case KeyEvent.VK_SPACE:
                if(pause == false ){
                    timer.stop();
                    pause = true;
                } else if (pause ) {
                    timer.start();
                    pause = false;
                }

        }
    }
    private void cycleMove(){
        game.makeMove('D');
    }
}
