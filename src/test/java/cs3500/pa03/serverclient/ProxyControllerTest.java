package cs3500.pa03.serverclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;
import cs3500.pa03.model.MockRandom;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.CoordAdapter;
import cs3500.pa04.json.EndGameArgsJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.FleetSpecJson;
import cs3500.pa04.json.JoinArgsJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupArgsJson;
import cs3500.pa04.json.ShipAdapter;
import cs3500.pa04.json.VolleyJson;
import cs3500.pa04.mockjsonsockets.Mocket;
import cs3500.pa04.serverClient.ProxyController;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
TODO: checkstyle patterns
 */

class ProxyControllerTest {
  private Mocket server;
  private ProxyController controller;
  private final ObjectMapper mapper = new ObjectMapper();
  private final MessageJson endGame = new MessageJson("end-game",
      JsonUtils.serializeRecord(new EndGameArgsJson("WIN", "u win lol")));
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");
  private final MessageJson endGameResponse = new MessageJson("end-game", VOID_RESPONSE);
  private MessageJson messageOutputSetup;
  private MessageJson messageInputSetup;
  private ArrayList<MessageJson> inputs;
  private final String ls = System.lineSeparator();

  @BeforeEach
  public void beforeEach() {
    messageInputSetup = new MessageJson("setup",
        JsonUtils.serializeRecord(new SetupArgsJson(10, 10,
            new FleetSpecJson(1, 1, 1, 1))));
    //init shiplist
    ArrayList<Coord> coordListSetup = new ArrayList<>();
    coordListSetup.add(new Coord(4, 8, true));
    coordListSetup.add(new Coord(5, 8, true));
    coordListSetup.add(new Coord(6, 8, true));
    coordListSetup.add(new Coord(7, 8, true));
    coordListSetup.add(new Coord(8, 8, true));
    coordListSetup.add(new Coord(9, 8, true));
    ArrayList<Coord> coordListSetup2 = new ArrayList<>();
    coordListSetup2.add(new Coord(0, 3, true));
    coordListSetup2.add(new Coord(0, 4, true));
    coordListSetup2.add(new Coord(0, 5, true));
    coordListSetup2.add(new Coord(0, 6, true));
    coordListSetup2.add(new Coord(0, 7, true));
    ArrayList<Coord> coordListSetup3 = new ArrayList<>();
    coordListSetup3.add(new Coord(2, 2, true));
    coordListSetup3.add(new Coord(2, 3, true));
    coordListSetup3.add(new Coord(2, 4, true));
    coordListSetup3.add(new Coord(2, 5, true));
    ArrayList<Coord> coordListSetup4 = new ArrayList<>();
    coordListSetup4.add(new Coord(5, 0, true));
    coordListSetup4.add(new Coord(5, 1, true));
    coordListSetup4.add(new Coord(5, 2, true));
    //create arraylist of ships
    List<Ship> shipList = new ArrayList();
    shipList.add(new Ship(coordListSetup, ShipType.CARRIER, Direction.HORIZONTAL));
    shipList.add(new Ship(coordListSetup2, ShipType.BATTLESHIP, Direction.VERTICAL));
    shipList.add(new Ship(coordListSetup3, ShipType.DESTROYER, Direction.VERTICAL));
    shipList.add(new Ship(coordListSetup4, ShipType.SUBMARINE, Direction.VERTICAL));
    //make fleet into json
    FleetJson fleet = ShipAdapter.adaptFleet(shipList);
    JsonNode setupArgs = JsonUtils.serializeRecord(fleet);
    messageOutputSetup = new MessageJson("setup", setupArgs);
    //make input array
    inputs = new ArrayList<>();
  }


  @Test
  void testRunJoin() {
    //make input message
    MessageJson messageJson = new MessageJson("join", VOID_RESPONSE);
    inputs.add(messageJson);
    inputs.add(endGame);

    //make controller
    server = new Mocket(new ByteArrayOutputStream(), inputs);
    try {
      controller = new ProxyController(server, "name", new MockRandom());
    } catch (IOException e) {
      throw new RuntimeException("Error initializing I/O Streams in ProxyController.");
    }

    controller.run();

    //make correct output
    JsonNode joinNode = JsonUtils.serializeRecord(new JoinArgsJson("name", "SINGLE"));
    MessageJson messageCorrect = new MessageJson("join", joinNode);

    assertEquals(JsonUtils.serializeRecord(messageCorrect) + ls
        + JsonUtils.serializeRecord(endGameResponse) + ls, server.getOutputStream().toString());
  }

  @Test
  void testRunSetup() {
    inputs.add(messageInputSetup);
    inputs.add(endGame);

    //make controller
    server = new Mocket(new ByteArrayOutputStream(), inputs);
    try {
      controller = new ProxyController(server, "name", new MockRandom());
    } catch (IOException e) {
      throw new RuntimeException("Error initializing I/O Streams in ProxyController.");
    }

    controller.run();

    assertEquals(JsonUtils.serializeRecord(messageOutputSetup) + ls
        + JsonUtils.serializeRecord(endGameResponse) + ls, server.getOutputStream().toString());
  }

