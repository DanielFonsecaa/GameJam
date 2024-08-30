package io.forsome.fighter;

import io.forsome.game.FightingGame;
import org.academiadecodigo.simplegraphics.keyboard.*;
import org.academiadecodigo.simplegraphics.pictures.Picture;


public class Player extends Fighter implements KeyboardHandler {

    private Keyboard keyboard;
    private Picture fighterSprite;
    private boolean jumping = false;
    private boolean crouching = false;
    private boolean attacking = false;
    private Picture playerName;
    private Picture square;
    private FightingGame fg;

    public Player(FightingGame fg, Picture sprite) {
        super(sprite);
        this.fighterSprite = sprite;
        this.fg = fg;
        this.keyboard = new Keyboard(this);
        this.playerName = new Picture(150, 100, fg.getPlayerSprites()[12]); // X Y String
        this.square = new Picture(40,100,fg.getPlayerSprites()[11]);
        playerName.grow(-22,-22);
        addKeyboard();
    }

    @Override
    public void createFighter() {
        fighterSprite = new Picture(200,250,fg.getPlayerSprites()[0]);
        this.playerName = new Picture(150, 100, fg.getPlayerSprites()[12]); // X Y String
        this.square = new Picture(40,100,fg.getPlayerSprites()[11]);
        playerName.grow(-22,-22);
        fighterSprite.draw();
        playerName.draw();
        square.draw();
    }

    public void deleteNameAndSquare(){
        square.delete();
        playerName.delete();
    }

    @Override
    public void resetPosition() {
        fighterSprite.delete();
    }

    public void addKeyboard() {
        addKeyEvent(KeyboardEvent.KEY_D, KeyboardEventType.KEY_PRESSED);
        addKeyEvent(KeyboardEvent.KEY_A, KeyboardEventType.KEY_PRESSED);
        addKeyEvent(KeyboardEvent.KEY_W, KeyboardEventType.KEY_PRESSED);
        addKeyEvent(KeyboardEvent.KEY_S, KeyboardEventType.KEY_PRESSED);
        addKeyEvent(KeyboardEvent.KEY_H, KeyboardEventType.KEY_PRESSED);
        addKeyEvent(KeyboardEvent.KEY_J, KeyboardEventType.KEY_PRESSED);
        addKeyEvent(KeyboardEvent.KEY_K, KeyboardEventType.KEY_PRESSED);
        addKeyEvent(KeyboardEvent.KEY_L, KeyboardEventType.KEY_PRESSED);

        addKeyEvent(KeyboardEvent.KEY_W, KeyboardEventType.KEY_RELEASED);
        addKeyEvent(KeyboardEvent.KEY_S, KeyboardEventType.KEY_RELEASED);
        addKeyEvent(KeyboardEvent.KEY_H, KeyboardEventType.KEY_RELEASED);
        addKeyEvent(KeyboardEvent.KEY_J, KeyboardEventType.KEY_RELEASED);
        addKeyEvent(KeyboardEvent.KEY_K, KeyboardEventType.KEY_RELEASED);
        addKeyEvent(KeyboardEvent.KEY_L, KeyboardEventType.KEY_RELEASED);
    }

    private void addKeyEvent(int key, KeyboardEventType type) {
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(key);
        event.setKeyboardEventType(type);
        keyboard.addEventListener(event);
    }

    public void moveRight() {
        if(fg.getPlayerMaxX()<=fg.getEnemyX()) {
            fighterSprite.translate(30, 0);
        }
    }

    public void moveLeft() {
        fighterSprite.translate(-30, 0);
    }

    public void jump() {        //has to improve our jump, maybe we can put more images???
        fighterSprite.load(fg.getPlayerSprites()[6]);
        fighterSprite.translate(0, -20);
        fighterSprite.translate(0, -20);
        fighterSprite.translate(0, -20);
    }

    public void crouch() {
        fighterSprite.load(fg.getPlayerSprites()[3]);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void standUp() {
        fighterSprite.translate(0, 0);
    }

    public void land() {
        try {
            Thread.sleep(300);
            fighterSprite.translate(0, 60);
            jumping = false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void highPunch() {
        fighterSprite.load(fg.getPlayerSprites()[1]);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void lowPunch() {
        fighterSprite.load(fg.getPlayerSprites()[4]);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void highKick() {
        fighterSprite.load(fg.getPlayerSprites()[2]);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void lowKick() {
        fighterSprite.load(fg.getPlayerSprites()[5]);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

    public boolean isAttacking() {
        return attacking;
    }

    public void playerWon() {

        fighterSprite.load(fg.getPlayerSprites()[9]);
    }

    public void playerLost() {
        fighterSprite.load(fg.getPlayerSprites()[10]);
    }


    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_D:
                if (!crouching && getMaxX() <= 1020) {
                    moveRight();
                }
                break;
            case KeyboardEvent.KEY_A:
                if (!crouching && getX() >= 40) {
                    moveLeft();
                }
                break;
            case KeyboardEvent.KEY_W:
                if (!jumping && !attacking) {
                    fighterSprite.load(fg.getPlayerSprites()[6]);
                    jump();
                    jumping = true;
                }
                break;
            case KeyboardEvent.KEY_S:
                if (!jumping && !attacking) {
                    fighterSprite.load(fg.getPlayerSprites()[3]);
                    crouch();
                    crouching = true;
                }
                break;
            case KeyboardEvent.KEY_H:
                if (!jumping && !attacking && !crouching) {
                    fighterSprite.load(fg.getPlayerSprites()[1]);
                    highPunch();
                    attacking = true;
                }
                break;
            case KeyboardEvent.KEY_J:
                if (!jumping && !attacking && crouching) {
                    fighterSprite.load(fg.getPlayerSprites()[4]);
                    lowPunch();
                    attacking = true;
                }
                break;
            case KeyboardEvent.KEY_K:
                if (!jumping && !attacking && !crouching) {
                    fighterSprite.load(fg.getPlayerSprites()[2]);
                    highKick();
                    attacking = true;
                }
                break;
            case KeyboardEvent.KEY_L:
                if (!jumping && !attacking && crouching) {
                    fighterSprite.load(fg.getPlayerSprites()[5]);
                    lowKick();
                    attacking = true;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_W:
                if (jumping) {
                    fighterSprite.load(fg.getPlayerSprites()[0]);
                    land();
                    jumping = false;
                }
                break;


            case KeyboardEvent.KEY_S:
                if (crouching) {
                    fighterSprite.load(fg.getPlayerSprites()[0]);
                    standUp();
                    crouching = false;
                }
                break;
            case KeyboardEvent.KEY_H:
                if(attacking && !crouching){
                    try {
                        Thread.sleep(200);
                        fighterSprite.load(fg.getPlayerSprites()[0]);
                        attacking = false;
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            case KeyboardEvent.KEY_J:
                if(attacking && crouching){
                    try {
                        Thread.sleep(200);
                        fighterSprite.load(fg.getPlayerSprites()[3]);
                        attacking = false;
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }

            case KeyboardEvent.KEY_K:
                if(attacking && !crouching){
                    try {
                        Thread.sleep(200);
                        fighterSprite.load(fg.getPlayerSprites()[0]);
                        attacking = false;
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            case KeyboardEvent.KEY_L:
                if(attacking && crouching){
                    try {
                        Thread.sleep(200);
                        fighterSprite.load(fg.getPlayerSprites()[3]);
                        attacking = false;
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}