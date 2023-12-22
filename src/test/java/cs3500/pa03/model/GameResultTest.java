package cs3500.pa03.model;

import static cs3500.pa03.model.GameResult.DRAW;
import static cs3500.pa03.model.GameResult.LOSE;
import static cs3500.pa03.model.GameResult.UNCONCLUDED;
import static cs3500.pa03.model.GameResult.WIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameResultTest {

  @Test
  void getResultMessage() {
    assertEquals(WIN.getResultMessage(), "Congrats, you win!");
    assertEquals(DRAW.getResultMessage(), "The game is a draw.");
    assertEquals(LOSE.getResultMessage(), "Unfortunately, you lose :(");
    assertEquals(UNCONCLUDED.getResultMessage(), "Game not yet concluded");
  }
}