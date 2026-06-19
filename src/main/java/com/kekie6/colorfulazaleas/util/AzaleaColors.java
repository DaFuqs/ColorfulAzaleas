package com.kekie6.colorfulazaleas.util;

public enum AzaleaColors {
    WHITE("titanium", 0xFFe8fafa),
    ORANGE("tecal", 0xFFfd9919),
    MAGENTA("bromelia", 13061821),
    LIGHT_BLUE("lethe", 0xFF48c2e7),
    YELLOW("fiss", 0xFFffbb3c),
    LIME("verdant", 8439583),
    PINK("bright", 0xFFfcb9d6),
    GRAY("pluvial", 4673362),
    LIGHT_GRAY("dusk", 10329495),
    CYAN("cerulean", 0xFF3fd494),
    PURPLE("walnut", 0xFFb844e9),
    BLUE("azule", 0xFF4466e3),
    BROWN("earthen", 8606770),
    GREEN("pastoral", 6192150),
    RED("roze", 0xFFd93a2a),
    BLACK("pitch", 0xFF434550);

    final String title;
    final int tint;

    AzaleaColors(String title, int tint) {
        this.title = title;
        this.tint = tint;
    }

    public String getTitle() {
        return title;
    }

    public int getTint() {
        return tint;
    }
}
