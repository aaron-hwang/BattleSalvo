package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Argments for an endgame message
 *
 * @param result The result of our game
 * @param reason the reason we lost
 */
public record EndGameArgsJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason
) {
}
