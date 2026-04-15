# CS3354 Team Surgeson – Chess Project

This repository contains both **Phase 1** and **Phase 2** of our chess project.

## Phase 2 Code

The **Phase 2** implementation is the Java Swing GUI version of the chess project.  
Phase 2 code is primarily located in these files and folders:

- `src/edu/txstate/chess/gui/`
- `src/edu/txstate/chess/board/`
- `src/edu/txstate/chess/pieces/`
- `src/edu/txstate/chess/Game.java`
- `src/edu/txstate/chess/Player.java`
- `src/edu/txstate/chess/util/`

Phase 2 features include:

- 8x8 Swing chessboard GUI
- Graphical display of all chess pieces
- Mouse-based piece movement
- Piece capturing
- Endgame popup notification
- Menu bar with **New Game**, **Save Game**, and **Load Game**
- Settings window for board customization

## How to Build and Run

### Requirements
- Java JDK 21 or compatible

### Compile
From the project root:

```bat
javac -d out src/edu/txstate/chess/board/*.java src/edu/txstate/chess/pieces/*.java src/edu/txstate/chess/util/*.java src/edu/txstate/chess/gui/*.java src/edu/txstate/chess/Game.java src/edu/txstate/chess/Player.java
```

### Run
```bat
java -cp out edu.txstate.chess.Game
```

Windows users can also use the full JDK path if needed, for exa