package cs3500.pa03.view;

import java.io.IOException;

/**
 * A viewer for a BattleSalvo game
 */
public class BattleSalvoViewer implements View {
  private final Appendable appendable;

  /**
   * Default constructor
   *
   * @param appendable The appendable we wish to use to display to
   */
  public BattleSalvoViewer(Appendable appendable) {
    this.appendable = appendable;
  }

  /**
   * Display a given message
   *
   * @param message the given message
   */
  @Override
  public void displayMessage(String message) {
    try {
      this.appendable.append(message).append(System.lineSeparator());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

  }
}
