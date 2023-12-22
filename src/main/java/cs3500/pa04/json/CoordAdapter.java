package cs3500.pa04.json;

import cs3500.pa03.model.Coord;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that adapts Coords to Json and vice versa, along with volleys (Which are merely
 * collections of Coords)
 */
public class CoordAdapter {

  //Same logic for existence of the ship adapter class extends to this class.

  /**
   * Convert a given coord to a corresponding json
   *
   * @param coord The coord to convert
   * @return The coordJson
   */
  public static CoordJson coordToJson(Coord coord) {
    int x = coord.getX();
    int y = coord.getY();
    return new CoordJson(x, y);
  }

  /**
   * Convert a given CoordJson to a Coord with the appropriate location data
   *
   * @param coordJson The CoordJson to adapt to a Coord
   * @return the parsed json as a Coord
   */
  public static Coord jsonToCoord(CoordJson coordJson) {
    int x = coordJson.x();
    int y = coordJson.y();
    return new Coord(x, y, false);
  }

  /**
   * Return a VolleyJson based on a given list of coords
   *
   * @param coordList list of Coords to parse
   * @return a valid VolleyJson
   */
  public static VolleyJson coordListToVolley(List<Coord> coordList) {
    List<CoordJson> jsonList = new ArrayList<>();
    for (Coord c : coordList) {
      CoordJson json = c.toJson();
      jsonList.add(json);
    }

    return new VolleyJson(jsonList);
  }

  /**
   * Given a VolleyJson, return a list of coordinates
   *
   * @param volley the VolleyJson to parse
   * @return A list of coords
   */
  public static List<Coord> volleyToCoordList(VolleyJson volley) {
    List<Coord> toReturn = new ArrayList<>();
    for (CoordJson coordJson : volley.coordinates()) {
      Coord c = CoordAdapter.jsonToCoord(coordJson);
      toReturn.add(c);
    }

    return toReturn;
  }

}
