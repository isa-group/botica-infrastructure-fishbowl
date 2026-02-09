package es.us.isa.botica.fishbowl.manager;

import static org.assertj.core.api.Assertions.assertThat;

import es.us.isa.botica.fishbowl.manager.Fishbowl.Position;
import org.junit.jupiter.api.Test;

public class FishbowlTest {
  @Test
  void testRender() {
    Fishbowl fishbowl = new Fishbowl(9);
    fishbowl.setPosition("1", new Position(8, 8));
    fishbowl.setPosition("1", new Position(0, 0));
    fishbowl.setPosition("2", new Position(5, 5));

    String render = fishbowl.render();
    assertThat(render)
        .isEqualTo(
            """
            1--------
            ---------
            ---------
            ---------
            ---------
            -----2---
            ---------
            ---------
            ---------""");
  }
}
