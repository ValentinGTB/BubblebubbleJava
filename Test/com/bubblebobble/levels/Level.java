package com.bubblebobble.levels;

import com.bubblebobble.models.GameModel;

public interface Level {
    public int getLevel();
    public void load(GameModel game);
}
