import java.io.IOException;

/**
 * Mock Appendable class used for testing IOExceptions.
 */
public class CorruptAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Unable to append character sequence.");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Unable to append character sequence.");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Unable to append character.");
  }

}

