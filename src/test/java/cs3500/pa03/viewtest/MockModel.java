package cs3500.pa03.viewtest;

import cs3500.pa03.controller.Reader;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Model;
import cs3500.pa03.model.Randomable;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.View;
import java.util.Map;

/**
 * A mock model for testing
 */
public class MockModel implements Model {

  public StringBuilder testOutput = new StringBuilder();

  @Override
  public GameResult determineConclusion() {
    this.testOutput.append("test ");
    return GameResult.WIN;
  }

  @Override
  public void fireShots() {
    this.testOutput.append("test");
  }

  @Override
  public void initShipTypes(Map<ShipType, Integer> shipTypeIntegerMap) {
    this.testOutput.append("test");
  }

  @Override
  public void initDimensions(int x, int y) {
    this.testOutput.append("test");
  }

  @Override
  public void initGame(Reader reader, Randomable rand, View viewer) {
    this.testOutput.append("test");
  }

}


