package com.bubblebobble.levels;

public final class LevelManager {
    public static Level getStartLevel() {
        return new Level01();
    }

    public static Level getNextLevel(Level currentLevel) {
        switch (currentLevel.getLevel()) {
            case 1:
                return new Level02();
            case 2:
                return new Level03();
            case 3:
                return new Level04();
            case 4:
                return new Level05();
            case 5:
                return new Level06();
            case 6:
                return new Level07();
            case 7:
                return new Level08();
            case 8:
                return new Level09();
            case 9:
                return new Level10();
            case 10:
                return new Level11();
            case 11:
                return new Level12();
            case 12:
                return new Level13();
            case 13:
                return new Level14();
            case 14:
                return new Level15();
            case 15:
                return new Level16();
            default:
                return null;
        }
    }
}
