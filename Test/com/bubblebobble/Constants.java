package com.bubblebobble;

public class Constants {
    // === Common
    public static final String FONT_NAME = "arcade-legacy.ttf";

    public static String BASE_URL = "Test/com/bubblebobble/views/img/";
    public static String PROFILES_URL = "Test/com/bubblebobble/profiles/";
    
    // === Window
    public static int MAX_WIDTH = 1000;
    public static int MAX_HEIGHT = 800;
    
    // === Player
    public static final int PLAYER_WIDTH = 40;
    public static final int PLAYER_HEIGHT = 40;
    
    public static final int PLAYER_LIVES = 1;
    public static final int PLAYER_DEFAULT_SPEED = 3;
    public static final int PLAYER_BOOSTED_SPEED = 5;

    public static final int PROJECTILE_SPEED = 10;
    public static final int SHOOT_DELAY = 1000 / 3;
    public static final int SHORT_SHOOT_DELAY = 1000 / 6;
    
    // === Enemy
    public static final int ENEMY_WIDTH = 40;
    public static final int ENEMY_HEIGHT = 40;

    public static final int ENEMY_SPEED = 2;
    public static final int ENEMY_BUBBLE_SPEED = 2;

    // === Platform
    public static final int PLATFORM_WIDTH = 40;
    public static final int PLATFORM_HEIGHT = 40;

    // === Wall
    public static final int WALL_WIDTH = 40;
    public static final int WALL_HEIGHT = 40;
}
