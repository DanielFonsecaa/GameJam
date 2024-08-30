package io.forsome.fighter;

import io.forsome.game.FightingGame;
import org.academiadecodigo.simplegraphics.pictures.*;

public class Enemy extends Fighter {

    // Attributes:
    private Picture fighterSprite;
    private Picture enemyName;
    private Picture square;
    private FightingGame fg;
    private boolean attacking = false;

    // Constructor:
    public Enemy(FightingGame fg, Picture sprite) {
        super(sprite);
        this.fighterSprite = sprite;
        this.fg = fg;
        this.enemyName = new Picture(600, 100, fg.getEnemySprites()[12]); // X Y String
        this.square = new Picture(900, 100, fg.getEnemySprites()[11]);
        enemyName.grow(-22,-22);
    }

    @Override
    public void createFighter() {
        fighterSprite = new Picture(700, 250, fg.getEnemySprites()[0]);
        this.enemyName = new Picture(600, 100, fg.getEnemySprites()[12]); // X Y String
        this.square = new Picture(900, 100, fg.getEnemySprites()[11]);
        enemyName.grow(-22,-22);
        fighterSprite.draw();
        enemyName.draw();
        square.draw();
    }

    @Override
    public void resetPosition() {
        fighterSprite.delete();
        enemyName.delete();
        square.delete();
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void moveRight() {
        if (getMaxX() <= 980) {
            fighterSprite.translate(30, 0);
            fg.enemyCheckCollision();
        }
    }

    public void moveLeft() {
        if (getX() >= 40 && fg.getEnemyX() >= fg.getPlayerMaxX()) {
            fighterSprite.translate(-30, 0);
            fg.enemyCheckCollision();
        }
    }

    public void jump() {
        fighterSprite.load(fg.getEnemySprites()[6]);
        fighterSprite.translate(0, -10);
        fighterSprite.translate(0, -10);
        fighterSprite.translate(0, -10);
        try {
            Thread.sleep(200);
            land();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        fg.enemyCheckCollision();
    }

    public void standUp() {
        fighterSprite.load(fg.getEnemySprites()[0]);
    }

    public void land() {
        fighterSprite.translate(0, 30);
        fighterSprite.load(fg.getEnemySprites()[0]);
    }

    public void crouch() {
        fighterSprite.load(fg.getEnemySprites()[3]);
        try {
            Thread.sleep(100);
            standUp();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        fg.enemyCheckCollision();
    }

    public void highPunch() {
        attacking = true;
        fighterSprite.load(fg.getEnemySprites()[1]);
        try {
            Thread.sleep(100);
            fg.enemyCheckCollision();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            attacking = false;
        }
    }

    public void lowPunch() {
        attacking = true;
        fighterSprite.load(fg.getEnemySprites()[4]);
        try {
            Thread.sleep(100);
            fg.enemyCheckCollision();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            attacking = false;
        }
    }

    public void highKick() {
        attacking = true;
        fighterSprite.load(fg.getEnemySprites()[2]);
        try {
            Thread.sleep(100);
            fg.enemyCheckCollision();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            attacking = false;
        }
    }

    public void lowKick() {
        attacking = true;
        fighterSprite.load(fg.getEnemySprites()[5]);
        try {
            Thread.sleep(100);
            fg.enemyCheckCollision();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            attacking = false;
        }
    }

    public void randomMove() {
        int randomNumber = (int) Math.floor(Math.random() * 12);
        switch (randomNumber) {
            case 1 -> moveRight();
            case 2 -> moveLeft();
            case 3 -> jump();
            case 4 -> crouch();
            case 5 -> highPunch();
            case 6 -> highKick();
            case 7 -> lowKick();
            case 8 -> lowPunch();
            case 9 -> moveLeft();
            case 10 -> moveLeft();
            case 11 -> highPunch();
            case 12 -> highKick();
        }
    }

    public void enemyWon() {
        fighterSprite.translate(0,-20);
        fighterSprite.load(fg.getEnemySprites()[9]);
    }

    public void enemyLost() {
        fighterSprite.load(fg.getEnemySprites()[10]);
    }

    @Override
    public int getX() {
        return fighterSprite.getX();
    }

    @Override
    public int getY() {
        return fighterSprite.getY();
    }

    @Override
    public int getMaxX() {
        return fighterSprite.getMaxX();
    }

    @Override
    public int getMaxY() {
        return fighterSprite.getMaxY();
    }
}
