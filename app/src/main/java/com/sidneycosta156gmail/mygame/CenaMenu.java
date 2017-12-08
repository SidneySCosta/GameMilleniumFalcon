package com.sidneycosta156gmail.mygame;

import com.sidneycosta156gmail.mygame.AndGraph.AGGameManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGInputManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGScene;
import com.sidneycosta156gmail.mygame.AndGraph.AGScreenManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGSprite;

/**
 * Created by sidney on 23/11/17.
 */

public class CenaMenu extends AGScene {

    AGSprite imagemfundo = null;
    AGSprite vrIniciar = null;
    AGSprite vrOpcoes = null;
    AGSprite vrSobre = null;
    AGSprite vrSair = null;
    AGSprite vrScore = null;

    int estado = 0;
    //int som = 0;
    int somChew = 0;


    public CenaMenu(AGGameManager manager) {
        super(manager);
    }

    @Override
    public void init() {

        imagemfundo = createSprite(R.drawable.fundo5, 1, 1); // carregar imagem
        imagemfundo.setScreenPercent(100, 100); // percentual imagem ocupa na tela
        imagemfundo.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2); // percentual que a imagem vai ocupar na tela

        this.setSceneBackgroundColor(1, 0, 0);

        vrIniciar = this.createSprite(R.drawable.play, 1, 1);
        vrIniciar.setScreenPercent(50, 10);
        vrIniciar.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 1.4f);
        vrIniciar.fadeIn(3000);

        vrOpcoes = this.createSprite(R.drawable.option, 1, 1);
        vrOpcoes.setScreenPercent(50, 10);
        vrOpcoes.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 1.7f);
        vrOpcoes.fadeIn(3000);

        vrSobre = this.createSprite(R.drawable.about, 1, 1);
        vrSobre.setScreenPercent(50, 10);
        vrSobre.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight /2.2f);
        vrSobre.fadeIn(3000);

        vrScore = this.createSprite(R.drawable.score, 1, 1);
        vrScore.setScreenPercent(50, 10);
        vrScore.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight /3.0f);
        vrScore.fadeIn(3000);

        vrSair = this.createSprite(R.drawable.quit, 1, 1);
        vrSair.setScreenPercent(50, 10);
        vrSair.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 5.0f);
        vrSair.fadeIn(3000);

       // if (Config.som)
       // Config.tocarMusica(new Random().nextInt(2));

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {


    }

    @Override
    public void loop() {
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {

            vrGameManager.setCurrentScene(0);
            return;

        }
        if (AGInputManager.vrTouchEvents.screenClicked())
        {
            if (vrIniciar.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
               // AGSoundManager.vrSoundEffects.play(somChew);
                vrGameManager.setCurrentScene(2);
                return;
            }

            if (vrOpcoes.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.setCurrentScene(3);
                return;
            }

            if (vrSobre.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.setCurrentScene(4);
                return;
            }
            if (vrScore.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.setCurrentScene(5);
                return;
            }

            if (vrSair.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                System.exit(0);
                return;
            }

            }

        }
    }





