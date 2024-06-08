package com.bubblebobble.models;

import com.bubblebobble.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerModel extends CharacterModel {
	private HashMap<String , PowerUpModel> pwupHash;
	int vita = Constants.VITA;

	private int DISTANCEPG = 40;

	private boolean isJumping;

	// private PlayerView pw = new PlayerView();

	

	public PlayerModel()
	{
		super();
	}

	public PlayerModel(int x, int y, HashMap<String , PowerUpModel> pwupHash) {
		super(x, y);
		this.pwupHash = pwupHash;
	}

    private List<ProjectileModel> projectiles = new ArrayList<>();

    public void shoot() {
        int projectileSpeed = 10; // Imposta la velocità del proiettile
        projectiles.add(new ProjectileModel(getX() + DISTANCEPG, getY() + DISTANCEPG - 80 / 2, projectileSpeed));
    }

    public List<ProjectileModel> getProjectiles() {
        return projectiles;
    }

    public void updateProjectiles() {
        for (ProjectileModel projectile : projectiles) {
            if (projectile.isActive()) {
                projectile.move();
                // Puoi aggiungere la logica per disattivare il proiettile se esce dallo schermo
                // projectile.deactivate() se il proiettile esce dallo schermo
            }
        }
    }

	public void setVita()
	{
		vita -= 1;
		System.out.println("DOPO SETVITA" + vita);
	}

	public int getVita()
	{
		return vita;
	}

	public void salta() {
		if (!isJumping) setJumping(true);
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean jumping) {
		if (jumping) {
			
			for(Map.Entry<String, PowerUpModel> entry : pwupHash.entrySet())
            {
                String key = entry.getKey();
                PowerUpModel valModel = (PowerUpModel) entry.getValue();

				if(key.equals("superjump"))
				{
					
					if(valModel.isActive() && !valModel.isExpired())
					{
						setYSpeed(-20);
						isJumping = true;
					}
					else
					{
						setYSpeed(-15);
						isJumping = true;
					}
				}

			}
			
			
		} else {
			setYSpeed(0);
			isJumping = false;
		}
	}

	public boolean collidesWith(PlatformModel platform) {
		// Controlla se il personaggio è sopra la piattaforma e se la parte inferiore del personaggio è al di sopra del punto superiore della piattaforma
	
		if (getYSpeed() >= 0 
			&& getY() + DISTANCEPG >= platform.getPlatformY() 
			&& getY() <= platform.getPlatformY() + platform.getPlatformHeight()
			&& getX() + DISTANCEPG >= platform.getPlatformX() 
			&& getX() <= platform.getPlatformX() + platform.getPlatformWidth()
			&& getY() + DISTANCEPG <= platform.getPlatformY() + platform.getPlatformHeight()) {
			// La parte inferiore del personaggio è sopra il punto superiore della piattaforma
			// La parte superiore del personaggio è al di sotto della piattaforma
			// Il personaggio sta scendendo
	
			// Regola la posizione del personaggio per farlo rimanere sulla piattaforma
			setY(platform.getPlatformY() - DISTANCEPG);
			setYSpeed(0);; // Imposta la velocità verticale a 0 per fermare il movimento verso il basso
			setJumping(false); // Imposta lo stato del salto a falso
			return true; // Collisione rilevata
		}
	
		return false; // Nessuna collisione rilevata
	}


	// Muove il personaggio
	@Override
	public void move() {
		super.move();
		updateProjectiles();
	}
}