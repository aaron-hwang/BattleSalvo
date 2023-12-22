package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa03.controller.BattleSalvoReader;
import cs3500.pa03.controller.Reader;
import cs3500.pa03.view.BattleSalvoViewer;
import cs3500.pa03.view.View;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BattleSalvoModelTest {
  Model exampleModel = new BattleSalvoModel();
  Model secondModel = new BattleSalvoModel();
  Map<ShipType, Integer> map;
  Reader reader;
  Reader otherReader;
  View viewer;
  StringBuilder build = new StringBuilder();

  @BeforeEach
  void setup() {
    try {
      reader = new BattleSalvoReader(new FileReader("outputFolder/testInput"));
    } catch (Exception e) {
      fail();
    }
    try {
      viewer = new BattleSalvoViewer(build);
    } catch (Exception e) {
      fail();
    }
    try {
      otherReader = new BattleSalvoReader(new FileReader("outputFolder/testInput1"));
    } catch (Exception e) {
      fail();
    }
    map = new HashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);
    exampleModel.initDimensions(6, 6);
    exampleModel.initShipTypes(map);
    Randomable rand = new RandomDecorator(9);
    exampleModel.initGame(reader, rand, viewer);
    secondModel.initDimensions(6, 6);
    secondModel.initShipTypes(map);
    secondModel.initGame(otherReader, rand, viewer);

  }

  @Test
  void fireShots() {
    exampleModel.fireShots();
    assertTrue(build.toString().contains("O"));
    assertTrue(build.toString().contains("S "));

  }

  @Test
  void initGame() {
  }

  @Test
  void initDimensions() {
    try {
      assertThrows(IllegalArgumentException.class,
          () -> exampleModel.initDimensions(6, 900));
    } catch (Exception e) {
      fail();
    }
    try {
      assertThrows(IllegalArgumentException.class,
          () -> exampleModel.initDimensions(900, 900));
    } catch (Exception e) {
      fail();
    }
    try {
      assertThrows(IllegalArgumentException.class,
          () -> exampleModel.initDimensions(900, 6));
    } catch (Exception e) {
      fail();
    }
    try {
      assertDoesNotThrow(() -> exampleModel.initDimensions(6, 6));
    } catch (Exception e) {
      fail();
    }
    try {
      assertThrows(IllegalArgumentException.class,
          () -> exampleModel.initDimensions(5, 6));
    } catch (Exception e) {
      fail();
    }
    try {
      assertThrows(IllegalArgumentException.class,
          () -> exampleModel.initDimensions(8, 4));
    } catch (Exception e) {
      fail();
    }

  }

  @Test
  void initShipTypes() {
    Map<ShipType, Integer> newMap = new HashMap<>();
    newMap.put(ShipType.CARRIER, 1);
    newMap.put(ShipType.BATTLESHIP, 0);
    newMap.put(ShipType.DESTROYER, 1);
    newMap.put(ShipType.SUBMARINE, 1);
    try {
      assertThrows(IllegalArgumentException.class,
          () -> exampleModel.initShipTypes(newMap));
    } catch (Exception e) {
      fail();
    }
    try {
      assertDoesNotThrow(() -> exampleModel.initShipTypes(map));
    } catch (Exception e) {
      fail();
    }
    Map<ShipType, Integer> realMap = new HashMap<>();
    realMap.put(ShipType.CARRIER, 999);
    realMap.put(ShipType.BATTLESHIP, 1);
    realMap.put(ShipType.DESTROYER, 1);
    realMap.put(ShipType.SUBMARINE, 1);
    try {
      assertThrows(IllegalArgumentException.class,
          () -> exampleModel.initShipTypes(realMap));
    } catch (Exception e) {
      fail();
    }

  }

  @Test
  void determineConclusion() {
    assertEquals(exampleModel.determineConclusion(), GameResult.DRAW);
  }

  @Test
  void determineConclusionUnconcluded() {
    exampleModel.fireShots();
    assertEquals(exampleModel.determineConclusion(), GameResult.UNCONCLUDED);
  }

  @Test
  void determineConclusionDraw() {
    for (int i = 0; i < 13; i++) {
      secondModel.fireShots();
    }
    assertEquals(secondModel.determineConclusion(), GameResult.DRAW);
  }


}