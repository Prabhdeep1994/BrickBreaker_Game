package com.prabhdeep_singh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false; // Whenever the game starts, it doesn't start by itself i.e that is why Play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;
    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {

        //Making background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, 692, 592);

        //Drawing Map
        map.draw((Graphics2D)graphics);

        //Making Borders
        graphics.setColor(Color.GREEN);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(682, 0, 3, 592);

        //Scroe
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString(""+score, 590, 30);

        //Paddle
        graphics.setColor(Color.RED);
        graphics.fillRect(playerX, 550, 100, 8);

        //Ball
        graphics.setColor(Color.GREEN);
        graphics.fillRect(ballPositionX, ballPositionY, 20, 20);

        if(totalBricks<=0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("You Won!!!, Score: " +score , 190, 300);

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart... " , 230, 350);

        }

        if(ballPositionY >570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("Game Over, Score: " +score , 190, 300);

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart... " , 230, 350);
        }
        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;
            }

            A:  for(int i=0; i<map.map.length; i++){
                for(int j=0; j<map.map[0].length; j++){
                    if(map.map[i][j]>0){
                        int brickX = j*map.brickWidth+80;
                        int brickY =i*map.brickHeight+50;
                        int brichWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rectangle = new Rectangle(brickX,brickY,brichWidth,brickHeight);
                        Rectangle ballRectangle = new Rectangle(ballPositionX, ballPositionY, 20,20);
                        Rectangle brickRectangle = rectangle;

                        if(ballRectangle.intersects(brickRectangle)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score +=5;

                            if(ballPositionX +19 <= brickRectangle.x ||
                                    ballPositionX +1 >= brickRectangle.x + brickRectangle.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballPositionX += ballXdir;
            ballPositionY += ballYdir;
            if(ballPositionX<0){
                ballXdir = -ballXdir;
            }
            if(ballPositionY<0){
                ballYdir = -ballYdir;
            }
            if(ballPositionX >670){
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPositionY = 120;
                ballPositionY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }
    public void moveRight(){
        play = true;
        playerX +=20;
    }
    public void moveLeft(){
        play = true;
        playerX -= 20;
    }

}

