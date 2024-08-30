package io.forsome.game;

import io.forsome.fighter.Enemy;
import io.forsome.fighter.Player;
import io.forsome.gameartifacts.*;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.*;
import io.forsome.gameartifacts.Music;

public class FightingGame implements KeyboardHandler {

    // Game Attributes (ScreenSize: 1030, 603)
    private int round = 3;
    private int playerWins = 0;
    private int enemyWins = 0;
    private Background background = new Background();
    private HUD gameHUD;
    private boolean gameStarted = false;
    // Fighters Attributes
    private Player player;
    private int playerHealth = 200;
    private Enemy enemy;
    private int enemyHealth = 200;
    protected int playerChosen;
    private String[] playerSprites = mekieAsPlayer;
    private String[] enemySprites = mekieAsEnemy;
    // Timer
    private int roundTime = 60;
    private boolean timerRunning = false;
    // Keyboard
    private Keyboard keyboard;
    // Enemy is defeated booleans
    private boolean mekieDefeated = false;
    private boolean mafaldaDefeated = false;
    private boolean nozkDefeated = false;
    private boolean fight = false;
    private Thread timerThread;
    private Picture endPlayer;
    // Musics and sounds:
    private static final String THE_SONG = "src/rsc/Sounds/theme.wav";
    private static final String HIT_SOUND_PATH = "src/rsc/Sounds/hit.wav";
    private static final String FIGHT_SOUND = "src/rsc/Sounds/readyFight.wav";
    private static final String LOST_SOUND = "src/rsc/Sounds/youLose.wav";
    private static final String WIN_SOUND = "src/rsc/Sounds/youWin.wav";
    private static final String GAME_OVER = "src/rsc/Sounds/gameover.wav";
    private static final float MESSAGES_VOLUME = 0.6f;
    private static final float DEFAULT_VOLUME = 0.3f;
    private static final float YOU_LOSE_VOLUME = 0.8f;


    // Constructor
    public FightingGame() {
        gameHUD = new HUD(this);
        player = new Player(this, new Picture(200, 300, playerSprites[0]));
        enemy = new Enemy(this, new Picture(700, 300, enemySprites[0]));
        this.keyboard = new Keyboard(this);
        addKeyboard();
    }

    public int getPlayerMaxX() {
        return player.getMaxX();
    }

    public int getEnemyX() {
        return enemy.getX();
    }

    public void gameStart() {
        // Show the menu and wait for the space key to be pressed
        gameHUD.delete();
        background.showMenu();
        background.hideWin();
    }

    public void playerSelection() {
        PlayerSelect playerSelect = new PlayerSelect();
        playerChosen = playerSelect.selection();
    }

