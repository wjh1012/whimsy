package cn.wangjiahang.whimsy.shorturl.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * @author jh.wang
 * @since 2024/4/28
 */
public class ShortUrlGenUtil {
    public static String domain = "";

    public static String genShortUrl(String longUrl) {
        if (longUrl == null || longUrl.isBlank()) {
            return "";
        }

        final long urlSign = Hashing.murmur3_128().hashString(longUrl, StandardCharsets.UTF_8).padToLong();
        return DecimalToBase62Util.decimalToBase62(urlSign);
    }

    public static String buildAllUrl(String sign) {
        if (domain == null || domain.isBlank()) {
            throw new IllegalArgumentException("未初始化系统域名");
        }

        if (sign == null || sign.isBlank()) {
            throw new IllegalArgumentException("短链标识无效");
        }

        return domain + sign;
    }

}
