```mermaid
erDiagram
    SUDOKU_BOARDS {
        int id PK
        string name
    }
    SUDOKU_FIELDS {
        int id PK
        int board_id FK
        int row
        int column
        int value
    }

    SUDOKU_FIELDS }|--|| SUDOKU_BOARDS : board_id
```