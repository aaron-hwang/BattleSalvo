package cs3500.pa04.mockjsonsockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.MessageJson;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock a Socket to simulate behaviors of ProxyControllers being connected to a server.
 */
public class Mocket extends Socket {

  private final InputStream testInputs;
  private final ByteArrayOutputStream testLog;
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * @param testLog what the server has received from the client
   * @param toSend what the server will send to the client
   */
  public Mocket(ByteArrayOutputStream testLog, ArrayList<MessageJson> toSend) {
    this.testLog = testLog;
    List<Byte> byteArray = new ArrayList<>();

    for (MessageJson m : toSend) {
      String json;
      //serialize the message into an input stream
      try {
        json = objectMapper.writeValueAsString(m);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Error converting to json string.");
      }

      for (byte b : json.getBytes()) {
        byteArray.add(b);
      }
    }
    byte[] byteArrayNotList = new byte[byteArray.size()];

    for (int i = 0; i < byteArray.size(); i++) {
      byteArrayNotList[i] = byteArray.get(i);
    }

    this.testInputs = new ByteArrayInputStream(byteArrayNotList);
  }

  @Override
  public InputStream getInputStream() {
    return this.testInputs;
  }

  @Override
  public OutputStream getOutputStream() {
    return this.testLog;
  }
}