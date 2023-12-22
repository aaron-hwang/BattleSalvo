package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordTest {
  Coord coord1;
  Coord coord2;

  @BeforeEach
  public void setup() {
    coord1 = new Coord(1, 2, false);
    coord2 = new Coord(1, 2, true);
  }

  @Test
  void getImpactStatus() {
    coord2.setImpactStatus(true);
    assertTrue(coord2.getImpactStatus());
    assertFalse(coord1.getImpactStatus());
  }

  @Test
  void setImpactStatus() {
    coord1.setImpactStatus(true);
    assertTrue(coord1.getImpactStatus());
  }

  @Test
  void testEquals() {
    String hi = "Hi";
    assertEquals(coord1, coord2);
    assertEquals(coord2, coord1);
    assertFalse(coord1.equals(hi));
  }

  @Test
  void testToString() {
    Coord h = new Coord(1, 1, true);
    h.setImpactStatus(true);
    assertEquals(h.toString(), "H");
    assertEquals(coord1.toString(), "O");

  }

  @Test
  void testUpdateData() {
    Coord that = new Coord(1000, 123, false);
    that.setImpactStatus(true);
    coord1.copyDataOf(that);
    assertTrue(coord1.getImpactStatus());
    assertEquals(coord1.toString(), "M");
  }

  @Test
  void testHashCode() {
    assertEquals(coord1.hashCode(), 66306);
  }
}