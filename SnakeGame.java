package Snake;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.Date;

import Highscores.EasyHighScores;
import Highscores.HardHighScores;
import Highscores.MediumHighScores;
import Sound.Music;

import javax.swing.*;

public class SnakeGame extends JFrame {
	private static final long serialVersionUID = 6678292058307426314L;
	private static final long FRAME_TIME = 1000L / 50L;
	private static final int MIN_SNAKE_LENGTH = 5;
	private static final int MAX_DIRECTIONS = 3;
	private BoardPanel board;
	private SidePanel side;
	private Random random;
	private Clock logicTimer;
	private boolean isNewGame;
	private boolean isGameOver;
	private boolean isPaused;
	private boolean viewHigscores;
	private LinkedList<Point> snake;
	private LinkedList<Direction> directions;
	private int score;
	private int fruitsEaten;
	private int nextFruitScore;
	private Music music;
	private String difficulty;
	private String name;
	private EasyHighScores easy;
	private MediumHighScores medium;
	private HardHighScores hard;
	private Date date;
	private JFrame frame;

	public SnakeGame() {
		super("Snake");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		//Boargame
		this.board = new BoardPanel(this);
		//Hien thi thong tin thanh tich cá nhân
		this.side = new SidePanel(this);
		//Am thanh
		this.music = new Music();

		add(board, BorderLayout.CENTER);
		add(side, BorderLayout.EAST);

		//Xu ly su kien ban phim
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				//Di chuyen di len
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					if (!isPaused && !isGameOver) {
						if (directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if (last != Direction.Down && last != Direction.Up) {
								directions.addLast(Direction.Up);
							}
						}
					}
					break;
				//Di chuyen di xuong
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					if (!isPaused && !isGameOver) {
						if (directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if (last != Direction.Up && last != Direction.Down) {
								directions.addLast(Direction.Down);
							}
						}
					}
					break;
				//Di chuyen sang trai
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					if (!isPaused && !isGameOver) {
						if (directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if (last != Direction.Right && last != Direction.Left) {
								directions.addLast(Direction.Left);
							}
						}
					}
					break;
				//Di chuyen sang phai
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					if (!isPaused && !isGameOver) {
						if (directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if (last != Direction.Left && last != Direction.Right) {
								directions.addLast(Direction.Right);
							}
						}
					}
					break;
				//Pause game
				case KeyEvent.VK_P:
					if (!isGameOver) {
						isPaused = !isPaused;
						//thời gian sẽ dừng lại
						logicTimer.setPaused(isPaused);
						//khi dừng game, nhạc sẽ dừng lại
						music.pauseMusic();
					}
					break;
				//Phim 1
				case KeyEvent.VK_1:
					//Kiêm tra có đang trong màn hình menu thành tích hay không
					if (viewHigscores) {
						try {
							File file = new File("EasyHighScores.txt");

							if (!Desktop.isDesktopSupported()) {
								JOptionPane.showMessageDialog(frame, "File Not Found!");
							}
							//Hiện file thành tích easy bằng Desktop
							Desktop desktop = Desktop.getDesktop();
							//Nếu file tồn tại sẽ xuất hiện desktop xem thành tích
							if (file.exists()) {
								desktop.open(file);
								file.setReadOnly();
							}

						} catch (Exception e1) {
							e1.printStackTrace();
						}

						break;
					}
					//Kiểm tra có đang trong màn hình Menu chính hay không
					if (isNewGame) {
						name = JOptionPane.showInputDialog(frame, "Enter Your Name: ", JOptionPane.OK_CANCEL_OPTION);
						//nếu tên trống sẽ không thể vào game
						if (name == null || name.isBlank()) {
							break;
						} else {
							newEasyGame();
						}
					}
					break;
				//Phim 2
				case KeyEvent.VK_2:
					//Kiêm tra có đang trong màn hình menu thành tích hay không
					if (viewHigscores) {
						try {
							File file = new File("MediumHighScores.txt");
							if (!Desktop.isDesktopSupported()) {
								JOptionPane.showMessageDialog(frame, "File Not Found!");
								break;
							}
							//Hiện file thành tích medium bằng Desktop
							Desktop desktop = Desktop.getDesktop();
							//Nếu file tồn tại sẽ xuất hiện desktop xem thành tích
							if (file.exists()) {
								desktop.open(file);
								file.setReadOnly();

							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						break;
					}
					//Kiểm tra có đang trong màn hình Menu chính hay không
					if (isNewGame) {
						name = JOptionPane.showInputDialog(frame, "Enter Your Name: ", JOptionPane.OK_CANCEL_OPTION);
						//nếu tên trống sẽ không thể vào game
						if (name == null || name.isBlank()) {
							break;
						} else {
							newMediumGame();
						}
					}
				//Phim 3
				case KeyEvent.VK_3:
					//Kiêm tra có đang trong màn hình menu thành tích hay không
					if (viewHigscores) {
						try {
							File file = new File("HardHighScores.txt");
							if (!Desktop.isDesktopSupported()) {
								JOptionPane.showMessageDialog(frame, "File Not Found!");
								break;
							}
							//Hiện file thành tích hard bằng Desktop
							Desktop desktop = Desktop.getDesktop();
							//Nếu file tồn tại sẽ xuất hiện desktop xem thành tích
							if (file.exists()) {
								desktop.open(file);
								file.setReadOnly();
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						break;
					}
					//Kiểm tra có đang trong màn hình Menu chính hay không
					if (isNewGame) {
						name = JOptionPane.showInputDialog(frame, "Enter Your Name: ", JOptionPane.OK_CANCEL_OPTION);
						//nếu tên trống sẽ không thể vào game
						if (name == null || name.isBlank()) {
							break;
						} else {
							newHardGame();
						}
					}
					break;
				//Phim 4: Xem thong tin diem so thanh tich nguoi choi
				case KeyEvent.VK_4:
					//Neu dang o man hinh chinh thi hien thi menu thanh tich
					if (isNewGame) {
						viewHigscores = true;
						if (viewHigscores) {
							isNewGame = false;
							isGameOver = false;
						}
					}
					break;
				//Enter
				case KeyEvent.VK_ENTER:
					//Nếu chưa thua game sẽ bỏ qua
					if (!isGameOver) {
						break;
					}
					//Nếu đã thua game sẽ trở về menu chính
					if (isGameOver) {
						isNewGame = true;
						board.clearBoard();
						score = 0;
						fruitsEaten = 0;
						nextFruitScore = 0;
					}
					break;
				//ESC
				case KeyEvent.VK_ESCAPE:
					//Nếu đang ở màn hình menu xem thành tích
					if (viewHigscores) {
						viewHigscores = false;
						isNewGame = true;
						break;
					}
					//Đang ở màn hình menu Chính
					if (isNewGame) {
						System.exit(0);
					}
					break;
				}
			}

		});

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void startGame() {
		this.random = new Random();
		this.snake = new LinkedList<>();
		this.directions = new LinkedList<>();
		this.logicTimer = new Clock(10.0f);
		this.isNewGame = true;

		logicTimer.setPaused(true);

		while (true) {
			long start = System.nanoTime();

			logicTimer.update();

			if (logicTimer.hasElapsedCycle()) {
				updateGame();
			}

			board.repaint();
			side.repaint();

			long delta = (System.nanoTime() - start) / 1000000L;
			if (delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateGame() {
		TileType collision = updateSnake();
		if (collision == TileType.Fruit) {
			fruitsEaten++;
			score += nextFruitScore;
			spawnFruit();
			music.playEatAppleSound();
		} else if (collision == TileType.SnakeBody) {
			isGameOver = true;
			logicTimer.setPaused(true);
			music.playGameOverSound();
			music.stopMusic();
		} else if (collision == TileType.Wall) {
			isGameOver = true;
			logicTimer.setPaused(true);
			music.playGameOverSound();
			music.stopMusic();
		} else if (nextFruitScore > 10) {
			nextFruitScore--;
		}
		//Khi thua game sẽ lưu kết quả thành tích vào file txt
		if (isGameOver) {
			if (getDifficulty() == "Easy") {
				File file = new File("EasyHighScores.txt");
				file.setWritable(true);
				file.setReadable(true);
				easy = new EasyHighScores();
				easy.write("Score: " + getScore() + " | " + "Fruits eaten: " + getFruitsEaten() + " | " + "Player: "
						+ getName() + " | " + "Date: " + getDate() + " | ");
				easy.sort();
				file.setReadOnly();
			} else if (getDifficulty() == "Medium") {
				File file = new File("MediumHighScore.txt");
				file.setWritable(true);
				file.setReadable(true);
				medium = new MediumHighScores();
				medium.write("Score: " + getScore() + " | " + "Fruits eaten: " + getFruitsEaten() + " | " + "Player: "
						+ getName() + " | " + "Date: " + getDate() + " | ");
				medium.sort();
				file.setReadOnly();
			} else if (getDifficulty() == "Hard") {
				File file = new File("HardHighScores.txt");
				file.setWritable(true);
				file.setReadable(true);
				hard = new HardHighScores();
				hard.write("Score: " + getScore() + " | " + "Fruits eaten: " + getFruitsEaten() + " | " + "Player: "
						+ getName() + " | " + "Date: " + getDate() + " | ");
				hard.sort();
				file.setReadOnly();
			}
		}
	}

	private TileType updateSnake() {

		Direction direction = directions.peekFirst();

		Point head = new Point(snake.peekFirst());
		//Huong di chuyen cua ran
		switch (direction) {
		case Up:
			head.y--;
			break;

		case Down:
			head.y++;
			break;

		case Left:
			head.x--;
			break;

		case Right:
			head.x++;
			break;
		}
		// Cho phep di xuyen matrix
		if (head.x < 0) {
			head.x = BoardPanel.COL_COUNT - 1;
		}
		if (head.x >= BoardPanel.COL_COUNT) {
			head.x = 0;
		}
		if (head.y < 0) {
			head.y = BoardPanel.ROW_COUNT - 1;
		}
		if (head.y >= BoardPanel.ROW_COUNT) {
			head.y = 0;
		}
		//Khong di xuyen matrix
//        if(head.x < 0 || head.x >= BoardPanel.COL_COUNT || head.y < 0 || head.y >= BoardPanel.ROW_COUNT) {
//            return TileType.SnakeBody; //Pretend we collided with our body.
//        }

		//Lay toa do cua dau ran
		TileType old = board.getTile(head.x, head.y);
		
		if (old != TileType.Fruit && snake.size() > MIN_SNAKE_LENGTH) {
			Point tail = snake.removeLast();
			board.setTile(tail, null);
			old = board.getTile(head.x, head.y);
		}

		if (old != TileType.SnakeBody) {
			board.setTile(snake.peekFirst(), TileType.SnakeBody);
			snake.push(head);
			board.setTile(head, TileType.SnakeHead);
			if (directions.size() > 1) {
				directions.poll();
			}
		}

		return old;
	}

	//Tao game de
	private void newEasyGame() {
		this.score = 0;
		this.fruitsEaten = 0;
		this.logicTimer = new Clock(15.0f);
		this.difficulty = "Easy";
		this.date = new Date();
		File file = new File("EasyHighScores.txt");
		file.setReadOnly();

		this.isNewGame = false;
		this.isGameOver = false;

		Point head = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2);

		snake.clear();
		snake.add(head);

		board.clearBoard();
		board.setTile(head, TileType.SnakeHead);

		directions.clear();
		directions.add(Direction.Up);

		logicTimer.reset();
		spawnFruit();
		music.playMusic();
	}

	//Game trung binh
	private void newMediumGame() {
		this.score = 0;
		this.fruitsEaten = 0;
		this.logicTimer = new Clock(20.0f);
		this.difficulty = "Medium";
		this.date = new Date();
		File file = new File("MediumHighScores.txt");
		file.setReadOnly();

		this.isNewGame = false;
		this.isGameOver = false;

		Point head = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2);

		snake.clear();
		snake.add(head);

		board.clearBoard();
		board.setTile(head, TileType.SnakeHead);

		directions.clear();
		directions.add(Direction.Up);

		logicTimer.reset();
		drawMediumLevelWalls();

		spawnFruit();
		music.playMusic();
	}

	//Gam kho
	private void newHardGame() {
		this.score = 0;
		this.fruitsEaten = 0;
		this.logicTimer = new Clock(25.0f);
		this.difficulty = "Hard";
		this.date = new Date();
		File file = new File("HardHighScores.txt");
		file.setReadOnly();

		this.isNewGame = false;
		this.isGameOver = false;

		Point head = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2);

		snake.clear();
		snake.add(head);

		board.clearBoard();
		board.setTile(head, TileType.SnakeHead);

		directions.clear();
		directions.add(Direction.Up);

		logicTimer.reset();
		drawHardLevelWalls();

		spawnFruit();
		music.playMusic();
	}

	//Kiem tra co chon xem diem cao hay khong
	public boolean isViewHigscores() {
		return viewHigscores;
	}

	//Kiem tra co chon newgame khong
	public boolean isNewGame() {
		return isNewGame;
	}

	//Kiem tra game over
	public boolean isGameOver() {
		return isGameOver;
	}

	//Kiem tra co tam dung game hay khong
	public boolean isPaused() {
		return isPaused;
	}

	//Tao tuong cho level trung binh
	private void drawMediumLevelWalls() {
		int x1, x2, x3, x4, x5, x6, x7, x8, x9;
		int y1, y2, y3, y4, y5, y6, y7, y8, y9;
		x1 = 5;
		x2 = 10;
		x3 = 15;
		x4 = 20;
		x5 = 25;
		x6 = 7;
		x7 = 1;
		x8 = 20;
		x9 = 21;
		y1 = 7;
		y2 = 3;
		y3 = 9;
		y4 = 2;
		y5 = 8;
		y6 = 20;
		y7 = 15;
		y8 = 18;
		y9 = 15;

		board.setTile(x1, y1, TileType.Wall);
		board.setTile(x2, y2, TileType.Wall);
		board.setTile(x3, y3, TileType.Wall);
		board.setTile(x4, y4, TileType.Wall);
		board.setTile(x5, y5, TileType.Wall);
		board.setTile(x6, y6, TileType.Wall);
		board.setTile(x7, y7, TileType.Wall);
		board.setTile(x8, y8, TileType.Wall);
		board.setTile(x9, y9, TileType.Wall);

	}

	//Tao tuong cho level kho
	private void drawHardLevelWalls() {
		int x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12;
		int y1, y2, y3, y4, y5, y6, y7, y8, y9, y10, y11, y12;

		x1 = 1;
		x2 = 2;
		x3 = 3;
		x4 = 4;
		x5 = 5;
		x6 = 6;
		y1 = 1;
		y2 = 2;
		y3 = 3;
		y4 = 4;
		y5 = 5;
		y6 = 6;

		x7 = 18;
		x8 = 19;
		x9 = 20;
		x10 = 21;
		x11 = 22;
		x12 = 23;
		y7 = 18;
		y8 = 19;
		y9 = 20;
		y10 = 21;
		y11 = 22;
		y12 = 23;

		board.setTile(x1, y1, TileType.Wall);
		board.setTile(x2, y2, TileType.Wall);
		board.setTile(x3, y3, TileType.Wall);
		board.setTile(x4, y4, TileType.Wall);
		board.setTile(x5, y5, TileType.Wall);
		board.setTile(x6, y6, TileType.Wall);
		board.setTile(x7, y7, TileType.Wall);
		board.setTile(x8, y8, TileType.Wall);
		board.setTile(x9, y9, TileType.Wall);
		board.setTile(x10, y10, TileType.Wall);
		board.setTile(x11, y11, TileType.Wall);
		board.setTile(x12, y12, TileType.Wall);

	}

	//Tao thuc an cho ran
	private void spawnFruit() {
		this.nextFruitScore = 100;

		int index = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());

		int freeFound = -1;
		for (int x = 0; x < BoardPanel.COL_COUNT; x++) {
			for (int y = 0; y < BoardPanel.ROW_COUNT; y++) {
				TileType tileType = board.getTile(x, y);
				if (tileType == TileType.Wall) {
					if (++freeFound == index) {
						board.setTile(x + 1, y, TileType.Fruit);
						break;
					}
				} else if (tileType == null || tileType == TileType.Fruit) {
					if (++freeFound == index) {
						board.setTile(x, y, TileType.Fruit);
						break;
					}
				}
			}
		}
	}

	//Lay ngay choi
	public Date getDate() {
		return date;
	}

	//Lay ten nguoi choi
	public String getName() {
		return name;
	}

	//Lay level dang choi
	public String getDifficulty() {
		return difficulty;
	}

	//Xoa text level dang choi
	public void clearDifficulty() {
		this.difficulty = "?";
	}

	//Xoa ten nguoi choi
	public void clearName() {
		this.name = "?";
	}

	//Lay so diem hien tai
	public int getScore() {
		return score;
	}

	//Lay so thuc an da an duoc
	public int getFruitsEaten() {
		return fruitsEaten;
	}

	//Lay so diem cua thuc an do
	public int getNextFruitScore() {
		return nextFruitScore;
	}

	//Lay huong di cua ran
	public Direction getDirection() {
		return directions.peek();
	}

	public static void main(String[] args) {
		SnakeGame snakeGame = new SnakeGame();
		snakeGame.startGame();
	}

}