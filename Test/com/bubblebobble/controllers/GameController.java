package com.bubblebobble.controllers;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import com.bubblebobble.views.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameController {
    private GameView game;
    private EnemyModel enemy;
    private PlayerModel player;
    private GameModel model;
    int xPlayer = 0;
    int yPlayer = 0;

    public GameController(int vuoto){}

    public GameController() {
        player = new PlayerModel(Constants.MAX_WIDTH/3, Constants.MAX_HEIGHT * 70 / 100 - Constants.ALL_PLATFORMHEIGHT);
        
        ArrayList<PlatformModel> platforms = new ArrayList<>();
        platforms.add(new PlatformModel(0, 0, Constants.ALL_PLATFORMHEIGHT ,Constants.MAX_WIDTH ));
        platforms.add(new PlatformModel(Constants.MAX_WIDTH-50, 0, Constants.ALL_PLATFORMHEIGHT ,Constants.MAX_WIDTH ));


        platforms.add(new PlatformModel(0, Constants.MAX_HEIGHT*90/100, Constants.MAX_WIDTH/2, Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT*75/100, Constants.MAX_WIDTH/2, Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT*55/100, Constants.MAX_WIDTH/2, Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT*45/100, Constants.MAX_WIDTH/2, Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT*35/100, Constants.MAX_WIDTH/2, Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT*25/100, Constants.MAX_WIDTH/2, Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT*15/100, Constants.MAX_WIDTH/2, Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT*5/100, Constants.MAX_WIDTH/2, Constants.ALL_PLATFORMHEIGHT));

        model = new GameModel(player, platforms);
        game = new GameView(model);
    }
    
    
    /** 
     * @return GameView
     */
    public GameView getGame() {
        return game;
    }

    

    // eseguito ad ogni frame
    // qui dobbiamo sia aggiornare i modelli (es. fare il move) che fare il render della view  
    public void onTick() {
        // qui gestiamo ogni aggiornamento dei nostri modelli
        
        player.move();
        blocchiBordiLeftRight();
        blocchiBordiTopBottom();

        xPlayer = player.getX();
        enemy.setPlayerX(xPlayer);

        yPlayer = player.getY();
        enemy.setPlayerY(yPlayer);

        

        for (PlatformModel platform : model.getPlatforms()) {
            if (player.collidesWith(platform)) {
                player.setJumping(false);
            }
        }
        
    }

    public void blocchiBordiTopBottom(){

        if  (player.getY() + player.getYSpeed() < 0){
            player.setYSpeed(Constants.SPEED);
        }
        
        if (player.getY() + player.getYSpeed() >= Constants.MAX_HEIGHT){
            player.setY(-40);
            player.setYSpeed(-Constants.SPEED);
        }

    }

    public void blocchiBordiLeftRight(){
        if(player.getX() + player.getXSpeed() < 0){
            player.setXSpeed(0);
            player.setX(0);
        }

        if(player.getX() + player.getXSpeed() >= Constants.MAX_WIDTH - 50){
            player.setXSpeed(0);
            player.setX(Constants.MAX_WIDTH - 50);
        }
    }

    public void onKeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            player.setXSpeed(-Constants.SPEED);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(Constants.SPEED);
        } else if (key == KeyEvent.VK_UP) {
            player.salta();
        }
        //new EnemyModel();

    }

    public void onKeyReleased(KeyEvent e) {
        // TODO: evitare numeri magici.
        // usare constants, ma dentro player.
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(0);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            player.setYSpeed(0);
        }
    }
    
}
