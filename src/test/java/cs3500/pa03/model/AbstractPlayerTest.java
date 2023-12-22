package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa03.controller.BattleSalvoReader;
import cs3500.pa03.controller.Reader;
import cs3500.pa03.view.BattleSalvoViewer;
import cs3500.pa03.view.View;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AbstractPlayerTest {

  AbstractPlayer player1;
  AbstractPlayer player2;
  Reader reader;
  View viewer;
  Randomable rand;
  Map<ShipType, Integer> map;

  /*
   * Setup function
   */


  @BeforeEach
  public void setupForTests() {
    File input = new File("outputFolder/testInput");
    map = new HashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);
    try {
      rand = new MockRandom();
    } catch (Exception e) {
      fail();
    }
    try {
      reader = new BattleSalvoReader(new FileReader(input));
    } catch (Exception e) {
      fail();
    }
    viewer = new BattleSalvoViewer(new PrintStream(System.out));
    player1 = new ManualPlayer("Manny", reader, viewer, rand);
    player2 = new RobotPlayer("Al", rand);
  }

  @Test
  void name() {
    assertEquals(player1.name(), "Manny");
    assertNotEquals("manny", player1.name());
  }

  /*
   * Also indirectly tests generatePositionFrom and randomShipPosition for us
   */


  @Test
  void setup() {
    assertEquals(4, player1.setup(6, 6, map).size());
    assertEquals(4, player2.setup(6, 6, map).size());
  }

  @Test
  void takeShots() {
    player1.setup(6, 6, map);
    player2.setup(6, 6, map);
    List<Coord> player2Shots = player2.takeShots();
    assertEquals(player2Shots.size(), 4);
    assertEquals(player1.takeShots().size(), 4);
    assertTrue(player1.enemyBoard.get(0, 0).getImpactStatus());
    assertTrue(player1.enemyBoard.get(1, 1).getImpactStatus());
    for (Coord c : player2Shots) {
      System.out.println(c.getX() + " " + c.getY());
    }
    assertTrue(player2Shots.get(0).equals(new Coord(1, 3, false)));
    assertTrue(player2Shots.get(1).equals(new Coord(0, 2, false)));
    assertTrue(player2Shots.get(2).equals(new Coord(4, 1, false)));
    assertTrue(player2Shots.get(3).equals(new Coord(2, 5, false)));

  }

  @Test
  void reportDamage() {
    player1.setup(6, 6, map);
    player2.setup(6, 6, map);
    List<Coord> player1Shots = player1.takeShots();
    List<Coord> player2Shots = player2.takeShots();
    assertEquals(player2.reportDamage(player1Shots).size(), 1);
    assertEquals(player1.reportDamage(player2Shots).size(), 1);


  }

  /**
   * Test the successful hits
   */
  @Test
  void successfulHits() {
    player1.setup(6, 6, map);
    player2.setup(6, 6, map);
    List<Coord> damage = player1.reportDamage(player2.takeShots());
    player2.successfulHits(damage);
    assertFalse(player2.enemyBoard.get(0, 1).getImpactStatus());

  }
}
