package com.labs.servicedesk.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public final class HeaderUtils {

  private static final Logger log = LoggerFactory.getLogger(HeaderUtils.class);

  public static HttpHeaders createAlert(String message, String param) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-" + "-alert", message);

    try {
      headers.add(
        "X-" + "-params",
        URLEncoder.encode(param, StandardCharsets.UTF_8.toString())
      );
    } catch (UnsupportedEncodingException var5) {}

    return headers;
  }

  public static HttpHeaders createEntityCreationAlert(
    boolean enableTranslation,
    String entityName,
    String param
  ) {
    String message = enableTranslation
      ? entityName + ".created"
      : "A new " + entityName + " is created with identifier " + param;
    return createAlert(message, param);
  }

  public static HttpHeaders createEntityUpdateAlert(
    boolean enableTranslation,
    String entityName,
    String param
  ) {
    String message = enableTranslation
      ? entityName + ".updated"
      : "A " + entityName + " is updated with identifier " + param;
    return createAlert(message, param);
  }

  public static HttpHeaders createEntityDeletionAlert(
    boolean enableTranslation,
    String entityName,
    String param
  ) {
    String message = enableTranslation
      ? entityName + ".deleted"
      : "A " + entityName + " is deleted with identifier " + param;
    return createAlert(message, param);
  }

  public static HttpHeaders createFailureAlert(
    boolean enableTranslation,
    String entityName,
    String errorKey,
    String defaultMessage
  ) {
    log.error("Entity processing failed, {}", defaultMessage);
    String message = enableTranslation ? "error." + errorKey : defaultMessage;
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-" + "-error", message);
    headers.add("X-" + "-params", entityName);
    return headers;
  }
}
