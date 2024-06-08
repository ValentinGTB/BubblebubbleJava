package com.bubblebobble.controllers;

import com.bubblebobble.Constants;
import com.bubblebobble.models.*;
import com.bubblebobble.views.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    private GameView game;
    private PlayerModel player;
    private GameModel model;
    private ScoreModel scoreModel;
    private PowerUpModel pwupModel;
    private PowerUpModel pwupModel2;
    private static final int POWER_UP_SPEED_BOOST = 5; // Aumento della velocità con il power-up
    private boolean isPowerUpActive = false;
    private HashMap<String , PowerUpModel> pwupHash;
    private ArrayList<EnemyModel> enemyArray;
    boolean dead = false;
    int xPlayer = 0;
    int yPlayer = 0;
    int newY = 0;
    int normalSpeed;
    int boostedSpeed;

    public GameController() {
        ArrayList<PlatformModel> platforms = new ArrayList<>();
        ArrayList<WallModel> walls = new ArrayList<>();
        enemyArray = new ArrayList<>();
        pwupHash = new HashMap<>();

        normalSpeed = Constants.SPEED;
        boostedSpeed = Constants.SPEED * 2;

        // muri esterni
        walls.add(new WallModel(0, 0, Constants.ALL_PLATFORMHEIGHT, Constants.MAX_WIDTH));
        walls.add(new WallModel(Constants.MAX_WIDTH - 55, 0, Constants.ALL_PLATFORMHEIGHT, Constants.MAX_WIDTH));

        // piattaforme top e bottom
        platforms.add(new PlatformModel(25, Constants.MAX_HEIGHT * 90 / 100, Constants.MAX_WIDTH,
                Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(25, Constants.MAX_HEIGHT * 0 / 100, Constants.MAX_WIDTH,
                Constants.ALL_PLATFORMHEIGHT));

        // piattaforma
        platforms.add(new PlatformModel(500, Constants.MAX_HEIGHT * 75 / 100, Constants.MAX_WIDTH / 2,
                Constants.ALL_PLATFORMHEIGHT));
        platforms.add(new PlatformModel(100, Constants.MAX_HEIGHT * 75 / 100, 0,
                Constants.ALL_PLATFORMHEIGHT));

        

        // --- Spostare la costruzione della hashmap nel model del powerup e non nel gamecontroller --- 

        pwupModel = new PowerUpModel(500 , 500 , 40 , 40 , null);
        pwupHash.put("velocita" , pwupModel);

        pwupModel2 = new PowerUpModel(100 , 600 , 40 , 40 , null);       
        pwupHash.put("instakill" , pwupModel2);

        PowerUpModel pwupModel3 = new PowerUpModel(700 , 600 , 40 , 40 , null);
        pwupHash.put("superjump" , pwupModel3);

        PowerUpModel pwupModel4 = new PowerUpModel(500 , 700 , 40 , 40 , null);
        pwupHash.put("doppipunti" , pwupModel4);

        PowerUpModel pwupModel5 = new PowerUpModel(500 , 400 , 40 , 40 , null);
        pwupHash.put("killthemall" , pwupModel5);

        PowerUpModel pwupModel6 = new PowerUpModel(700 , 500 , 40 , 40 , null);
        pwupHash.put("freeze" , pwupModel6);

        PowerUpModel pwupModel7 = new PowerUpModel(700 , 700 , 40 , 40 , null);
        pwupHash.put("freezeAndKill" , pwupModel7);

        PowerUpModel pwupModel8 = new PowerUpModel(200 , 700 , 40 , 40 , null);
        pwupHash.put("jumpPoints" , pwupModel8);

        scoreModel = new ScoreModel(pwupHash);

        // Player
        player = new PlayerModel(Constants.MAX_WIDTH / 3,
        Constants.MAX_HEIGHT * 70 / 100 - Constants.ALL_PLATFORMHEIGHT , pwupHash);

        EnemyModel enemy = new EnemyModel(player, walls, platforms , 70 , 680);
        enemyArray.add(enemy);

        EnemyModel enemy2 = new EnemyModel(player, walls, platforms , 90 , 400);
        enemyArray.add(enemy2);

        EnemyModel enemy3 = new EnemyModel(player, walls, platforms , 100 , 300);
        enemyArray.add(enemy3);

        model = new GameModel(player, platforms, enemy, walls , pwupModel);
        game = new GameView(model, scoreModel , pwupHash , enemyArray);
    }

    public GameView getGame() {
        return game;
    }

    public GameModel getGameModel() {
        return model;
    }

    // eseguito ad ogni frame
    // qui dobbiamo sia aggiornare i modelli (es. fare il move) che fare il render
    // della view
    public void onTick() {

        for(EnemyModel enemy : enemyArray)
        {

                // qui gestiamo ogni aggiornamento dei nostri modelli
                player.move();
                if (!enemy.isInBubble()) {
                    enemy.move();
                    newY = enemy.getY();
                } else {
                    // Se il nemico è nella bolla, aggiorna solo la posizione verso l'alto
                    if (newY >= 40 && !enemy.isFruit()) { // Se sto salendo (non sono in cima) e NON sono un frutto
                        newY -= 1;
                        enemy.setY(newY);
                        enemy.collisionDead();
                    } else if (newY <= 40) { // Se sono in cima
                        dead = true; // Evita che arrivando in cima si attivi il CollisioneEnemy che ti farebbe tornare in basso
                    }
                }
    
                checkProjectileCollisions(enemy);
                BlocchiDirezzionali(enemy);
                ControlloSaltoPlatform();
    
                if (!dead || enemy.isFruit()) { // Se non sono morto oppure sono un frutto, riattiva la caduta
                    CollisioneEnemy(enemy); // Fa ricominciare il nemico a cadere
                }

        }
        
        for(PowerUpModel pwupModel : pwupHash.values())
        {
            if (pwupModel.isExpired() && isPowerUpActive) {
                deactivatePowerUp();
            }

            checkPowerUpCollisions(pwupModel);
        }
            
    }

    private void checkPowerUpCollisions(PowerUpModel pwupModel) {
        if (!pwupModel.isActive() && player.getX() < pwupModel.getX() + pwupModel.getWidth() &&
            player.getX() + 40 > pwupModel.getX() &&
            player.getY() < pwupModel.getY() + pwupModel.getHeight() &&
            player.getY() + 40 > pwupModel.getY()) {
            pwupModel.activate();
            activatePowerUp();
            if(pwupModel == pwupHash.get("killthemall")) Constants.killthemall = true;
            if(pwupModel == pwupHash.get("freeze")) Constants.freeze = true;
            if(pwupModel == pwupHash.get("freezeAndKill")) Constants.freezeAndKill = true;
        }
    }

    private void activatePowerUp() {
        isPowerUpActive = true;
    }

    private void deactivatePowerUp() {
        isPowerUpActive = false;
    }

    public void ControlloSaltoPlatform() {
        for (PlatformModel platform : model.getPlatforms()) {
            if (player.collidesWith(platform)) {
                player.setJumping(false);
            }
        }
    }

    public void CollisioneEnemy(EnemyModel enemy) {
        for (PlatformModel platform : model.getPlatforms()) {
            if (enemy.collidesWith(platform)) {
                enemy.setColliding(false);
            } else {
                enemy.setColliding(true);
                enemy.setY(enemy.getY() + 1); // Gravità
            }
        }
    }

    public void BlocchiDirezzionali(EnemyModel enemy) {
        blocchiBordiLeftRight();
        blocchiBordiTopBottom(enemy);
    }

    public void blocchiBordiTopBottom(EnemyModel enemy) {
        if (player.getY() + player.getYSpeed() < 0) {
            player.setYSpeed(Constants.SPEED);
        }
        if (player.getY() + player.getYSpeed() >= Constants.MAX_HEIGHT) {
            player.setY(-40);
            player.setYSpeed(-Constants.SPEED);
        }

        // Blocco Enemy
        if (enemy.getY() + enemy.getEnemySpeed() < 0) {
            enemy.setEnemySpeed(Constants.SPEED);
        }
        if (enemy.getY() + enemy.getEnemySpeed() >= Constants.MAX_HEIGHT) {
            enemy.setY(-50);
            enemy.setEnemySpeed(-Constants.SPEED);
        }
    }

    public void blocchiBordiLeftRight() {
        if (player.getX() + player.getXSpeed() < Constants.WallWidth) {
            System.err.println(Constants.WallWidth + "Wall width");
            player.setXSpeed(0);
            player.setX(Constants.WallWidth);
        }
        if (player.getX() + player.getXSpeed() >= Constants.MAX_WIDTH - (50 + Constants.WallWidth)) {
            player.setXSpeed(0);
            player.setX(Constants.MAX_WIDTH - (53 + Constants.WallWidth));
        }
    }

    private void checkProjectileCollisions(EnemyModel enemy) {
        List<ProjectileModel> projectiles = player.getProjectiles();
        for (ProjectileModel projectile : projectiles) {
            if (projectile.collidesWith(enemy)) {
                // Gestisci la collisione: ingloba il nemico nella bolla
                for(Map.Entry<String, PowerUpModel> entry : pwupHash.entrySet())
                {
                String key = entry.getKey();
                PowerUpModel valModel = (PowerUpModel) entry.getValue();
                
                // Se il player NON ha raccolto il powerup INSTAKILL

                if(key.equals("instakill"))
                {
                    if(!valModel.isActive()) enemy.setInBubble(true);
                    else{enemy.instaKill();}
                }
                

                }

                projectile.deactivate();
            }

            else if(projectile.collidesWithWalls(Constants.WALL_LEFT, Constants.WALL_RIGHT) && projectile.isActive()){
                scoreModel.addPoints(10);
                projectile.deactivate();
            }
        }
    }

    public void onKeyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();

        int currentSpeed = pwupModel.isActive() && !pwupModel.isExpired() ? boostedSpeed : normalSpeed;

        if (key == KeyEvent.VK_LEFT) {
            player.setXSpeed(-currentSpeed);
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(currentSpeed);
        }
        else if (key == KeyEvent.VK_UP) {
            player.salta();
            if(pwupHash.get("jumpPoints").isActive() && isPowerUpActive)
            {
                scoreModel.addPoints(100);
            }

        } else if (key == KeyEvent.VK_SPACE) {
            player.shoot();
        }
    }

    public void onKeyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.setXSpeed(0);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            player.setYSpeed(0);
        }
    }

    public ArrayList<Object> creaArray(PowerUpModel pwupModel, int x , int y , int width ,int height ,Image image)
    {
        ArrayList<Object> pwupArray = new ArrayList<>();
        pwupArray.add(pwupModel);
        pwupArray.add(x);
        pwupArray.add(y);
        pwupArray.add(width);
        pwupArray.add(height);
        pwupArray.add(image);

        return pwupArray;
    }
}
