package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Direction;

/**
 * Json for a ship
 */
public record ShipJson(

    /*
      Starting coord of the ship
     */
    @JsonProperty("coord") CoordJson startCoord,

    /*
      The length of the ship this json represents
     */
    @JsonProperty("length") int length,

    /*
      The direction of the ship this json represents
     */
    @JsonProperty("direction") Direction direction) {
}
