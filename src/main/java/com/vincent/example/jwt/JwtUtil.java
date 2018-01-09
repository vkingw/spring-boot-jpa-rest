package com.vincent.example.jwt;

import com.vincent.example.config.Constants;
import com.vincent.example.model.User;
import com.vincent.example.utils.JsonUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * Created : vincent
 * Date : 2017/8/3 下午3:28
 * Email : alfa.king+git@gmail.com
 */
public class JwtUtil {

  /**
   * 由字符串生成加密key
   *
   * @return
   */
  public SecretKey generalKey() {
    String stringKey = Constants.JWT_JIAN_SHU + Constants.JWT_SECRET;
    byte[] encodedKey = Base64.decodeBase64(stringKey);
    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    return key;
  }

  /**
   * 创建jwt
   *
   * @param id
   * @param subject
   * @param ttlMillis
   * @return
   * @throws Exception
   */
  public String createJWT(String id, String subject, long ttlMillis) throws Exception {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    SecretKey key = generalKey();
    JwtBuilder builder = Jwts.builder()
      .setId(id)
      .setIssuedAt(now)
      .setSubject(subject)
      .signWith(signatureAlgorithm, key);
    if (ttlMillis >= 0) {
      long expMillis = nowMillis + ttlMillis;
      Date exp = new Date(expMillis);
      builder.setExpiration(exp);
    }
    return builder.compact();
  }

  /**
   * 解密jwt
   *
   * @param jwt
   * @return
   * @throws Exception
   */
  public Claims parseJWT(String jwt) throws Exception {
    SecretKey key = generalKey();
    Claims claims = Jwts.parser()
      .setSigningKey(key)
      .parseClaimsJws(jwt).getBody();
    return claims;
  }

  /**
   * 生成subject信息
   *
   * @param user
   * @return
   */
  public static String generalSubject(User user) {
    return JsonUtils.toJson(user);
  }

}
