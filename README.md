# Team Surgeson
# CS3354 Chess Project - Phase 3

Integrated Java chess application with Swing GUI and rules-based backend.

## How to Compile
javac -d out src/edu/txstate/chess/*.java src/edu/txstate/chess/board/*.java src/edu/txstate/chess/gui/*.java src/edu/txstate/chess/pieces/*.java src/edu/txstate/chess/util/*.java

## How to Run
java -cp out edu.txstate.chess.Game

## Features
- Full move validation (pawns, rooks, knights, bishops, queens, kings)
- Check, Checkmate, and Stalemate detection
- Castling and En Passant support
- Save/Load game state
- Board customization (colors, size)
