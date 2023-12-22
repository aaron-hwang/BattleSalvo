package cs3500.pa04.json;

import static cs3500.pa03.model.Direction.HORIZONTAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * Test for jsonutils
 */
class JsonUtilsTest {

  @Test
  void serializeRecord() {
    ShipJson shipJson = new ShipJson(new CoordJson(0, 0), 3, HORIZONTAL);
    JsonNode node = JsonUtils.serializeRecord(shipJson);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node1 = mapper.convertValue(shipJson, JsonNode.class);
    assertInstanceOf(JsonNode.class, node);
    assertEquals(node1, node);
  }
}