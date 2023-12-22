package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests for CoordAdapter
 */
class CoordAdapterTest {

  Coord coord;
  List<Coord> coordList;

  @BeforeEach
  public void setup() {
    coord = new Coord(1, 2, false);
    coordList = new ArrayList<>();
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < 6; x++) {
        coordList.add(new Coord(x, y, false));
      }
    }
  }

  @Test
  void coordToJson() {
    CoordJson json = CoordAdapter.coordToJson(coord);
    assertEquals(json.x(), 1);
    assertEquals(json.y(), 2);
  }

  @Test
  void jsonToCoord() {
    CoordJson json = CoordAdapter.coordToJson(coord);
    Coord coord1 = CoordAdapter.jsonToCoord(json);
    assertTrue(coord1.equals(coord));
    assertTrue(coord.equals(coord1));
  }

  @Test
  void coordListToVolley() {
    VolleyJson volley = CoordAdapter.coordListToVolley(coordList);
    assertInstanceOf(VolleyJson.class, volley);
    List<CoordJson> coordJsons = volley.coordinates();
    int i = 0;
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < 6; x++) {
        CoordJson json = coordJsons.get(i);
        assertEquals(json.x(), x);
        assertEquals(json.y(), y);
        i++;
      }
    }
  }

  @Test
  void volleyToCoordList() {
    VolleyJson volley = CoordAdapter.coordListToVolley(coordList);
    assertInstanceOf(List.class, CoordAdapter.volleyToCoordList(volley));
  }
}