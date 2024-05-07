package com.bubblebobble.models;

import java.util.ArrayList;

import com.bubblebobble.*;
import com.bubblebobble.views.*;

public class GameModel {
    private PlayerModel player;
    private ArrayList<PlatformModel> platforms;

    public GameModel(PlayerModel player, ArrayList<PlatformModel> platforms) {
        this.player = player;
        this.platforms = platforms;
    }

    public PlayerModel getPlayer() {
        return this.player;
    }

    public ArrayList<PlatformModel> getPlatforms() {
        return platforms;
    }
}
