package cs3500.pa04;

import cs3500.pa03.controller.BattleSalvoController;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.BattleSalvoModel;
import cs3500.pa03.model.Model;
import cs3500.pa03.model.RandomDecorator;
import cs3500.pa03.model.Randomable;
import cs3500.pa04.serverClient.ProxyController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Readable input = new InputStreamReader(System.in);
    Appendable output = new PrintStream(System.out);
    Model realModel = new BattleSalvoModel();
    Randomable rand = new RandomDecorator();
    Controller battleSalvo = new BattleSalvoController(input, output, realModel, rand);

    switch (args.length) {
      case 2 -> Driver.validHostPort(args[0], args[1]);
      case 0 -> battleSalvo.run();
      default ->
          throw new IllegalArgumentException("Please input a host and a port, or input nothing to "
              + "begin a CPU match.");
    }
  }


  /**
   * Validates and instantiates a server connection and client if all given arguments are valid
   *
   * @param arg - String representing the host name
   * @param arg1 - String representing the port number
   */
  private static void validHostPort(String arg, String arg1) {
    int portNum;
    Socket serverSocket;
    ProxyController client;

    try {
      portNum = Integer.parseInt(arg1);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Please input a valid port.");
    }

    try {
      serverSocket = new Socket(arg, portNum);
    } catch (UnknownHostException e) {
      throw new IllegalArgumentException("Please input a valid host name.");
    } catch (IOException e) {
      throw new RuntimeException("There's been an error assigning input/output streams when "
      + "creating the server socket.");
    }

    try {
      client = new ProxyController(serverSocket, "aliens-from-london", new RandomDecorator());
    } catch (IOException e) {
      throw new RuntimeException("There's been an error assigning input/output streams when "
      + "creating the client.");
    }

    client.run();
  }
}