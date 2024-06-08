CREATE TABLE IF NOT EXISTS SUDOKU_BOARD (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS SUDOKU_FIELD (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    board_id INTEGER,
    row INTEGER,
    column INTEGER,
    value INTEGER,
    FOREIGN KEY (board_id) REFERENCES SUDOKU_BOARD (id)
);
