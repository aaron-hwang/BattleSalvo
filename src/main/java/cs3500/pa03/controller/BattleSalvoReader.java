package cs3500.pa03.controller;

import java.util.Objects;
import java.util.Scanner;

/**
 * A reader that reads in some type of input
 */
public class BattleSalvoReader implements Reader {

  private final Readable readable;

  private final Scanner scanner;

  /**
   * Constructor
   *
   * @param readable What we read
   */
  public BattleSalvoReader(Readable readable) {
    this.readable = Objects.requireNonNull(readable);
    this.scanner = new Scanner(this.readable);
  }

  /**
   * Read a message
   *
   * @return Said message
   */
  @Override
  public String read() {
    StringBuilder output = new StringBuilder();

    output.append(scanner.next());

    return output.toString();
  }
}
