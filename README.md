# The Botica fishbowl: an example project

Welcome to the fishbowl example project! This repository showcases a simple yet powerful example of
how to use Botica to create and manage an automated infrastructure with containerized bots. The
example simulates a 9x9 fishbowl where multiple fish move around, and a manager bot keeps track of
their positions.

## Project Overview

This Botica infrastructure demonstrates nearly every aspect of Botica's capabilities, including
support for multi-language bots, proactive and reactive behavior, and file system integration. The
environment consists of two main types of agents: fish and a manager.

The **fish** are proactive bots that periodically send their current position within the fishbowl.
Each fish may move to a new position with each update, simulating random movement within the 9x9
grid. This project includes three fish bots: two are implemented in Java, and one is implemented in
Node.js, although both of the implementation behave identically.

The **manager** is a reactive bot, written in Java, that listens for updates from the fish bots. It
subscribes to the fish movement keys and the `move_fish` order. Whenever a fish bot publishes its
new position, the manager reads this update, renders the current state of the fishbowl in the logs,
and periodically saves the fishbowl's state to a file in the `fishbowl/` directory. This process
continues, with the manager creating a new file every few seconds, such as `v1.txt`, `v2.txt`, and
so on.

This example project illustrates several important features of Botica:

- **Multi-language support**: the fish bots are implemented in both Java and Node.js, demonstrating
  Botica's ability to manage and run bots written in different programming languages within the same
  infrastructure.
- **Proactive and reactive bots**: the fish bots are proactive, performing actions on a schedule,
  while the manager bot is reactive, responding to events triggered by the fish bots.
- **File system integration**: the manager bot periodically writes the fishbowl's state to files,
  illustrating how Botica bots can interact with the file system, a critical feature for many
  real-world automation scenarios.

## Configuration

The behavior and interaction of the bots within this infrastructure are defined in the `config.yml`
file located in this repository. This configuration file sets up the bot types, instances, and their
interactions.

- **View the configuration**: [config.yml](./config.yml)

## Source code

The source code for each bot is hosted in their respective repositories:

- **Java fish bot**:
  [botica-bot-fishbowl-fish-java](https://github.com/isa-group/botica-bot-fishbowl-fish-java)
- **Node.js fish bot**:
  [botica-bot-fishbowl-fish-node](https://github.com/isa-group/botica-bot-fishbowl-fish-node)
- **Manager bot**:
  [botica-bot-fishbowl-manager](https://github.com/isa-group/botica-bot-fishbowl-manager)

## Getting started

To get started with this example project:

1. **Clone the repository**: clone this repository to your local machine.
2. **Run the infrastructure**: use the provided script in this repository to download the latest
   version of the Botica Director and start the environment based on the configuration
   in `config.yml`.
3. **Monitor the output**: watch the logs to see the fishbowl being rendered as the fish bots send
   their position updates. The saved fishbowl states can be found in the `fishbowl/` directory.

Feel free to explore the configuration and the source code in the linked repositories to better
understand how this setup works and how you can adapt it to your own use cases.
