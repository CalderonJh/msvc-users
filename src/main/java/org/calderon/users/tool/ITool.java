package org.calderon.users.tool;

public interface ITool {
  static String message(String key) {
    return new MessagesTool().getMessage(key);
  }

  static String message(String key, Object... args) {
    return new MessagesTool().getMessage(key, args);
  }
}