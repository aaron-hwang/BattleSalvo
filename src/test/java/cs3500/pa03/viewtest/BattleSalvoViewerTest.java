package cs3500.pa03.viewtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.BattleSalvoViewer;
import cs3500.pa03.view.View;
import org.junit.jupiter.api.Test;

class BattleSalvoViewerTest {

  @Test
  void displayMessage() {
    StringBuilder build = new StringBuilder();
    View viewer = new BattleSalvoViewer(build);
    viewer.displayMessage("Hi");
    assertEquals(build.toString(), "Hi" + System.lineSeparator());
  }
}