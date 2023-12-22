package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A json that represents the specs for a fleet of ships
 */
public record FleetSpecJson(
    @JsonProperty("CARRIER") int carrierAmount,
    @JsonProperty("SUBMARINE") int subAmount,
    @JsonProperty("BATTLESHIP") int battleshipAmount,
    @JsonProperty("DESTROYER") int destroyerAmount
) {

}
