# SoftwareConstructionProject

A JavaFX-based board game project built for HBV202G: Software Design and Construction.

## Features

- JavaFX-based GUI with dynamic player movement
- Game logic separated from UI (MVC-style architecture)
- Use of JavaFX property binding for real-time updates
- Basic dice, turn-taking, and snake/ladder behavior
- Two-player support

##  Design and Architecture

Written in Java 21 with JavaFX UI

- Business logic is in the vinnsla package
- UI is handled in SlangaController and SlangaApplication
- Fully documented class structure and explanation of design pattern use

### UML Class Diagram

See [Design Documentation](docs/design.md) for class structure and design decisions.

### Design Patterns

**Observer Pattern:**

In this project, the Observer design pattern is used to handle communication between the business logic (Leikur) and the user interface (SlangaController).
- Leikur acts as the Subject. It maintains a list of observers and notifies them when important game events occur, such as when the current player changes.
- LeikurObserver is an interface defined in the vinnsla package. It declares a method onCurrentPlayerChanged(Leikmadur newPlayer) that observers must implement.
- SlangaController implements the LeikurObserver interface. It updates the JavaFX UI automatically when notified of changes by Leikur.


##  Maven commands

- `mvn clean` deletes the target/ directory where Maven stores compiled classes.
- `mvn compile` compiles all implementation classes.
- `mvn test` executes any JUnit tests.
- `mvn exec:java` runs your Java program
- `mvn javafx:run` runs javafx application

## License
This project is licensed under the [MIT License](./LICENSE).

