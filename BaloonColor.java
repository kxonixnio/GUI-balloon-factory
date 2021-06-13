public enum BaloonColor {
    DARK_RED("139 0 0"), LIGHT_PINK("255 182 193"), KHAKI("240 230 140"),
    INDIGO("75 0 130"), ORCHID("218 112 214"), LIME("0 255 0"),
    TEAL("0 128 128"), TURQUOISE("64 244 208"), DEEP_SKY_BLUE("0 191 255"),
    NAVY("0 0 128"), GOLDENROD("218 165 32"), CHOCOLATE("210 105 30"),
    LIGHTCORAL("240 128 128"), WHEAT("245 222 179"), SIENNA("160 82 45"),
    SILVER("192 192 192");

    private final String color_code;
    private final String[]RGB;

    BaloonColor(String color_code) {
        this.color_code = color_code;
        RGB = color_code.split(" ");
    }

    /*
        Funkcje zwracające odpowiednie "części" koloru RGB
     */
    public int getColor_R(){
        return Integer.parseInt(RGB[0]);
    }

    public int getColor_G(){
        return Integer.parseInt(RGB[1]);
    }

    public int getColor_B(){
        return Integer.parseInt(RGB[2]);
    }

    public String getColor_code(){
        return color_code;
    }
}
