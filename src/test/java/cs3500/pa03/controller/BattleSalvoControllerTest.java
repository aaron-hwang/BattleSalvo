package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa03.model.RandomDecorator;
import cs3500.pa03.model.Randomable;
import cs3500.pa03.viewtest.MockModel;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.jupiter.api.Test;

/**
 * Test for the battlesalvocontroller
 */

class BattleSalvoControllerTest {

  MockModel model;

  @Test
  void run() {
    model = new MockModel();
    Readable readable = null;
    Appendable appendable;
    Randomable rand = new RandomDecorator(6);
    try {
      readable = new FileReader("outputFolder/testInput");
    } catch (FileNotFoundException e) {
      fail();
    }
    appendable = new StringBuilder();

    Controller controller = new BattleSalvoController(readable, appendable, model, rand);
    controller.run();
    assertFalse(appendable.toString().isEmpty());
    assertTrue(model.testOutput.toString().contains("test "));
  }
}