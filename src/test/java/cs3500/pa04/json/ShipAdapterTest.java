package cs3500.pa04.json;

import static cs3500.pa03.model.Direction.HORIZONTAL;
import static cs3500.pa03.model.ShipType.BATTLESHIP;
import static cs3500.pa03.model.ShipType.CARRIER;
import static cs3500.pa03.model.ShipType.DESTROYER;
import static cs3500.pa03.model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.MockRandom;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.RobotPlayer;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipAdapterTest {
  Ship ship;
  ArrayList<Coord> shipPos;
  List<Ship> fleet = new ArrayList<>();
  Player player1;
  Map<ShipType, Integer> shipMap = new HashMap<>();

  @BeforeEach
  public void setup() {
    shipPos = new ArrayList<>(
        Arrays.asList(
            new Coord(1, 1, false),
            new Coord(2, 1, false),
            new Coord(3, 1, false)));
    ship = new Ship(shipPos, SUBMARINE, HORIZONTAL);
    try {
      player1 = new RobotPlayer("Al", new MockRandom());
    } catch (Exception e) {
      fail();
    }
    shipMap.put(CARRIER, 1);
    shipMap.put(BATTLESHIP, 1);
    shipMap.put(DESTROYER, 1);
    shipMap.put(SUBMARINE, 1);
    fleet = player1.setup(12, 12, shipMap);

  }

  @Test
  void shipToJson() {
    ShipJson json = ShipAdapter.shipToJson(ship);
    assertEquals(json.direction(), HORIZONTAL);
    assertEquals(json.length(), 3);
    assertEquals(json.startCoord().x(), 1);
    assertEquals(json.startCoord().y(), 1);
  }

  @Test
  void adaptFleet() {
    FleetJson fleetJson = ShipAdapter.adaptFleet(fleet);
    List<ShipJson> ships = fleetJson.fleet();
    for (int i = 0; i < ships.size(); i++) {
      System.out.println(ships.get(i).direction());
      System.out.println(ships.get(i).length());
      System.out.println(ships.get(i).startCoord());
    }
    assertEquals(ships.get(0).length(), 6);
    assertEquals(ships.get(0).direction(), HORIZONTAL);
    assertEquals(ships.get(0).startCoord().x(), 4);
    assertEquals(ships.get(0).startCoord().y(), 8);

  }
}