  @Test
  void testRunTakeShots() {
    //make input
    MessageJson input = new MessageJson("take-shots", mapper.createObjectNode());

    inputs.add(messageInputSetup);
    inputs.add(input);
    inputs.add(endGame);

    //make controller
    server = new Mocket(new ByteArrayOutputStream(), inputs);
    try {
      controller = new ProxyController(server, "name", new MockRandom());
    } catch (IOException e) {
      throw new RuntimeException("Error initializing I/O Streams in ProxyController.");
    }

    controller.run();

    //make correct output
    List<Coord> coordList = new ArrayList<>();
    coordList.add(new Coord(1, 5, false));
    coordList.add(new Coord(0, 8, false));
    coordList.add(new Coord(4, 3, false));
    coordList.add(new Coord(9, 7, false));

    VolleyJson volley = CoordAdapter.coordListToVolley(coordList);
    JsonNode takeShotsNode = JsonUtils.serializeRecord(volley);
    MessageJson messageCorrect = new MessageJson("take-shots", takeShotsNode);

    assertEquals(JsonUtils.serializeRecord(messageOutputSetup) + ls
        + JsonUtils.serializeRecord(messageCorrect) + ls
        + JsonUtils.serializeRecord(endGameResponse) + ls, server.getOutputStream().toString());
  }

  @Test
  void testRunReportDamage() {
    //make input
    List<Coord> coordListInput = new ArrayList<>();
    coordListInput.add(new Coord(0, 1, false));
    coordListInput.add(new Coord(3, 2, false));
    VolleyJson volleyJsonInput = CoordAdapter.coordListToVolley(coordListInput);
    MessageJson input = new MessageJson("report-damage",
        JsonUtils.serializeRecord(volleyJsonInput));


    inputs.add(messageInputSetup);
    inputs.add(input);
    inputs.add(endGame);

    //make controller
    server = new Mocket(new ByteArrayOutputStream(), inputs);
    try {
      controller = new ProxyController(server, "name", new MockRandom());
    } catch (IOException e) {
      throw new RuntimeException("Error initializing I/O Streams in ProxyController.");
    }

    controller.run();

    //make correct output
    List<Coord> coordList = new ArrayList<>();

    VolleyJson volley = CoordAdapter.coordListToVolley(coordList);
    JsonNode reportDamageNode = JsonUtils.serializeRecord(volley);
    MessageJson messageCorrect = new MessageJson("report-damage", reportDamageNode);

    assertEquals(JsonUtils.serializeRecord(messageOutputSetup) + ls
        + JsonUtils.serializeRecord(messageCorrect) + ls
        + JsonUtils.serializeRecord(endGameResponse)
        + ls, server.getOutputStream().toString());
  }

  @Test
  void testRunSuccessfulHits() {
    //make input
    List<Coord> coordListInput = new ArrayList<>();
    coordListInput.add(new Coord(0, 1, false));
    coordListInput.add(new Coord(3, 2, false));

    VolleyJson volley = CoordAdapter.coordListToVolley(coordListInput);
    JsonNode successfulHitsNode = JsonUtils.serializeRecord(volley);
    MessageJson messageJsonInput = new MessageJson("successful-hits",
        successfulHitsNode);

    inputs.add(messageInputSetup);
    inputs.add(messageJsonInput);
    inputs.add(endGame);

    //make controller
    server = new Mocket(new ByteArrayOutputStream(), inputs);
    try {
      controller = new ProxyController(server, "name", new MockRandom());
    } catch (IOException e) {
      throw new RuntimeException("Error initializing I/O Streams in ProxyController.");
    }

    controller.run();

    //make correct output
    MessageJson messageCorrect = new MessageJson("successful-hits",
        VOID_RESPONSE);

    assertEquals(JsonUtils.serializeRecord(messageOutputSetup) + ls
        + JsonUtils.serializeRecord(messageCorrect) + ls
        + JsonUtils.serializeRecord(endGameResponse) + ls, server.getOutputStream().toString());
  }

  @Test
  void testRunEndGame() {

    //make input
    EndGameArgsJson endGameArgsJson = new EndGameArgsJson("WIN", "Player 1 sank all of"
        + " Player 2's ships");
    JsonNode endGameNode = JsonUtils.serializeRecord(endGameArgsJson);
    MessageJson messageJsonInput = new MessageJson("end-game",
        endGameNode);

    inputs.add(messageJsonInput);

    //make controller
    server = new Mocket(new ByteArrayOutputStream(), inputs);
    try {
      controller = new ProxyController(server, "name", new MockRandom());
    } catch (IOException e) {
      throw new RuntimeException("Error initializing I/O Streams in ProxyController.");
    }

    controller.run();

    //make correct output
    MessageJson messageCorrect = new MessageJson("end-game",
        VOID_RESPONSE);

    assertEquals(JsonUtils.serializeRecord(messageCorrect) + System.lineSeparator(),
        server.getOutputStream().toString());
  }

  @Test
  public void testErrors() {

    MessageJson wrongName = new MessageJson("nonexistant", mapper.createObjectNode());
    inputs.add(wrongName);
    inputs.add(endGame);

    server = new Mocket(new ByteArrayOutputStream(), inputs);
    try {
      controller = new ProxyController(server, "name", new MockRandom());
    } catch (IOException e) {
      throw new RuntimeException("Error initializing I/O Streams in ProxyController.");
    }

    try {
      controller.run();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }
}