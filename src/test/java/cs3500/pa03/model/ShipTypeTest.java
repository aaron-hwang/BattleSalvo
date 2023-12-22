package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShipTypeTest {

  @Test
  void getSize() {
    assertEquals(ShipType.CARRIER.getSize(), 6);
    assertEquals(ShipType.BATTLESHIP.getSize(), 5);
    assertEquals(ShipType.DESTROYER.getSize(), 4);
    assertEquals(ShipType.SUBMARINE.getSize(), 3);
  }
}