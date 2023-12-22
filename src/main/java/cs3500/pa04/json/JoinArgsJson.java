package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json record for our arguments to send back to the server when we are sent a join request
 */
public record JoinArgsJson(@JsonProperty("name") String name,
                           @JsonProperty("game-type") String gameType) {
}
