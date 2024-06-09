package com.bubblebobble.contansts;

public enum PowerUps {
    VELOCITA("velocita");

    private final String name;

    private PowerUps(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
