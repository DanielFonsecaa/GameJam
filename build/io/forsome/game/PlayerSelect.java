package io.forsome.game;

import io.forsome.gameartifacts.Music;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class PlayerSelect implements KeyboardHandler {

    Picture background;
    Picture arrow;
    private boolean selected = false;
    private Keyboard keyboard;
    private static final String MENU_SELECTION = "rsc/Sounds/menuSelection.wav";

    public PlayerSelect() {
        this.background = new Picture(10, 10, "rsc/GameArtifacts/PlayerSelection.png");
        this.arrow = new Picture(10, 60, "rsc/GameArtifacts/arrow.png");
        this.keyboard = new Keyboard(this);
        addKeyboard();
        background.draw();
        arrow.grow(-80, -80);
        arrow.draw();
    }

    public int selection() {
        while (!selected) {
            // wait for player select;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int choice = 0;
        switch (arrow.getX()) {
            case 90:
                background.delete();
                arrow.delete();
                choice = 1;
                break;
            case 326:
                background.delete();
                arrow.delete();
                choice = 2;
                break;
            case 562:
                background.delete();
                arrow.delete();
                choice = 3;
                break;
            case 798:
                background.delete();
                arrow.delete();
                choice = 3;
                break;
            default:
                choice = 1;
                break;
        }
        return choice;
    }

    public void moveLeft() {
        if ((background.getX() + 236) < arrow.getX()) {
            arrow.translate(-236, 0);
            Music.menuSelection(MENU_SELECTION);
        }
    }

    public void moveRight() {
        if ((background.getMaxX() - 236) >= arrow.getMaxX()) {
            arrow.translate(236, 0);
            Music.menuSelection(MENU_SELECTION);
        }
    }

    public void addKeyboard() {
        KeyboardEvent selectPlayer = new KeyboardEvent();
        selectPlayer.setKey(KeyboardEvent.KEY_SPACE);
        selectPlayer.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(selectPlayer);

        KeyboardEvent left = new KeyboardEvent();
        left.setKey(KeyboardEvent.KEY_LEFT);
        left.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(left);

        KeyboardEvent right = new KeyboardEvent();
        right.setKey(KeyboardEvent.KEY_RIGHT);
        right.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(right);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
            selected = true;
        }
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_LEFT) {
            moveLeft();
        }
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_RIGHT) {
            moveRight();
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }

}
