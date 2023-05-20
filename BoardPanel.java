package Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {

    private static final long serialVersionUID = -1102632585936750607L;
    public static final int COL_COUNT = 25;
    public static final int ROW_COUNT = 25;
    public static final int TILE_SIZE = 20;
    private static final int EYE_LARGE_INSET = TILE_SIZE / 3;
    private static final int EYE_SMALL_INSET = TILE_SIZE / 6;
    private static final int EYE_LENGTH = TILE_SIZE / 5;
    private static final Font FONT = new Font("Tahoma", Font.BOLD, 25);
    private SnakeGame game;
    private TileType[] tiles;

    public BoardPanel(SnakeGame game) {
        this.game = game;
        this.tiles = new TileType[ROW_COUNT * COL_COUNT];

        setPreferredSize(new Dimension(COL_COUNT * TILE_SIZE, ROW_COUNT * TILE_SIZE));
        setBackground(Color.BLACK);
    }

    //Clear ban choi hien tai
    public void clearBoard() {
        for(int i = 0; i < tiles.length; i++) {
            tiles[i] = null;
        }
    }

    //Thay doi tile
    public void setTile(Point point, TileType type) {
        setTile(point.x, point.y, type);
    }
    public void setTile(int x, int y, TileType type) {
        tiles[y * ROW_COUNT + x] = type;
    }

    //Lay tile
    public TileType getTile(int x, int y) {
        return tiles[y * ROW_COUNT + x];
    }

    //Ve boardgame
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int x = 0; x < COL_COUNT; x++) {
            for(int y = 0; y < ROW_COUNT; y++) {
                TileType type = getTile(x, y);
                if(type != null) {
                    drawTile(x * TILE_SIZE, y * TILE_SIZE, type, g);
                }
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        for(int x = 0; x < COL_COUNT; x++) {
            for(int y = 0; y < ROW_COUNT; y++) {
                g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, getHeight());
                g.drawLine(0, y * TILE_SIZE, getWidth(), y * TILE_SIZE);
            }
        }
        if(game.isGameOver() || game.isNewGame() || game.isPaused() || game.isViewHigscores()) {
            g.setColor(Color.WHITE);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            String WelcomeMessage;
            String smallWelcomeMessage;
            String smallMessage1;
            String smallMessage2;
            String smallMessage3;
            String smallMessage4;
            String smallMessage5;


            String GameOverMessage;
            String smallGameOverMessage;

            String PauseMessage;
            String smallPauseMessage;

            String highscoreMessage;
            String highscoreMessage1;
            String highscoreMessage2;
            String highscoreMessage3;
            String highscoreMessage4;


            //Man hinh Menu chinh
            if(game.isNewGame()) {
                game.clearDifficulty();
                game.clearName();
                WelcomeMessage = "Welcome to Snake Game!";
                smallWelcomeMessage = "Choose Difficulty:";
                smallMessage1 = "1 - Easy";
                smallMessage2 = "2 - Medium";
                smallMessage3 = "3 - Hard";
                smallMessage4 = "4 - View Highscores";
                smallMessage5 = "Press ESC to Exit Game";

                g.setFont(FONT);

                g.drawString(WelcomeMessage, centerX - g.getFontMetrics().stringWidth(WelcomeMessage) / 2, centerY - 200);
                g.drawString(smallWelcomeMessage, centerX - g.getFontMetrics().stringWidth(smallWelcomeMessage) / 2, centerY - 100);
                g.drawString(smallMessage1, centerX - (g.getFontMetrics().stringWidth(smallMessage1) / 2), centerY - 50);
                g.drawString(smallMessage2, centerX - (g.getFontMetrics().stringWidth(smallMessage2) / 2), centerY + 0);
                g.drawString(smallMessage3, centerX - (g.getFontMetrics().stringWidth(smallMessage3) / 2), centerY + 50);
                g.drawString(smallMessage4, centerX - (g.getFontMetrics().stringWidth(smallMessage4)) / 2, centerY + 100);
                g.drawString(smallMessage5, centerX - (g.getFontMetrics().stringWidth(smallMessage5)) / 2, centerY + 200);

            }

            //Man hinh gameover
            else if(game.isGameOver()) {
                GameOverMessage= "Game Over!";
                smallGameOverMessage = "Press Enter to Back to Main Menu";
                g.setFont(FONT);
                g.drawString(GameOverMessage, centerX - (g.getFontMetrics().stringWidth(GameOverMessage)) / 2, centerY - 50);
                g.drawString(smallGameOverMessage, centerX - (g.getFontMetrics().stringWidth(smallGameOverMessage)) / 2, centerY + 50);
            }

            //Man hinh pause game
            else if(game.isPaused()) {
                PauseMessage = "Paused";
                smallPauseMessage = "Press P to Resume";
                g.setFont(FONT);

                g.drawString(PauseMessage, centerX - (g.getFontMetrics().stringWidth(PauseMessage)) / 2, centerY - 50);
                g.drawString(smallPauseMessage, centerX - (g.getFontMetrics().stringWidth(smallPauseMessage)) / 2, centerY + 50);

            }

            //Man hinh menu chon xem thanh tich nguoi choi
            else if(game.isViewHigscores()) {
                highscoreMessage = "Choose what highscores u want to see:";
                highscoreMessage1 = "1 - Easy";
                highscoreMessage2 = "2 - Medium";
                highscoreMessage3 = "3 - Hard";
                highscoreMessage4 = "ESC to return to main menu";

                g.setFont(FONT);
                g.drawString(highscoreMessage, centerX - (g.getFontMetrics().stringWidth(highscoreMessage) / 2), centerY - 50);
                g.drawString(highscoreMessage1, centerX - (g.getFontMetrics().stringWidth(highscoreMessage1) / 2), centerY + 0);
                g.drawString(highscoreMessage2, centerX - (g.getFontMetrics().stringWidth(highscoreMessage2) / 2), centerY + 50);
                g.drawString(highscoreMessage3, centerX - (g.getFontMetrics().stringWidth(highscoreMessage3)) / 2, centerY + 100);
                g.drawString(highscoreMessage4, centerX - (g.getFontMetrics().stringWidth(highscoreMessage4)) / 2, centerY + 200);

            }
        }
    }

    //Ve tile
    private void drawTile(int x, int y, TileType type, Graphics g) {

        switch(type) {
        	//Thuc an
            case Fruit:
                g.setColor(Color.RED);
                g.fillOval(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4);
                break;
            //Vat can(Tuong)
            case Wall:
                g.setColor(Color.YELLOW);
                g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                break;
            //Than ran
            case SnakeBody:
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(x + 2, y + 2, TILE_SIZE - 2 , TILE_SIZE - 2);
                break;
            //Dau ran
            case SnakeHead:
                g.setColor(Color.GREEN);
                g.fillOval(x + 2, y + 2, TILE_SIZE - 2 , TILE_SIZE - 2);
                g.setColor(Color.BLACK);

                switch(game.getDirection()) {
                	//Di len
                    case Up: {
                        int baseY = y + EYE_SMALL_INSET;
                        g.drawLine(x + EYE_LARGE_INSET, baseY, x + EYE_LARGE_INSET, baseY + EYE_LENGTH);
                        g.drawLine(x + TILE_SIZE - EYE_LARGE_INSET, baseY, x + TILE_SIZE - EYE_LARGE_INSET, baseY + EYE_LENGTH);
                        break;
                    }
                    //Di xuong
                    case Down: {
                        int baseY = y + TILE_SIZE - EYE_SMALL_INSET;
                        g.drawLine(x + EYE_LARGE_INSET, baseY, x + EYE_LARGE_INSET, baseY - EYE_LENGTH);
                        g.drawLine(x + TILE_SIZE - EYE_LARGE_INSET, baseY, x + TILE_SIZE - EYE_LARGE_INSET, baseY - EYE_LENGTH);
                        break;
                    }
                    //Di sang trai
                    case Left: {
                        int baseX = x + EYE_SMALL_INSET;
                        g.drawLine(baseX, y + EYE_LARGE_INSET, baseX + EYE_LENGTH, y + EYE_LARGE_INSET);
                        g.drawLine(baseX, y + TILE_SIZE - EYE_LARGE_INSET, baseX + EYE_LENGTH, y + TILE_SIZE - EYE_LARGE_INSET);
                        break;
                    }
                    //Di sang phai
                    case Right: {
                        int baseX = x + TILE_SIZE - EYE_SMALL_INSET;
                        g.drawLine(baseX, y + EYE_LARGE_INSET, baseX - EYE_LENGTH, y + EYE_LARGE_INSET);
                        g.drawLine(baseX, y + TILE_SIZE - EYE_LARGE_INSET, baseX - EYE_LENGTH, y + TILE_SIZE - EYE_LARGE_INSET);
                        break;
                    }

                }
                break;
        }
    }

}
