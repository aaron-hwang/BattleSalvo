package cs3500.pa03.controller;

import static cs3500.pa03.model.GameResult.UNCONCLUDED;

import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Model;
import cs3500.pa03.model.Randomable;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.BattleSalvoViewer;
import cs3500.pa03.view.View;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A controller for a game of BattleSalvo
 */
public class BattleSalvoController implements Controller {

  private final Readable input;
  private final Appendable output;
  private final Model battleSalvoModel;
  private final Reader reader;
  private final View viewer;
  private final Randomable rand;

  /**
   * Constructor
   *
   * @param input            our input method
   * @param output           our output method
   * @param battleSalvoModel our BattleSalvoNodel game
   * @param rand             our random to use
   */
  public BattleSalvoController(Readable input, Appendable output, Model battleSalvoModel,
                               Randomable rand) {
    this.input = input;
    this.output = output;
    this.battleSalvoModel = battleSalvoModel;
    this.viewer = new BattleSalvoViewer(output);
    this.reader = new BattleSalvoReader(input);
    this.rand = rand;
  }

  /**
   * Runs the controller
   */
  @Override
  public void run() {
    //Accept input for dimensions of the board, reask if invalid dimensions
    viewer.displayMessage("Welcome to BattleSalvo! Please enter the desired dimensions for your"
        + "board. Please keep in mind that the dimensions for each axis are [6, 15]");
    this.promptForDimensions();

    //ask for fleet parameters
    this.mapShipTypesPrompt();

    //Set up the game
    this.battleSalvoModel.initGame(this.reader, this.rand, this.viewer);

    //construct manual player with inputs?/manual data entry object and in controller,
    // pass in inputs
    //into the data model thing through controller so player can grab them
    //Start a loop to accept input from each player
    boolean isConcluded = false;
    GameResult result = UNCONCLUDED;
    //Get the feedback of shots fired from each player,
    //send each player the other players fired shots
    while (!isConcluded) {
      System.out.println("Help");
      //call
      try {
        this.battleSalvoModel.fireShots();
      } catch (IllegalArgumentException e) {
        this.viewer.displayMessage("");
      }
      result = this.battleSalvoModel.determineConclusion();
      isConcluded = !result.equals(UNCONCLUDED);
    }
    this.viewer.displayMessage(result.getResultMessage());
  }

  /**
   * Convert user input into a mapping of ship types and their amounts
   *
   * @return A mapping of the shiptypes and integers
   */
  private Map<ShipType, Integer> mapShipTypes() {
    HashMap<ShipType, Integer> shipMap = new HashMap<>();
    try {
      for (int i = 0; i < ShipType.values().length; i++) {
        ShipType type = ShipType.values()[i];
        int amount = Integer.parseInt(this.reader.read());
        shipMap.put(type, amount);
      }
    } catch (NumberFormatException e) {
      this.viewer.displayMessage("It seems you entered an input that wasn't a number. Try again");
      return this.mapShipTypes();
    }
    return shipMap;
  }

  /**
   * prompts the user to map ship types
   */
  private void mapShipTypesPrompt() {
    //Try to prompt user, then feed that into model
    //if that fails, catch the exception and call this function again
    try {
      this.viewer.displayMessage("Please input desired amounts for each ship type!");
      this.battleSalvoModel.initShipTypes(this.mapShipTypes());
    } catch (IllegalArgumentException e) {
      this.viewer.displayMessage(e.getMessage());
      this.mapShipTypesPrompt();
    }
  }


  /**
   * Prompts the user to input dimensions of the board
   */
  private void promptForDimensions() {
    try {
      this.viewer.displayMessage("Please input desired board dimensions. Please remember bounds"
          + " are [6, 15]");
      int[] inputs = this.dimensionsInput();
      this.battleSalvoModel.initDimensions(inputs[0], inputs[1]);
    } catch (IllegalArgumentException e) {
      this.viewer.displayMessage(e.getMessage());
      this.promptForDimensions();
    }

  }

  /**
   * Attempt to parse input for the dimensions of the desired board
   *
   * @return int array representing the desired dimensions
   */
  private int[] dimensionsInput() {
    int[] inputs = new int[2];
    try {
      int width = Integer.parseInt(reader.read());
      int height = Integer.parseInt(reader.read());
      inputs[0] = width;
      inputs[1] = height;
    } catch (NumberFormatException e) {
      this.viewer.displayMessage("Seems you inputted some non integer arguments. Try again");
      this.dimensionsInput();
    }

    return inputs;
  }


}
