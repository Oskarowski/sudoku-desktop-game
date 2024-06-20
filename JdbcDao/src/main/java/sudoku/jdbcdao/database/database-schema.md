```mermaid
erDiagram
    SUDOKU_BOARD {
        int id PK
        string name
    }
    SUDOKU_FIELD {
        int id PK
        int board_id FK
        int row
        int column
        int value
    }

    SUDOKU_FIELD }|--|| SUDOKU_BOARD : board_id
```