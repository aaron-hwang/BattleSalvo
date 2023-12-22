package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * Json for a volley of shots
 */
public record VolleyJson(
    /*
      The coordinates in this volleyjson.
     */
    @JsonProperty("coordinates") List<CoordJson> coordinates
) {
}
