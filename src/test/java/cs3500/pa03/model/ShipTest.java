package cs3500.pa03.model;

import static cs3500.pa03.model.ShipType.BATTLESHIP;
import static cs3500.pa03.model.ShipType.DESTROYER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

  Ship ship1;
  Ship ship2;
  ArrayList<Coord> coords = new ArrayList<>();
  ArrayList<Coord> sunkCoords = new ArrayList<>();

  @BeforeEach
  public void setup() {
    coords.add(new Coord(0, 0, true));
    coords.add(new Coord(0, 1, true));
    coords.add(new Coord(0, 2, true));
    coords.add(new Coord(0, 3, true));
    ship1 = new Ship(coords, DESTROYER, Direction.VERTICAL);
    for (int i = 0; i < 5; i++) {
      Coord c = new Coord(0, i, true);
      c.setImpactStatus(true);
      sunkCoords.add(c);
    }
    ship2 = new Ship(sunkCoords, BATTLESHIP, Direction.VERTICAL);
  }

  @Test
  void isSunk() {
    assertFalse(ship1.isSunk());
    assertTrue(ship2.isSunk());
  }

  @Test
  void containsCoord() {
    for (Coord c : coords) {
      assertTrue(ship2.containsCoord(c));
    }
  }
}