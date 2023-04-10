package utils;

import java.io.IOException;

/**
 * Class FakeAppendable is an instance of {@link Appendable} that always throw IOException.
 * This is designed for test only.
 */
public class FakeAppendable implements Appendable {

  /**
   * append method is designed to throw IOException.
   *
   * @param csq The character sequence to append.  If {@code csq} is
   *            {@code null}, then the four characters {@code "null"} are
   *            appended to this Appendable.
   * @return no return.
   * @throws IOException always.
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  /**
   * append method is designed to throw IOException.
   *
   * @param csq   The character sequence from which a subsequence will be
   *              appended.  If {@code csq} is {@code null}, then characters
   *              will be appended as if {@code csq} contained the four
   *              characters {@code "null"}.
   * @param start The index of the first character in the subsequence
   * @param end   The index of the character following the last character in the
   *              subsequence
   * @return no return.
   * @throws IOException always.
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  /**
   * append method is designed to throw IOException.
   *
   * @param c The character to append
   * @return no return.
   * @throws IOException always.
   */
  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }

}
