package org.calderon.users.tool;

import jakarta.annotation.PostConstruct;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessagesTool {
  private static MessageSource mss;
  private final MessageSource messageSource;

  public MessagesTool(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @PostConstruct
  public void init() {
    mss = this.messageSource;
  }
  public static String msg(String key) {
    return mss.getMessage(key, null, LocaleContextHolder.getLocale());
  }
  public static String msg(String key, Object... args) {
    return mss.getMessage(key, args, LocaleContextHolder.getLocale());
  }
}
