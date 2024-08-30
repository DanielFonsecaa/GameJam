package io.forsome.gameartifacts;

import io.forsome.game.FightingGame;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;
import org.academiadecodigo.simplegraphics.graphics.Text;

public class HUD {

    private int roundTimer = 60;
    private Rectangle playerHealthBar;
    private Rectangle enemyHealthBar;
    private Text timer;
    private int enemyLifeBarX = 600;
    private int enemyLifeBarLifeSize = 350;
    private int playerLifeBarLifeSize = 350;
    private FightingGame fg;
    private Picture roundCounter;
    private Picture enemyRoundCounter;

    public HUD(FightingGame fg) {
        //                                      x      y       lifeSize  height
        this.playerHealthBar = new Rectangle(90, 30, playerLifeBarLifeSize, 50);
        this.enemyHealthBar = new Rectangle(enemyLifeBarX, 30, enemyLifeBarLifeSize, 50);
        this.timer = new Text(515, 60, String.valueOf(roundTimer)); // X Y String
        timer.grow(70,70);
        this.fg = fg;
        this.roundCounter = new Picture(30,30, "rsc/GameArtifacts/RoundCounter.png");
        this.enemyRoundCounter = new Picture(975,30, "rsc/GameArtifacts/RoundCounter.png");
    }

    public void drawHUD() {
        delete();
        enemyLifeBarX = 600;
        enemyLifeBarLifeSize = 350;
        playerLifeBarLifeSize = 350;
        // Player Life
        playerHealthBar = new Rectangle(90, 30, playerLifeBarLifeSize, 50);
        playerHealthBar.draw();
        playerHealthBar.setColor(Color.GREEN);
        playerHealthBar.fill();
        // EnemyLife
        enemyHealthBar = new Rectangle(enemyLifeBarX, 30, enemyLifeBarLifeSize, 50);
        enemyHealthBar.draw();
        enemyHealthBar.setColor(Color.GREEN);
        enemyHealthBar.fill();
        // Timer
        timer.setColor(Color.RED);
        timer.draw();
    }

    public void delete(){
        playerHealthBar.delete();
        enemyHealthBar.delete();
        timer.delete();
    }

    public void updateTimer(){
        timer.delete();
        timer = new Text(515,60,String.valueOf(roundTimer) );
        timer.setColor(Color.RED);
        timer.draw();
        timer.grow(70,70);
        roundTimer--;
    }

    public void resetRoundTimer(){
        this.roundTimer = 60;
    }

    public void enemyDamage() {
        enemyLifeBarX += 35;
        enemyLifeBarLifeSize -= 35;
        enemyHealthBar.delete();
        enemyHealthBar = new Rectangle(enemyLifeBarX, 30, enemyLifeBarLifeSize, 50);
        enemyHealthBar.setColor(Color.GREEN);
        if (enemyLifeBarLifeSize <= 230) {
            enemyHealthBar.setColor(Color.YELLOW);
        }
        if (enemyLifeBarLifeSize <= 90) {
            enemyHealthBar.setColor(Color.RED);
        }
        //System.out.println(enemyLifeBarLifeSize);
        enemyHealthBar.draw();
        enemyHealthBar.fill();
    }

    public void playerDamage() { // Need to be updated!
        playerLifeBarLifeSize -= 35;
        playerHealthBar.delete();
        playerHealthBar = new Rectangle(90, 30, playerLifeBarLifeSize, 50);
        playerHealthBar.setColor(Color.GREEN);
        if (playerLifeBarLifeSize <= 230) {
            playerHealthBar.setColor(Color.YELLOW);
        }
        if (playerLifeBarLifeSize <= 90) {
            playerHealthBar.setColor(Color.RED);
        }
        //System.out.println(playerLifeBarLifeSize);
        playerHealthBar.draw();
        playerHealthBar.fill();
    }

    public void roundCounter(){
        roundCounter.draw();
    }

    public void deleteRoundCounter(){
        roundCounter.delete();
    }

    public void enemyRoundCounter(){
        enemyRoundCounter.draw();
    }

    public void deleteEnemyRoundCounter(){
        enemyRoundCounter.delete();
    }

}
