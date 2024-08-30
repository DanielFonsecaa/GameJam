package io.forsome.game;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.pictures.Picture;
import static org.academiadecodigo.simplegraphics.graphics.Canvas.*;


public class Background {

    Picture backgroundRelva;
    Picture backgroundSala;
    Picture menu;
    private int randomLevel;
    Picture win;
    Picture loose;
    Picture round1;
    Picture round2;
    Picture finalRound;
    Picture ko;
    Picture gameOver;
    Picture notGameOver;

    public Background(){
        this.backgroundRelva = new Picture(10, 10, "rsc/GameArtifacts/BackGroundSalinha.JPG");
        this.backgroundSala = new Picture(10, 10, "rsc/GameArtifacts/BackGroundRelva.JPG");
        this.menu = new Picture(10,10,"rsc/GameArtifacts/MENU.png");
        this.win = new Picture(500,300,"rsc/GameArtifacts/YouWin.png");
        win.grow(150,150);
        this.loose = new Picture(500,300,"rsc/GameArtifacts/YouLose.png");
        loose.grow(150,150);
        this.ko = new Picture(500,300,"rsc/GameArtifacts/K.O.png");
        ko.grow(100,100);
        this.round1 = new Picture(500,300,"rsc/GameArtifacts/Round1.png");
        round1.grow(150,150);
        this.round2 = new Picture(500,300,"rsc/GameArtifacts/Round2.png");
        round2.grow(150,150);
        this.finalRound = new Picture(500,300,"rsc/GameArtifacts/FinalRound.png");
        finalRound.grow(150,150);
        this.gameOver = new Picture(10,10,"rsc/GameArtifacts/gameOver.png");
        this.notGameOver = new Picture(10,10,"rsc/GameArtifacts/notGameOver.png");
    }

    public void createLevel() {
        randomLevel = (int) Math.floor(Math.random() * 2);
        switch (randomLevel) {
            case 0:
                backgroundRelva.draw();
                break;
            case 1:
                backgroundSala.draw();
                break;
        }
    }

    public void showMenu(){
        menu.draw();
    }

    public void hideMenu(){
        menu.delete();
    }

    public void showWin(){
        win.draw();
    }

    public void showLoose(){
        loose.draw();
    }

    public void hideLoose(){
        loose.delete();
    }

    public void hideWin(){
        win.delete();
    }

    public void round1(){
        round1.draw();

    }

    public void round2(){
        round2.draw();

    }

    public void finalRound(){
        finalRound.draw();

    }

    public void hide(){
        round1.delete();
        round2.delete();
        finalRound.delete();
    }

    public void ko(){
        ko.draw();

    }

    public void hideKo(){
        ko.delete();
    }

    public void showGameOver(){
        gameOver.draw();
    }

    public void showNotGameOver(){
        notGameOver.draw();
    }

    public void hideGameOver(){
        gameOver.delete();
    }

    public void hideNotGameOver(){
        notGameOver.delete();
    }
}