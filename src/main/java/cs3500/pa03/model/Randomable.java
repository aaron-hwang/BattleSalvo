package cs3500.pa03.model;


/**
 * An object that can be randomized
 */
public interface Randomable {

  /**
   * Get the next int from a randomable object
   *
   * @return the next int
   */
  int nextInt();

  /**
   * The same as nextInt(),
   * but only returning ints from 0 (inclusive) to a given upperbound (exclusive)
   *
   * @param upperBound - the upper limit of what ints this will return
   * @return - returns next int between 0 and upperBound
   */
  int nextInt(int upperBound);

  /**
   * The same as nextInt(), but only returning ints betweeb origin (inclusive)
   * and upperBound (exclusive)
   *
   * @param origin the lowest int we will accept
   * @param upperBound the upperbound of ints we will accept (exclusive)
   * @return - returns the next int between origin and upperBound
   */
  int nextInt(int origin, int upperBound);

  boolean nextBoolean();
}
