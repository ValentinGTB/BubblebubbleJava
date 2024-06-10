package com.bubblebobble.levels;

import com.bubblebobble.models.GameModel;

// TODO: si potrebbe avere un metodo getInitialPosition che restituisce la posizione iniziale del giocaotre
// utile sia nel caso si cominci un nuovo livello, che quando il player muore (lo si può riposizione nel punto iniziale)

public interface Level {
    public int getLevel();
    public void load(GameModel game);
}
