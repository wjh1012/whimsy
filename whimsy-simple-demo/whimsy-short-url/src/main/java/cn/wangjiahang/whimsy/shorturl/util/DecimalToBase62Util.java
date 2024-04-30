package cn.wangjiahang.whimsy.shorturl.util;

public class DecimalToBase62Util {

    private static final String BASE_62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = BASE_62_CHARS.length();

    public static String decimalToBase62(long decimal) {
        StringBuilder base62 = new StringBuilder();
        while (decimal > 0) {
            int remainder = (int) (decimal % BASE);
            base62.insert(0, BASE_62_CHARS.charAt(remainder));
            decimal /= BASE;
        }
        return base62.toString();
    }

    public static long base62ToDecimal(String base62) {
        long decimal = 0;
        for (int i = 0; i < base62.length(); i++) {
            char c = base62.charAt(i);
            int position = BASE_62_CHARS.indexOf(c);
            decimal += (long) (position * Math.pow(BASE, base62.length() - i - 1));
        }
        return decimal;
    }

}
