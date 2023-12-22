package cs3500.pa03.model;

import cs3500.pa03.controller.Reader;
import cs3500.pa03.view.View;
import java.util.Map;
import java.util.Random;

/**
 * An interface for representing a model
 */
public interface Model {

  GameResult determineConclusion();

  void fireShots();

  void initShipTypes(Map<ShipType, Integer> shipTypeIntegerMap);

  void initDimensions(int x, int y);

  void initGame(Reader reader, Randomable rand, View viewer);
}
