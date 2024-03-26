package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import exercise.exceptions.InvalidSudokuException;
import exercise.model.SudokuBoard;
import exercise.solver.BacktrackingSudokuSolver;

public class SudokuBoardTest {

    @Test
    void testFillBoardCorrectness() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        try {
            sudokuBoard.solveGame();
        } catch (InvalidSudokuException e) {
            e.printStackTrace();
            fail("solveGame() threw an InvalidSudokuException");
        } finally {
            assertTrue(sudokuBoard.isValidSudoku());
        }
    }

    @RepeatedTest(50)
    void testFillBoardSubsequentCalls() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());

        try {
            sudokuBoard1.solveGame();
            sudokuBoard2.solveGame();
        } catch (InvalidSudokuException e) {
            e.printStackTrace();
            fail("solveGame() threw an InvalidSudokuException");
        }

        assertTrue(sudokuBoard1.isValidSudoku());
        assertTrue(sudokuBoard2.isValidSudoku());

        assertFalse(checkSameLayout(sudokuBoard1.getBoard(),
                sudokuBoard2.getBoard()));
    }

    private boolean checkSameLayout(int[][] board1, int[][] board2) {
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[i].length; j++) {
                if (board1[i][j] != board2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @ParameterizedTest
    @MethodSource("provideInitiallyCorrectBoards")
    void testFillBoardWithInitiallyCorrectBoards(int[][] initialBoard) {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        for (int y = 0; y < initialBoard.length; y++) {
            for (int x = 0; x < initialBoard[y].length; x++) {
                sudokuBoard.set(y, x, initialBoard[y][x]);
            }
        }

        assertTrue(sudokuBoard.isValidSudoku());
    }

    @ParameterizedTest
    @MethodSource("provideInitiallyWrongBoards")
    void testFillBoardWithInitiallyWrongBoards(int[][] initialBoard) {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        for (int y = 0; y < initialBoard.length; y++) {
            for (int x = 0; x < initialBoard[y].length; x++) {
                sudokuBoard.set(y, x, initialBoard[y][x]);
            }
        }

        assertFalse(sudokuBoard.isValidSudoku());
    }

    // Method to provide the test data (initial correct boards)
    private static int[][][] provideInitiallyCorrectBoards() {
        return new int[][][] {
                {
                        { 3, 2, 7, 6, 5, 1, 4, 8, 9 },
                        { 5, 6, 9, 8, 7, 4, 3, 1, 2 },
                        { 1, 4, 8, 2, 3, 9, 6, 7, 5 },
                        { 7, 3, 6, 5, 4, 2, 8, 9, 1 },
                        { 2, 5, 4, 1, 9, 8, 7, 6, 3 },
                        { 9, 8, 1, 3, 6, 7, 2, 5, 4 },
                        { 8, 9, 2, 4, 1, 6, 5, 3, 7 },
                        { 6, 7, 3, 9, 2, 5, 1, 4, 8 },
                        { 4, 1, 5, 7, 8, 3, 9, 2, 6 }
                },
                {
                        { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                        { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
                        { 4, 5, 6, 7, 8, 9, 1, 2, 3 },
                        { 3, 1, 2, 8, 4, 5, 9, 6, 7 },
                        { 6, 9, 7, 3, 1, 2, 8, 4, 5 },
                        { 8, 4, 5, 6, 9, 7, 3, 1, 2 },
                        { 2, 3, 1, 5, 7, 4, 6, 9, 8 },
                        { 9, 6, 8, 2, 3, 1, 5, 7, 4 },
                        { 5, 7, 4, 9, 6, 8, 2, 3, 1 }
                },
                {
                        { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                        { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
                        { 4, 5, 6, 9, 8, 7, 1, 2, 3 },
                        { 3, 1, 2, 8, 4, 5, 9, 6, 7 },
                        { 6, 9, 7, 3, 1, 2, 8, 4, 5 },
                        { 8, 4, 5, 7, 6, 9, 3, 1, 2 },
                        { 2, 3, 1, 5, 7, 4, 6, 9, 8 },
                        { 9, 6, 8, 2, 3, 1, 5, 7, 4 },
                        { 5, 7, 4, 6, 9, 8, 2, 3, 1 }
                },
                {
                        { 2, 4, 6, 7, 8, 5, 3, 1, 9 },
                        { 1, 3, 5, 4, 6, 9, 7, 8, 2 },
                        { 7, 8, 9, 2, 3, 1, 4, 6, 5 },
                        { 4, 5, 3, 6, 2, 7, 1, 9, 8 },
                        { 8, 1, 2, 9, 5, 4, 6, 3, 7 },
                        { 6, 9, 7, 3, 1, 8, 2, 5, 4 },
                        { 3, 6, 4, 5, 9, 2, 8, 7, 1 },
                        { 5, 7, 1, 8, 4, 3, 9, 2, 6 },
                        { 9, 2, 8, 1, 7, 6, 5, 4, 3 }
                },
        };
    }

    // Method to provide the test data (initial wrong boards)
    private static int[][][] provideInitiallyWrongBoards() {
        return new int[][][] {
                {
                        { 3, 2, 7, 6, 5, 1, 4, 8, 9 },
                        { 5, 6, 9, 8, 7, 4, 3, 1, 2 },
                        { 1, 4, 8, 2, 3, 9, 6, 7, 5 },
                        { 7, 3, 6, 5, 4, 2, 8, 9, 1 },
                        { 2, 5, 4, 1, 8, 8, 7, 6, 3 },
                        { 9, 8, 1, 3, 6, 7, 2, 5, 4 },
                        { 8, 9, 2, 4, 1, 6, 5, 3, 7 },
                        { 6, 7, 3, 9, 2, 5, 1, 4, 8 },
                        { 4, 1, 5, 7, 8, 3, 9, 2, 6 }
                },
                {
                        { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                        { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
                        { 4, 5, 9, 7, 8, 9, 1, 2, 3 },
                        { 3, 1, 2, 8, 4, 5, 9, 6, 7 },
                        { 6, 9, 7, 3, 1, 2, 8, 4, 5 },
                        { 8, 4, 5, 6, 9, 7, 3, 1, 2 },
                        { 2, 3, 1, 5, 7, 4, 6, 9, 8 },
                        { 9, 6, 8, 2, 3, 1, 5, 7, 4 },
                        { 5, 7, 4, 9, 6, 8, 2, 3, 1 }
                },
                {
                        { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                        { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
                        { 4, 5, 6, 9, 8, 7, 1, 2, 3 },
                        { 3, 1, 2, 8, 4, 5, 9, 6, 7 },
                        { 6, 9, 7, 3, 1, 2, 8, 4, 5 },
                        { 8, 4, 5, 7, 6, 9, 3, 1, 2 },
                        { 2, 3, 1, 5, 7, 4, 6, 9, 8 },
                        { 9, 6, 8, 2, 3, 1, 5, 7, 4 },
                        { 5, 8, 4, 6, 9, 8, 2, 3, 1 }
                },
                {
                        { 2, 4, 6, 7, 8, 5, 3, 1, 9 },
                        { 1, 2, 5, 4, 6, 9, 7, 8, 2 },
                        { 7, 8, 9, 2, 3, 1, 4, 6, 5 },
                        { 4, 5, 3, 6, 2, 7, 1, 9, 8 },
                        { 8, 1, 2, 9, 5, 4, 6, 3, 7 },
                        { 6, 9, 7, 3, 1, 8, 2, 5, 4 },
                        { 3, 6, 4, 5, 9, 2, 8, 7, 1 },
                        { 5, 7, 1, 8, 4, 3, 9, 5, 6 },
                        { 9, 2, 8, 1, 7, 6, 5, 4, 3 }
                },
        };
    }
}
