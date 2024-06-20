# Sudoku Java Desktop Application

## Table of Contents

- [Sudoku Java Desktop Application](#sudoku-java-desktop-application)
  - [Table of Contents](#table-of-contents)
  - [About the Project](#about-the-project)
  - [Dependencies](#dependencies)
  - [Installation](#installation)
  - [How to Play](#how-to-play)
  - [Features](#features)
  - [In-Game Screenshots](#in-game-screenshots)
    - [Main Menu](#main-menu)
    - [Gameplay](#gameplay)
    - [Save Game](#save-game)
  - [Internationalization](#internationalization)
  - [Database Schema](#database-schema)

## About the Project

<!-- TODO -->

## Dependencies

This project requires the following dependencies:

- Java >21: Minimum version required for running the application.
- JDK >21: Minimum version required for running the application.
- JavaFX >22: for building the graphical user interface.
- Maven >3.9: for project management and building.
- JOOQ: for database interactions.
- SQLite: as the database to store Sudoku boards.
- SLF4J and Logback: for logging purposes.

## Installation

To set up and run this project locally, follow these steps:

1. **Clone the repository**:
    ```sh
    git clone https://github.com/Oskarowski/sudoku-desktop-game.git
    cd sudoku-desktop-game
    ```

2. **Build the project**:
    ```sh
    mvn install
    ```

3. **Run the application**:
    ```sh
    cd ./View | mvn javafx:run
    ```

## How to Play

1. **Start the Game**:
    - Launch the application, choose difficulty and click on "Start Game". Or load previous game from DB or filesystem.

2. **Save Game**:
    - Click on the "Save Game" button to save your current Sudoku board to a file.
    - You can also save your game to a database by clicking the "Save to DB" button.

3. **Load Game**:
    - To load a game from a file, click on the "Load Game" button.
    - To load a game from the database, click on the "Load from DB" button and select the desired saved game from the list.

4. **Solve Sudoku**:
    - Fill in the cells with numbers from 1 to 9. Each number can appear only once per row, column, and 3x3 grid.

## Features

- **Save and Load Game**: Save your game progress to a file or database and load it anytime.
- **Database Integration**: Store and retrieve Sudoku boards from DB (SQLite).
- **System Filebase Integration**: Store and retrieve Sudoku boards using the system's filesystem (object serialization).
- **Internationalization**: Supports multiple languages.
- **User-friendly Interface**: Intuitive and easy-to-use GUI built with JavaFX.
- **Difficulty Levels**: Choose from 3 different levels of difficulty.
- **Logging**: Comprehensive logging for debugging.

## In-Game Screenshots

### Main Menu
![Main Menu](path/to/main/menu/screenshot.png)

### Gameplay
![Gameplay](path/to/gameplay/screenshot.png)

### Save Game
![Save Game](path/to/save/game/screenshot.png)

## Internationalization

This application supports multiple languages. The localization is handled using `ResourceBundle` to load the appropriate language properties files based on the user's locale.

## Database Schema
The Sudoku game uses a SQLite database to store game boards and fields.

For an ER diagram illustrating the database schema, please refer to [ER Diagram](JdbcDao/src/main/java/sudoku/jdbcdao/database/database-schema.md).