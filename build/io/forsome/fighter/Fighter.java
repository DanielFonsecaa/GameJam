package io.forsome.fighter;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public abstract class Fighter {

    private Picture sprite;

    public Fighter(Picture sprite) {
        this.sprite = sprite;
    }

    // Basic Movements
    public abstract void moveRight();
    public abstract void moveLeft();
    public abstract void jump();
    public abstract void crouch();

    // Skills
    public abstract void highPunch();
    public abstract void lowPunch();
    public abstract void highKick();
    public abstract void lowKick();

    // Other Methods
    public abstract void resetPosition();
    public abstract void createFighter();

    // Positions
    public abstract int getX();
    public abstract int getY();
    public abstract int getMaxX();
    public abstract int getMaxY();

}
