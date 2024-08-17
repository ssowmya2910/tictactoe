import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGame {
    int windowWidth = 700;
    int windowHeight =650; 
    JFrame gameFrame = new JFrame("Tic-Tac-Toe");
    JLabel statusLabel = new JLabel();
    JPanel headerPanel = new JPanel();
    JPanel gridPanel = new JPanel();
    JPanel controlPanel = new JPanel();
    JButton resetButton = new JButton("Reset");
    JButton tryAgainButton = new JButton("Try Again");

    JButton[][] gameBoard = new JButton[3][3];
    String player1 = "X";
    String player2 = "O";
    String currentPlayer = player1;

    boolean isGameOver = false;
    int moveCount = 0;

    TicTacToeGame() {
        gameFrame.setVisible(true);
        gameFrame.setSize(windowWidth, windowHeight);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

        statusLabel.setBackground(Color.white);
        statusLabel.setForeground(Color.darkGray);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 50));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setText("Tic-Tac-Toe");
        statusLabel.setOpaque(true);

        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(statusLabel);
        gameFrame.add(headerPanel, BorderLayout.NORTH);

        gridPanel.setLayout(new GridLayout(3, 3));
        gridPanel.setBackground(Color.white);
        gameFrame.add(gridPanel, BorderLayout.CENTER);

        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(resetButton, BorderLayout.WEST);
        controlPanel.add(tryAgainButton, BorderLayout.EAST);
        gameFrame.add(controlPanel, BorderLayout.SOUTH);

        resetButton.setFont(new Font("Arial", Font.BOLD, 30));
        resetButton.setFocusable(false);
        resetButton.setBackground(Color.white);
        resetButton.setForeground(Color.black);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGameBoard();
            }
        });

        tryAgainButton.setFont(new Font("Arial", Font.BOLD, 30));
        tryAgainButton.setFocusable(false);
        tryAgainButton.setBackground(Color.white);
        tryAgainButton.setForeground(Color.black);
        tryAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGameBoard();
            }
        });
        tryAgainButton.setVisible(false);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton cell = new JButton();
                gameBoard[row][col] = cell;
                gridPanel.add(cell);

                cell.setBackground(Color.white);
                cell.setForeground(Color.darkGray);
                cell.setFont(new Font("Arial", Font.BOLD, 120));
                cell.setFocusable(false);

                cell.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (isGameOver) return;
                        JButton clickedCell = (JButton) e.getSource();
                        if (clickedCell.getText().isEmpty()) {
                            clickedCell.setText(currentPlayer);
                            clickedCell.setForeground(currentPlayer.equals(player1) ? Color.green : Color.red);
                            moveCount++;
                            evaluateWinner();
                            if (!isGameOver) {
                                currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
                                statusLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }
    }

    void evaluateWinner() {
        for (int row = 0; row < 3; row++) {
            if (gameBoard[row][0].getText().isEmpty()) continue;

            if (gameBoard[row][0].getText().equals(gameBoard[row][1].getText()) &&
                gameBoard[row][1].getText().equals(gameBoard[row][2].getText())) {
                for (int i = 0; i < 3; i++) {
                    highlightWinner(gameBoard[row][i]);
                }
                isGameOver = true;
                return;
            }
        }
        for (int col = 0; col < 3; col++) {
            if (gameBoard[0][col].getText().isEmpty()) continue;

            if (gameBoard[0][col].getText().equals(gameBoard[1][col].getText()) &&
                gameBoard[1][col].getText().equals(gameBoard[2][col].getText())) {
                for (int i = 0; i < 3; i++) {
                    highlightWinner(gameBoard[i][col]);
                }
                isGameOver = true;
                return;
            }
        }
        if (gameBoard[0][0].getText().equals(gameBoard[1][1].getText()) &&
            gameBoard[1][1].getText().equals(gameBoard[2][2].getText()) &&
            !gameBoard[0][0].getText().isEmpty()) {
            for (int i = 0; i < 3; i++) {
                highlightWinner(gameBoard[i][i]);
            }
            isGameOver = true;
            return;
        }
        if (gameBoard[0][2].getText().equals(gameBoard[1][1].getText()) &&
            gameBoard[1][1].getText().equals(gameBoard[2][0].getText()) &&
            !gameBoard[0][2].getText().isEmpty()) {
            highlightWinner(gameBoard[0][2]);
            highlightWinner(gameBoard[1][1]);
            highlightWinner(gameBoard[2][0]);
            isGameOver = true;
            return;
        }

        if (moveCount == 9) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    indicateTie(gameBoard[row][col]);
                }
            }
            isGameOver = true;
            tryAgainButton.setVisible(true);
        }
    }

    void highlightWinner(JButton cell) {
        cell.setForeground(Color.green);
        cell.setBackground(Color.darkGray);
        statusLabel.setText(currentPlayer + " wins!");
        tryAgainButton.setVisible(true);
    }

    void indicateTie(JButton cell) {
        cell.setForeground(Color.orange);
        cell.setBackground(Color.darkGray);
        statusLabel.setText("It's a tie!");
    }

    void resetGameBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoard[row][col].setText("");
                gameBoard[row][col].setForeground(Color.white);
                gameBoard[row][col].setBackground(Color.white);
            }
        }
        isGameOver = false;
        moveCount = 0;
        currentPlayer = player1;
        statusLabel.setText("Tic-Tac-Toe");
        tryAgainButton.setVisible(false);
    }

    public static void main(String[] args) {
        new TicTacToeGame();
    }
}