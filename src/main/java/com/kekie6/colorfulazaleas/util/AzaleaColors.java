package com.kekie6.colorfulazaleas.util;

public enum AzaleaColors {
    WHITE("titanium", 0xFFe8fafa),
    ORANGE("tecal", 0xFFfd9919),
    MAGENTA("bromelia", 13061821),
    LIGHT_BLUE("lethe", 3949738), // Light Blue is Blue
    YELLOW("fiss", 0xFFffbb3c),
    LIME("verdant", 8439583),
    PINK("bright", 0xFFfcb9d6),
    GRAY("pluvial", 4673362),
    LIGHT_GRAY("dusk", 10329495),
    CYAN("cerulean", 1481884),
    PURPLE("walnut", 0xFFb844e9),
    BLUE("azule", 0xFF2ae8e2), // Blue is Light Blue
    BROWN("earthen", 8606770),
    GREEN("pastoral", 6192150),
    RED("roze", 0xFFd93a2a),
    BLACK("pitch", 1908001),
    ;

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
