package es.us.isa.botica.fishbowl.manager;

import es.us.isa.botica.bot.BaseBot;
import es.us.isa.botica.bot.OrderHandler;
import es.us.isa.botica.bot.shutdown.ShutdownRequest;
import es.us.isa.botica.bot.shutdown.ShutdownRequestHandler;
import es.us.isa.botica.bot.shutdown.ShutdownResponse;
import es.us.isa.botica.fishbowl.manager.Fishbowl.Position;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagerBot extends BaseBot {
  private static final Logger log = LoggerFactory.getLogger(ManagerBot.class);
  private static final Path DIRECTORY_PATH = Path.of("/app/renders");
  private static final int FISHBOWL_SIZE = 9;

  private final Fishbowl fishbowl = new Fishbowl(FISHBOWL_SIZE);
  private ScheduledExecutorService executor;
  private int fileVersion = 1;

  @Override
  public void configure() {
    int delay = Integer.parseInt(System.getenv("FISHBOWL_FILE_UPDATE_SECONDS"));
    this.executor = Executors.newSingleThreadScheduledExecutor();
    this.executor.scheduleWithFixedDelay(this::saveFile, delay, delay, TimeUnit.SECONDS);
  }

  @Override
  public void onStart() {
    this.deleteDirectoryContents();
  }

  @OrderHandler("register_movement")
  public void registerMovement(JSONObject message) {
    String fish = message.getString("silhouette");
    Position lastPosition = this.fishbowl.getPosition(fish);
    Position newPosition = new Position(message.getInt("x"), message.getInt("y"));

    log.info("Fish {} moved! {} -> {}", fish, lastPosition, newPosition);
    this.fishbowl.setPosition(fish, newPosition);
    log.info("\n{}", this.fishbowl.render());
  }

  @ShutdownRequestHandler
  public ShutdownResponse onShutdownRequest(ShutdownRequest request) throws InterruptedException {
    this.executor.shutdown();
    boolean terminated = this.executor.awaitTermination(1, TimeUnit.SECONDS);

    if (!terminated) {
      if (request.isForced()) {
        this.executor.shutdownNow();
        return ShutdownResponse.ready();
      } else {
        return ShutdownResponse.cancel();
      }
    }
    return ShutdownResponse.ready();
  }

  private void deleteDirectoryContents() {
    try (Stream<Path> paths = Files.walk(DIRECTORY_PATH)) {
      paths.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void saveFile() {
    Path filePath = DIRECTORY_PATH.resolve("v" + this.fileVersion++ + ".txt");
    try {
      Files.writeString(filePath, this.fishbowl.render());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
