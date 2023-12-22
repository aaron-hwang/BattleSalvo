package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Ship;
import java.util.List;

/**
 * Json to represent a fleet
 *
 * @param fleet ships this fleet represents
 */
public record FleetJson(
    @JsonProperty List<ShipJson> fleet) {
}
