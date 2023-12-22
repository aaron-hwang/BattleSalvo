package cs3500.pa04.json;

import cs3500.pa03.model.Ship;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts and handles Ships and Json interactions; i.e Ships to Json and vice versa, along with
 * volleyjson and lists of ships
 */
public class ShipAdapter {

  //NOTE: Using getters for the fields of ships to initialize the json seems bad, but decouples the
  //transformation of ships into data from the actual ship class itself. Alt option ties json
  //creation to the ships, arguably violates single responsibility principle

  /**
   * Adapt a ship to Json
   *
   * @param ship The ship to adapt
   * @return A ShipJson representing the ship we were given
   */
  public static ShipJson shipToJson(Ship ship) {
    CoordJson startCoordJson = CoordAdapter.coordToJson(ship.getStartingPosition());
    return new ShipJson(startCoordJson, ship.getLength(), ship.getDirection());
  }

  /**
   * Convert a list of ships into a fleetJson of ships
   *
   * @param ships The ships we wish to adapt
   * @return - returns a json equivalent of ships
   */
  public static FleetJson adaptFleet(List<Ship> ships) {
    List<ShipJson> shipJsons = new ArrayList<>();
    for (Ship s : ships) {
      shipJsons.add(ShipAdapter.shipToJson(s));
    }
    return new FleetJson(shipJsons);
  }
}
