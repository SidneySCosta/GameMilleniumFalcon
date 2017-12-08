package com.sidneycosta156gmail.mygame;

import com.sidneycosta156gmail.mygame.AndGraph.AGGameManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGInputManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGScene;
import com.sidneycosta156gmail.mygame.AndGraph.AGScreenManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGSoundManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGSprite;

import java.util.Random;

/**
 * Created by sidney on 28/11/17.
 */

public class CenaSobre extends AGScene
{
    AGSprite imagemsobre = null;
    AGSprite vrCreditos = null;

    public CenaSobre(AGGameManager manager )
    {
        super(manager);
    }

    @Override
    public void init()
    {
        this.imagemsobre = this.createSprite(R.drawable.creditos, 1, 1);
        imagemsobre.setScreenPercent(100, 100); // percentual imagem ocupa na tela
        imagemsobre.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2); // percentual que a imagem vai ocupar na tela

        setSceneBackgroundColor(1,1,1);
        AGSoundManager.vrMusic.loadMusic("creditos.mp3", true);
        AGSoundManager.vrMusic.play();

        vrCreditos = this.createSprite(R.drawable.letras1, 1, 1);
        vrCreditos.setScreenPercent(80, 40);
        vrCreditos.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, vrCreditos.getSpriteHeight() / 1.8f);
        vrCreditos.vrDirection.setY(1);
        vrCreditos.fadeIn(3000);
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        movimentaletras();
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            AGSoundManager.vrMusic.stop();
            if (Config.som) {
                Config.tocarMusica(new Random().nextInt(2));
            }
            vrGameManager.setCurrentScene(1);
            return;
        }
    }

    public void movimentaletras(){
        vrCreditos.vrPosition.setY(vrCreditos.vrPosition.getY() + 1 *  vrCreditos.vrDirection.getY());
        if (vrCreditos.vrPosition.getY()> AGScreenManager.iScreenWidth + vrCreditos.getSpriteWidth()/2){
            vrCreditos.iMirror = AGSprite.NONE;
        }
    }
}
