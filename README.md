# CS3354 Team Surgeson – Console Chess (Phase 1)

## How to build and run

Requirements: Java JDK 21 (or compatible).

From the project root:

```bash
javac -d out src/edu/txstate/chess/board/*.java src/edu/txstate/chess/pieces/*.java src/edu/txstate/chess/util/*.java src/edu/txstate/chess/Game.java src/edu/txstate/chess/Player.java
java -cp out edu.txstate.chess.Game
```

(Windows users can also use the full JDK path if needed, e.g. `"C:\Program Files\Java\jdk-21.0.10\bin\javac.exe"`.)

## How to play

- The board prints with files A–H and ranks 1–8.
- White uses symbols `wK`, `wQ`, `wR`, `wB`, `wN`, `wp`; black uses `bK`, `bQ`, `bR`, `bB`, `bN`, `bp`.
- Empty squares are shown as `##`.
- On your turn, enter moves like `E2 E4` (case-insensitive).
- Type `quit` to end the game.

## Implemented (Phase 1)

- 8x8 board with standard chess initial setup.
- Console-based board rendering with coordinates and `##` for empty squares.
- Command-line game loop showing whose turn it is.
- Input parsing and format validation for moves like `E2 E4`.
- Basic move legality:
  - Must move your own color.
  - Cannot capture your own pieces.
  - Pawns move forward (one or two squares from the start) and capture diagonally.
  - Rooks move horizontally/vertically without jumping through pieces.

## Not implemented yet

- Movement rules for bishops, knights, queens, and kings.
- Check, checkmate, and stalemate detection.
- Castling, en passant, and pawn promotion.