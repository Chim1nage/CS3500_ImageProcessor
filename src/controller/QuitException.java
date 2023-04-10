package controller;

/**
 * Class QuitException represent an exception that thrown when Controller meet command of quit.
 * It extends {@link RuntimeException}.
 */
public class QuitException extends RuntimeException {


  /**
   * isQuitException static method is designed to judge whether it is a command of quit with given
   * {@link String command}. Return {@link Boolean true } if it is a command of quit. Otherwise,
   * return {@link Boolean false}.
   *
   * @param command is an instance of {@link String} that represent a command
   * @return {@link Boolean true } if is command of quit. Otherwise,{@link Boolean false}.
   */
  public static boolean isQuitException(String command) {
    return command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit");
  }
}
