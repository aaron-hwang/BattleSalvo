package cs3500.pa04.serverClient;

import static cs3500.pa03.model.ShipType.BATTLESHIP;
import static cs3500.pa03.model.ShipType.CARRIER;
import static cs3500.pa03.model.ShipType.DESTROYER;
import static cs3500.pa03.model.ShipType.SUBMARINE;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Randomable;
import cs3500.pa03.model.RobotPlayer;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyController implements Controller {

  private final String name;

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");
  private final Socket server;
  private final InputStream in;

  private final PrintStream responseStream;
  private Player player;
  private final ObjectMapper mapper = new ObjectMapper();
  private final Randomable rand;

  /**
   * Construct an instance of a ProxyController.
   *
   * @param server the socket connection to the server
   * @throws IOException if there is an unexpected error in assigning io streams to variables
   */
  public ProxyController(Socket server, String name, Randomable rand) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.responseStream = new PrintStream(server.getOutputStream());
    this.name = name;
    this.rand = rand;
    this.player = new RobotPlayer(this.name, this.rand);
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
      //run is where message is returned
    } catch (IOException e) {
      // Disconnected from server or parsing exception
      System.err.println(e.getMessage());
      throw new RuntimeException("You disconnected from the server and/or there is an error "
          + "parsing the server's message.");
    }
  }


  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new RuntimeException("Invalid message");
    }
  }


  /**
   * End the game
   *
   * @param arguments The arguments to determine how to call end game
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameArgsJson argsJson = this.mapper.convertValue(arguments, EndGameArgsJson.class);
    GameResult result = GameResult.valueOf(argsJson.result());
    String reason = argsJson.reason();
    this.player.endGame(result, reason);
    MessageJson endResponse = new MessageJson("end-game", VOID_RESPONSE);
    this.responseStream.println(JsonUtils.serializeRecord(endResponse));
    try {
      this.server.close();
    } catch (IOException e) {
      System.err.println("Server connection could not be closed");
    }
  }

  /**
   * Handle the successful hits
   *
   * @param arguments The successful hits
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    VolleyJson ourSuccessfulVolley = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> successfulHits = CoordAdapter.volleyToCoordList(ourSuccessfulVolley);
    this.player.successfulHits(successfulHits);
    MessageJson message = new MessageJson("successful-hits", VOID_RESPONSE);
    this.responseStream.println(JsonUtils.serializeRecord(message));
  }


  /**
   * Handles when we are sent a reportDdmage call from the server. Should have the player
   * report what shots were successful from the enemy, to the server.
   *
   * @param arguments The arguments from which we will grab our reported shots.
   */
  private void handleReportDamage(JsonNode arguments) {
    VolleyJson shotsFromOpponentJson = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> shotsRecieved = CoordAdapter.volleyToCoordList(shotsFromOpponentJson);
    List<Coord> takenDamage = this.player.reportDamage(shotsRecieved);
    VolleyJson volleyToSend = CoordAdapter.coordListToVolley(takenDamage);
    JsonNode volleySerialized = JsonUtils.serializeRecord(volleyToSend);
    MessageJson returnMessage = new MessageJson("report-damage", volleySerialized);
    this.responseStream.println(JsonUtils.serializeRecord(returnMessage));
  }

  /**
   * Handles when we are sent a takeShots request from the server
   */
  private void handleTakeShots() {
    List<Coord> shotsFired = this.player.takeShots();
    VolleyJson volleyToSend = CoordAdapter.coordListToVolley(shotsFired);
    JsonNode serializedVolley = JsonUtils.serializeRecord(volleyToSend);
    MessageJson responseJson = new MessageJson("take-shots", serializedVolley);
    this.responseStream.println(JsonUtils.serializeRecord(responseJson));
  }


  /**
   * Handles whenever we recieve a setup argument from the server
   *
   * @param arguments arguments of the message we recieved
   */
  private void handleSetup(JsonNode arguments) {
    SetupArgsJson args = this.mapper.convertValue(arguments, SetupArgsJson.class);
    FleetSpecJson fleetSpecs = this.mapper.convertValue(args.fleetSpec(), FleetSpecJson.class);
    //There has to be a cleaner way to do this....
    Map<ShipType, Integer> fleetSpecsMap = new HashMap<>();
    fleetSpecsMap.put(CARRIER, fleetSpecs.carrierAmount());
    fleetSpecsMap.put(BATTLESHIP, fleetSpecs.battleshipAmount());
    fleetSpecsMap.put(DESTROYER, fleetSpecs.destroyerAmount());
    fleetSpecsMap.put(SUBMARINE, fleetSpecs.subAmount());
    //INITIALIZE PLAYER
    this.player = new RobotPlayer(this.name, this.rand);
    int height = args.height();
    int width = args.width();
    //keep in mind this mutates player's fields
    List<Ship> ships = player.setup(height, width, fleetSpecsMap);
    //Convert the list of ships to a fleet, serialize the fleet as a JsonNode, then
    //Embed the fleet as the arguments of a MessageJson and then return the serialization of that...
    //Yes, this is ugly.
    FleetJson fleet = ShipAdapter.adaptFleet(ships);
    JsonNode fleetNode = JsonUtils.serializeRecord(fleet);
    MessageJson response = new MessageJson("setup", fleetNode);
    this.responseStream.println(JsonUtils.serializeRecord(response));

  }

  /**
   * Handles a join request from the server
   */
  private void handleJoin() {
    JoinArgsJson join = new JoinArgsJson(this.name, "SINGLE");
    JsonNode joinArgs = JsonUtils.serializeRecord(join);
    MessageJson joinMessage = new MessageJson("join", joinArgs);
    this.responseStream.println(JsonUtils.serializeRecord(joinMessage));
  }
}

