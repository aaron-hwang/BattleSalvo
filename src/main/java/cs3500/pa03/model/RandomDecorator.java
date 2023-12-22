package cs3500.pa03.model;

import java.util.Random;

/**
 * Decorator class for randoms
 */
public class RandomDecorator implements Randomable {

  private final Random rand;

  /**
   * Default constructor
   */
  public RandomDecorator() {
    this.rand = new Random();
  }

  /**
   * Seeded constructor
   *
   * @param seed Construct a randomDecorator with a seed
   */
  public RandomDecorator(int seed) {
    this.rand = new Random(seed);
  }

  /**
   * Get the next int from a randomable object
   *
   * @return the next int
   */
  @Override
  public int nextInt() {
    return this.rand.nextInt();
  }

  /**
   * The same as nextInt(),
   * but only returning ints from 0 (inclusive) to a given upperbound (exclusive)
   *
   * @param upperBound - upper limit of what ints this will return
   * @return - returns next int between 0 and upperBound
   */
  @Override
  public int nextInt(int upperBound) {
    return this.rand.nextInt(upperBound);
  }

  /**
   * The same as nextInt(), but only returning ints betweeb origin (inclusive)
   * and upperBound (exclusive)
   *
   * @param origin     the lowest int we will accept
   * @param upperBound the upperbound of ints we will accept (exclusive)
   * @return - returns the next int between origin and upperBound
   */
  @Override
  public int nextInt(int origin, int upperBound) {
    return this.rand.nextInt(origin, upperBound);
  }

  @Override
  public boolean nextBoolean() {
    return this.nextInt() % 2 == 0;
  }
}
