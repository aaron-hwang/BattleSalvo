package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BattleSalvoReaderTest {
  Reader reader;
  Readable readable;

  @BeforeEach
  public void setup() {
    try {
      readable = new FileReader(Path.of("outputFolder/testReader").toFile());
    } catch (FileNotFoundException e) {
      fail();
    }
    reader = new BattleSalvoReader(readable);
  }

  @Test
  void read() {
    StringBuilder build = new StringBuilder();
    for (int i = 0; i < 5; i++) {
      build.append(reader.read());
    }
    assertEquals(build.toString(), "12345");
  }

}