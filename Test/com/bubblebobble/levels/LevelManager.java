package com.bubblebobble.levels;

import java.util.Optional;

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
            default:
                return null;
        }
    }
}
