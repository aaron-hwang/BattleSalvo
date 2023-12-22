package cs3500.pa03.model;

import static cs3500.pa03.model.GameResult.DRAW;
import static cs3500.pa03.model.GameResult.LOSE;
import static cs3500.pa03.model.GameResult.UNCONCLUDED;
import static cs3500.pa03.model.GameResult.WIN;

import cs3500.pa03.controller.Reader;
import cs3500.pa03.view.View;
import java.util.List;
import java.util.Map;

/**
 * Our model for BattleSalvo, representing an instance of a game between two players
 */
public class BattleSalvoModel implements Model {
  private AbstractPlayer player1;
  private AbstractPlayer player2;

  private int height;
  private int width;
  private Map<ShipType, Integer> specifications;
  private int previousP1ShotsFiredSize;
  private int previousP2ShotsFiredSize;

  /**
   * Exchanges shots between two players
   */
  public void fireShots() {
    List<Coord> player1Shots = this.player1.takeShots();
    List<Coord> player2Shots = this.player2.takeShots();
    List<Coord> player1DamageTaken = this.player1.reportDamage(player2Shots);
    List<Coord> player2DamageTaken = this.player2.reportDamage(player1Shots);
    this.player1.successfulHits(player2DamageTaken);
    this.player2.successfulHits(player1DamageTaken);
    this.previousP1ShotsFiredSize = player1Shots.size();
    this.previousP2ShotsFiredSize = player2Shots.size();

  }

  /**
   * Initializes each player with the appropriate data, to be used after initDimensions and
   * initShipTypes
   *
   * @param reader Reader to inject our manual player with
   * @param rand   The Randomable to seed with
   */
  @Override
  public void initGame(Reader reader, Randomable rand, View viewer) {
    this.player1 = new ManualPlayer("Manny",
        reader, viewer, rand);
    this.player2 = new RobotPlayer("Al", rand);
    this.player1.setup(this.height, this.width, this.specifications);
    this.player2.setup(this.height, this.width, this.specifications);
    this.previousP1ShotsFiredSize = 0;
    this.previousP2ShotsFiredSize = 0;
  }

  /**
   * Initialize the dimensions of the boards for our games
   *
   * @param width width
   * @param height height
   */
  public void initDimensions(int width, int height) {
    if (width < 6 || width > 15 || height < 6 || height > 15) {
      throw new IllegalArgumentException("Input must be within [6, 15]");
    } else {
      this.height = height;
      this.width = width;
    }
  }

  /**
   * Initialize the mapping of ship types to their amounts that should be present
   *
   * @param map mapping of ships to their amounts
   */
  public void initShipTypes(Map<ShipType, Integer> map) {
    int shipSum = 0;
    int maxAmtOfShips = Math.min(this.height, this.width);
    for (ShipType s : ShipType.values()) {
      int shipAmt = map.get(s);
      if (shipAmt == 0) {
        throw new IllegalArgumentException("Must have at least 1 of each ship type");
      }
      shipSum += shipAmt;
    }

    if (shipSum > Math.min(this.height, this.width)) {
      throw new IllegalArgumentException("Must not exceed " + maxAmtOfShips + " ships");
    }

    this.specifications = map;
  }


  /**
   * Determine the conclusion of this battle salvo game
   *
   * @return The GameResult representing the conclusion of the game
   */
  @Override
  public GameResult determineConclusion() {
    if (this.previousP1ShotsFiredSize == 0 && this.previousP2ShotsFiredSize == 0) {
      return DRAW;
    } else if (this.previousP1ShotsFiredSize == 0 && !(this.previousP2ShotsFiredSize == 0)) {
      return LOSE;
    } else if (!(this.previousP1ShotsFiredSize == 0) && this.previousP2ShotsFiredSize == 0) {
      return WIN;
    } else {
      return UNCONCLUDED;
    }
  }
}