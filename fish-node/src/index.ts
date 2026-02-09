import botica from "botica-lib-node";
import logger from "./logger.js";
import { randomInt } from "node:crypto";

const MAX_INDEX = 9;
const bot = await botica();

const silhouette = process.env.FISH_SILHOUETTE;
let x = parseInt(process.env.FISH_POSITION_X!);
let y = parseInt(process.env.FISH_POSITION_Y!);

bot.proactive(async () => {
  const previousX = x;
  const previousY = y;

  x += nextMovement(x);
  y += nextMovement(y);
  logger.info(`Moved! (${previousX},${previousY}) -> (${x},${y})`);

  await bot.publishOrder("fishbowl_manager", "register_movement", {
    silhouette,
    x,
    y,
  });
});

await bot.start();

function nextMovement(currentPosition: number) {
  const minMovement = currentPosition === 0 ? 0 : -1;
  const maxMovement = currentPosition === MAX_INDEX - 1 ? 1 : 2;
  return randomInt(minMovement, maxMovement);
}
