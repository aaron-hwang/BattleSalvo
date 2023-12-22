package cs3500.pa03.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A mock random to get controlled and consistent random number generation
 * This reads from a long list of random ints from 0 to 14 inclusive
 */
public class MockRandom implements Randomable {

  private int index = 0;
  private final File randomNumberFile = new File("src/test/resources/randomNums.txt");
  private final Scanner scanner;

  /**
   * Default constructor
   */
  public MockRandom() throws FileNotFoundException {
    try {
      this.scanner = new Scanner(randomNumberFile);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException(e.getMessage());
    }
  }

  /**
   * Get the next int from a randomable object
   * Note: Mutates the Scanner's position
   *
   * @return the next int
   */
  @Override
  public int nextInt() {
    if (this.scanner.hasNextLine()) {
      return Integer.parseInt(scanner.nextLine());
    } else {
      throw new RuntimeException("Something went wrong generating your numbers");
    }
  }

  /**
   * The same as nextInt(),
   * but only returning ints from 0 (inclusive) to a given upperbound (exclusive)
   *
   * @param upperBound - the upper limit of what ints this function can return
   * @return - returns the next int between 0 and upperBound
   */
  @Override
  public int nextInt(int upperBound) {
    int num = this.nextInt();
    if (num < upperBound && num >= 0) {
      return num;
    } else {
      return this.nextInt(upperBound);
    }
  }

  /**
   * The same as nextInt(), but only returning ints betweeb origin (inclusive)
   * and upperBound (exclusive)
   *
   * @param origin     the lowest int we will accept
   * @param upperBound the upperbound of ints we will accept (exclusive)
   * @return returns the next int that is in between origin and upperBound
   */
  @Override
  public int nextInt(int origin, int upperBound) {
    int num = this.nextInt();
    if (num < upperBound && num >= origin) {
      return num;
    } else {
      return this.nextInt(origin, upperBound);
    }
  }

  /**
   * Get a random next boolea n
   *
   * @return a random boolean value
   */
  @Override
  public boolean nextBoolean() {
    return this.nextInt() % 2 == 0;
  }
}
