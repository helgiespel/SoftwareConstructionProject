# SoftwareConstructionProject

A JavaFX-based board game project built for HBV202G: Software Design and Construction.

## Features

- JavaFX-based GUI with dynamic player movement
- Game logic separated from UI (MVC-style architecture)
- Use of JavaFX property binding for real-time updates
- Basic dice, turn-taking, and snake/ladder behavior
- Two-player support

##  Design and Architecture

Written in Java 17 with JavaFX UI

-Business logic is in the vinnsla package
-UI is handled in SlangaController and SlangaApplication
-Fully documented class structure and explanation of design pattern use

### UML Class Diagram

See [Design Documentation](docs/design.md) for class structure and design decisions.

### Design Patterns


##  How to Run

mvn javafx:run 
OR
./run.sh

run tests : mvn test

## License
This project is licensed under the [MIT License](./LICENSE).

