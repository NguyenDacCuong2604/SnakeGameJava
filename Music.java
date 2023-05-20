package Code;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Music {

    private Clip gameOverSound;
    private Clip eatAppleSound;
    private Clip gameMusicSound;

    public Music() { initSounds(); }

    //Doc file am thanh
    public void initSounds() {

        try {
            URL url = this.getClass().getClassLoader().getResource("Code/gameOver.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            gameOverSound = AudioSystem.getClip();
            gameOverSound.open(audioInputStream);

            URL url1 = this.getClass().getClassLoader().getResource("Code/eatApple.wav");
            AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(url1);
            eatAppleSound = AudioSystem.getClip();
            eatAppleSound.open(audioInputStream1);

            URL url2 = this.getClass().getClassLoader().getResource("Code/gameMusic.wav");
            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(url2);
            gameMusicSound = AudioSystem.getClip();
            gameMusicSound.open(audioInputStream2);
        }

        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {}

    }
    
    //Dung am thanh
    public void pauseMusic() {
        if(gameMusicSound.isRunning()) {
            gameMusicSound.stop();
        }
        else {
            gameMusicSound.loop(100);
            gameMusicSound.start();
        }
    }

    //Bat dau am thanh
    public void playMusic() {
        gameMusicSound.setMicrosecondPosition(0);
        gameMusicSound.loop(100);
        gameMusicSound.start();
    }

    //Dung am thanh nen
    public void stopMusic() {
        gameMusicSound.stop();
    }

    //Bat am thanh game lose
    public void playGameOverSound() {
        gameOverSound.setMicrosecondPosition(0);
        gameOverSound.start();
    }
    
    //Bat am thanh ran an moi
    public void playEatAppleSound() {
        eatAppleSound.setMicrosecondPosition(0);
        eatAppleSound.start();
    }
}