    public void newGame(){
        // Background Music
        String filePath = THE_SONG;
        Music.playMusic(filePath);
        //background.hideGameOver();
        player.deleteNameAndSquare();
        background.hideNotGameOver();
        background.hideWin();
        player.resetPosition();
        enemy.resetPosition();
        gameHUD.delete();
        roundTime = 60;
        playerWins = 0;
        enemyWins = 0;
        round = 3;
        background.hideMenu();
        background.createLevel();
        gameHUD.drawHUD();
        // Player and Enemy Creation
        player.createFighter();
        enemy.createFighter();
        // Main game loop:
        gameStarted = true;
        while (gameStarted) {
            if (!mekieDefeated) {
                //Music.readyFightSound(FIGHT_SOUND,MESSAGES_VOLUME);
                enemySprites = mekieAsEnemy;
                playFightMekie();
                mekieDefeated = true;
            }
            if (mekieDefeated && !mafaldaDefeated) {
                //Music.readyFightSound(FIGHT_SOUND,MESSAGES_VOLUME);
                enemySprites = mafaldaAsEnemy;
                playFightMafalda();
                mafaldaDefeated = true;
            }
            if (mafaldaDefeated && !nozkDefeated) {
                //Music.readyFightSound(FIGHT_SOUND,MESSAGES_VOLUME);
                enemySprites = nozkAsEnemy;
                playFightNozk();
                nozkDefeated = true;
            }
            background.showNotGameOver();
            endPlayer = new Picture(720,176,playerSprites[9]);
            endPlayer.grow(50,50);
            endPlayer.draw();
            gameStarted = false; // End game loop after all fights are done
            try {
                Thread.sleep(5000);
                playerCheckCollisions();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endPlayer.delete();
        }
        mekieDefeated = false;
        mafaldaDefeated = false;
        nozkDefeated = false;
        enemySprites = mekieAsEnemy;
        gameHUD.delete();
        background.hideGameOver();
        gameStart();
    }

    public void fight() {
        fight = false;
        Music.readyFightSound(FIGHT_SOUND,MESSAGES_VOLUME);
        while (!fight) {
            resetRound();
            //startTimer();
            if (round == 3) {
                background.round1();
            } else if (round == 2) {
                background.round2();
            } else if (round == 1) {
                background.finalRound();
            }
            round--;
            try {
                Thread.sleep(1500);
                background.hide();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Start the timer in a separate thread
            new Thread(this::startTimer).start();
            while (playerHealth > 0 && enemyHealth > 0 && roundTime > 0) {
                enemy.randomMove();
                // Add a delay to control the game loop speed
                try {
                    Thread.sleep(300);
                    playerCheckCollisions();
                    enemyCheckCollision();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (playerHealth > enemyHealth) {
                playerWins++;
                background.ko();
                gameHUD.roundCounter(); //player
                try {
                    player.playerWon();
                    enemy.enemyLost();
                    Thread.sleep(1000);
                    background.hideKo();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (enemyHealth > playerHealth) {
                enemyWins++;
                background.ko();
                gameHUD.enemyRoundCounter();
                try {
                    enemy.enemyWon();
                    player.playerLost();
                    Thread.sleep(1000);
                    background.hideKo();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (enemyHealth <= 0) {
                try {
                    enemy.enemyLost();
                    Music.youWinSound(WIN_SOUND,MESSAGES_VOLUME);
                    player.playerWon();
                    Thread.sleep(1000);
                    if (playerWins == 2) {
                        background.showWin();
                        gameHUD.deleteRoundCounter();
                        gameHUD.deleteEnemyRoundCounter();
                        fight = true;
                        round = 3;
                        playerWins = 0;
                        enemyWins = 0;
                        Thread.sleep(1000);
                        return;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (playerHealth <= 0) {
                try {
                    enemy.enemyWon();
                    Music.youLoseSound(LOST_SOUND,YOU_LOSE_VOLUME);
                    player.playerLost();
                    Thread.sleep(1000);
                    if (enemyWins == 2) {
                        background.showLoose();
                        Thread.sleep(1000);
                        Music.gameOverSound(GAME_OVER,MESSAGES_VOLUME);
                        background.showGameOver();
                        Thread.sleep(3000);
                        mekieDefeated = false;
                        mafaldaDefeated = false;
                        nozkDefeated = false;
                        enemySprites = mekieAsEnemy;
                        gameHUD.deleteEnemyRoundCounter();
                        gameHUD.deleteRoundCounter();
                        enemyWins = 0;
                        playerWins = 0;
                        round = 3;
                        gameHUD.delete();
                        background.hideGameOver();
                        background.hideLoose();
                        gameStarted = false;
                        fight = true;
                        gameHUD.resetRoundTimer();
                        newGame();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            timerRunning = false; // Stop the timer when the round ends
            if (timerThread != null && timerThread.isAlive()) {
                try {
                    timerThread.join(); // Wait for the timer thread to finish
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            gameHUD.resetRoundTimer(); // Reset the round timer
        }
    }

    public void playFightMekie() {
        fight();
    }

    public void playFightMafalda() {
         fight();
    }

    public void playFightNozk() {
        fight();
    }

    private void resetRound() {
        background.hideWin();
        gameHUD.delete();
        gameHUD.drawHUD();
        playerHealth = 200;
        enemyHealth = 200;
        roundTime = 60;
        if (timerThread != null && timerThread.isAlive()) {
            timerRunning = false;
            try {
                timerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        timerRunning = true;
        player.resetPosition();
        enemy.resetPosition();
        player.createFighter();
        enemy.createFighter();
    }

    private void startTimer() {
        roundTime = 60;
        timerThread = new Thread(() -> {
            while (roundTime > 0 && timerRunning) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                roundTime--;
                gameHUD.updateTimer();
            }
            timerRunning = false;
        });
        timerThread.start();
    }

    private void playerCheckCollisions() {
        if (player.getMaxX() >= enemy.getX()) {
            if (player.isAttacking()) {
                enemyHealth -= 20;
                gameHUD.enemyDamage();   //this is to decrease size of rectangle
                Music.playSoundEffect(HIT_SOUND_PATH,DEFAULT_VOLUME);
            }
        }
    }

    public void enemyCheckCollision() {
        if (enemy.getX() <= player.getMaxX()) {
            if (enemy.isAttacking()) {
                playerHealth -= 20;
                gameHUD.playerDamage();   //this is to decrease size of rectangle
                Music.playSoundEffect(HIT_SOUND_PATH,DEFAULT_VOLUME);
            }
        }
    }

    public void addKeyboard() {
        KeyboardEvent startGame = new KeyboardEvent();
        startGame.setKey(KeyboardEvent.KEY_SPACE);
        startGame.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(startGame);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
            if (!gameStarted) {
                gameStarted = true;
                new Thread(() -> {
                    playerSelection(); // Perform player selection
                    selectedPlayer(playerChosen); // Set the player sprites based on selection
                    newGame();         // Start the game after selection
                }).start(); // Start the game loop in a new thread
            }
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
    }

    public void selectedPlayer(int playerChosen) {
        switch (playerChosen) {
            case 1:
                //Mekie
                playerSprites = mekieAsPlayer;
                break;
            case 2:
                //Mafalda
                playerSprites = mafaldaAsPlayer;
                break;
            case 3:
                //Nozk
                playerSprites = nozkAsPlayer;
                break;
        }
    }

    public String[] getPlayerSprites() {
        return this.playerSprites;
    }

    public String[] getEnemySprites() {
        return this.enemySprites;
    }

    // Mekie Sprites - 1
    private static String[] mekieAsPlayer = {
            "rsc/Mekie - Left/0 - Idle/0.png",
            "rsc/Mekie - Left/1 - High Punch/0.png",
            "rsc/Mekie - Left/2 - High Kick/0.png",
            "rsc/Mekie - Left/3 - Crouch/0.png",
            "rsc/Mekie - Left/4 - Low Punch/0.png",
            "rsc/Mekie - Left/5 - Low Kick/0.png",
            "rsc/Mekie - Left/6 - Jump/0.png",
            "rsc/Mekie - Left/7 - Jump Kick/0.png",
            "rsc/Mekie - Left/8 - Special/0.png",
            "rsc/Mekie - Left/9 - Win/0.png",
            "rsc/Mekie - Left/10 - Lose/0.png",
            "rsc/Mekie - Left/11 - PIC/square.png",
            "rsc/Names/MEKIE_NAME.png"
    };
    private static String[] mekieAsEnemy = {
            "rsc/Mekie/0 - Idle/0.png",
            "rsc/Mekie/1 - High Punch/0.png",
            "rsc/Mekie/2 - High Kick/0.png",
            "rsc/Mekie/3 - Crouch/0.png",
            "rsc/Mekie/4 - Low Punch/0.png",
            "rsc/Mekie/5 - Low Kick/0.png",
            "rsc/Mekie/6 - Jump/0.png",
            "rsc/Mekie/7 - Jump Kick/0.png",
            "rsc/Mekie/8 - Special/0.png",
            "rsc/Mekie/9 - Win/0.png",
            "rsc/Mekie/10 - Lose/0.png",
            "rsc/Mekie/11 - PIC/square.png",
            "rsc/Names/MEKIE_NAME.png"
    };

    //Mafalda Sprites - 2
    private static String[] mafaldaAsPlayer = {
            "rsc/Mafalda - Left/0 - Idle/0.png",
            "rsc/Mafalda - Left/1 - High Punch/0.png",
            "rsc/Mafalda - Left/2 - High Kick/0.png",
            "rsc/Mafalda - Left/3 - Crouch/0.png",
            "rsc/Mafalda - Left/4 - Low Punch/0.png",
            "rsc/Mafalda - Left/5 - Low Kick/0.png",
            "rsc/Mafalda - Left/6 - Jump/0.png",
            "rsc/Mafalda - Left/7 - Jump Kick/0.png",
            "rsc/Mafalda - Left/8 - Special/0.png",
            "rsc/Mafalda - Left/9 - Win/0.png",
            "rsc/Mafalda - Left/10 - Lose/0.png",
            "rsc/Mafalda - Left/11 - PIC/square.png",
            "rsc/Names/MAFALDA-NAME.png"
    };
    private static String[] mafaldaAsEnemy = {
            "rsc/Mafalda/0 - Idle/0.png",
            "rsc/Mafalda/1 - High Punch/0.png",
            "rsc/Mafalda/2 - High Kick/0.png",
            "rsc/Mafalda/3 - Crouch/0.png",
            "rsc/Mafalda/4 - Low Punch/0.png",
            "rsc/Mafalda/5 - Low Kick/0.png",
            "rsc/Mafalda/6 - Jump/0.png",
            "rsc/Mafalda/7 - Jump Kick/0.png",
            "rsc/Mafalda/8 - Special/0.png",
            "rsc/Mafalda/9 - Win/0.png",
            "rsc/Mafalda/10 - Lose/0.png",
            "rsc/Mafalda/11 - PIC/square.png",
            "rsc/Names/MAFALDA-NAME.png"
    };

    //Nozk Sprites - 3
    private static String[] nozkAsPlayer = {
            "rsc/Nozk - Left/0 - Idle/0.png",
            "rsc/Nozk - Left/1 - High Punch/0.png",
            "rsc/Nozk - Left/2 - High Kick/0.png",
            "rsc/Nozk - Left/3 - Crouch/0.png",
            "rsc/Nozk - Left/4 - Low Punch/0.png",
            "rsc/Nozk - Left/5 - Low Kick/0.png",
            "rsc/Nozk - Left/6 - Jump/0.png",
            "rsc/Nozk - Left/7 - Jump Kick/0.png",
            "rsc/Nozk - Left/8 - Special/0.png",
            "rsc/Nozk - Left/9 - Win/0.png",
            "rsc/Nozk - Left/10 - Lose/0.png",
            "rsc/Nozk - Left/11 - PIC/square.png",
            "rsc/Names/NOZK-NAME.png"
    };
    private static String[] nozkAsEnemy = {
            "rsc/Nozk/0 - Idle/0.png",
            "rsc/Nozk/1 - High Punch/0.png",
            "rsc/Nozk/2 - High Kick/0.png",
            "rsc/Nozk/3 - Crouch/0.png",
            "rsc/Nozk/4 - Low Punch/0.png",
            "rsc/Nozk/5 - Low Kick/0.png",
            "rsc/Nozk/6 - Jump/0.png",
            "rsc/Nozk/7 - Jump Kick/0.png",
            "rsc/Nozk/8 - Special/0.png",
            "rsc/Nozk/9 - Win/0.png",
            "rsc/Nozk/10 - Lose/0.png",
            "rsc/Nozk/11 - PIC/square.png",
            "rsc/Names/NOZK-NAME.png"
    };

    /*
    public static void main(String[] args) {
        String filePath = THE_SONG;
        Music.playMusic(filePath);
        FightingGame fg = new FightingGame();
        fg.gameStart();
    }

     */
}
