
/**
 * Questa enum sarà usata quando ci saranno livelli da salvare
 */


import java.awt.Graphics2D;
import java.util.HashMap;

public enum Livelli {

	l1(new HashMap<>()) , l2(new HashMap<>()) , l3(new HashMap<>()) , l4(new HashMap<>()) , l5(new HashMap<>()) , l6(new HashMap<>()) , l7(new HashMap<>()) , l8(new HashMap<>()) , l9(new HashMap<>()) , l10(new HashMap<>()) , l11(new HashMap<>()) , l12(new HashMap<>()) , l13(new HashMap<>()) , l14(new HashMap<>()) , l15(new HashMap<>()) , l16(new HashMap<>());
	
	private final HashMap<String , Object> hashLivello;
	
	Livelli(HashMap<String, Object> hashLivello)
	{
		this.hashLivello = hashLivello;
	}
	
	public HashMap<String, Object> getHashLivello() {
        return hashLivello;
    }
	
	public void putGraphics2DValue(String nome, Graphics2D value) {
        hashLivello.put(nome, value);
    }
	
	public Graphics2D getGraphics2DValue(String key) {
        Object obj = hashLivello.get(key);
        if (obj instanceof Graphics2D) {
            return (Graphics2D) obj;
        }
        return null; // Restituisce null se il valore non è di tipo Graphics2D
    }
	
}


// public void creaTiles(String nome , Graphics2D g, Color colore , int x, int y, int larg , int alt) 
// 	{
		
// 		if(nome.equals("player")) {g.drawImage(drago, x,y,this);}
// 		else {
		
		
// 		g.setColor(colore);
// 		g.fillRect(x, y, larg, alt);
// 		}
// 		Object[] array = new Object[] {g , larg , alt , x , y};

// 		piattaforme.put(nome , array);
		
// 	}


// public void paintComponent(Graphics e) //Capisci cos'è paintComponent
// 	{
// 		super.paintComponent(e);

// 		Graphics2D g = (Graphics2D) e;
// 		//creaTiles("player" , g , Color.white , playerX, playerY, tileSize, tileSize);
// 		g.drawImage(drago , playerX , playerY , null);
		
// 		Graphics2D piattaforma = (Graphics2D) e;
// 		creaTiles("piattaforma0" , piattaforma , Color.green , 0, altezzaTerrenoTile, 724, 50);
		
// 		Graphics2D piattaforma2 = (Graphics2D) e;
// 		creaTiles("piattaforma2" , piattaforma2 , Color.BLACK , 200, 200, 100, 100);
		
// 		Graphics2D piattaforma3 = (Graphics2D) e;
// 		creaTiles("piattaforma3" , piattaforma3 , Color.GREEN , 724 + 10, altezzaTerrenoTile, 100, 100);
		
// 		g.dispose();
		
		
// 	}