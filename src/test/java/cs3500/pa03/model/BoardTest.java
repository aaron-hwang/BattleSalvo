package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

  Board board;

  @BeforeEach
  public void setup() {
    board = new Board(15, 15);
  }

  @Test
  void updateCoord() {
    Coord c = new Coord(0, 0, false);
    c.setImpactStatus(true);
    this.board.updateCoord(c);
    assertTrue(board.get(0, 0).getImpactStatus());
    assertEquals(board.get(0, 0).toString(), "M");
  }

  @Test
  void testToString() {
    assertEquals(board.toString().substring(0, 1), "O");
  }

  @Test
  public void testGetRandomCoord() {
    Randomable random = new RandomDecorator(12);
    Coord c = this.board.getRandomCoord(random);
    Coord d = new Coord(6, 2, false);
    d.setImpactStatus(true);
    board.updateCoord(d);
    assertTrue(c.equals(new Coord(6, 2, false)));
    assertTrue(c.getImpactStatus());
  }

  @Test
  public void testFreeSquares() {
    assertEquals(board.freeSquares(), 225);
  }

  @Test
  void get() {
    assertEquals(board.get(0, 0), new Coord(0, 0, true));
  }

  @Test
  void getRandomCoord() {
    Randomable rand = null;
    try {
      rand = new MockRandom();
    } catch (Exception e) {
      fail();
    }
    Board smallBoard = new Board(2, 2);
    Coord c = smallBoard.getRandomCoord(rand);
    assertFalse(c.equals(new Coord(1, 1, false)));
    assertTrue(c.getX() == 1);
    assertTrue(c.getY() == 0);
  }

  @Test
  void generatePositions() {
    Randomable other = null;
    try {
      other = new MockRandom();
    } catch (Exception e) {
      fail();
    }

    ArrayList<Coord> coords = board.generatePositions(0, 0, other, 3);
    assertTrue(coords.size() == 3);
    assertTrue(coords.get(0).equals(new Coord(0, 0, false)));
    assertTrue(coords.get(1).equals(new Coord(0, 1, false)));
    assertTrue(coords.get(2).equals(new Coord(0, 2, false)));

  }
}