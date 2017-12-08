package com.sidneycosta156gmail.mygame;

import com.sidneycosta156gmail.mygame.AndGraph.AGGameManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGInputManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGScene;
import com.sidneycosta156gmail.mygame.AndGraph.AGScreenManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGSprite;
import com.sidneycosta156gmail.mygame.AndGraph.AGTimer;

import java.util.Random;

/**
 * Created by sidney on 23/11/17.
 */

public class CenaAbertura extends AGScene
{
    AGSprite imagemfundo = null;
    AGSprite imagem = null;  // inseri imagem no fundo
    AGTimer intervalotempo = null; // tempo para trocar de cena

    public CenaAbertura(AGGameManager manager )
    {
        super(manager);
    }

    @Override
    public void init() {
        imagemfundo = createSprite(R.drawable.stars, 1,1); // carregar imagem
        imagemfundo.setScreenPercent(100, 100); // percentual imagem ocupa na tela
        imagemfundo.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2); // percentual que a imagem vai ocupar na tela

        setSceneBackgroundColor(1,1,1);

        intervalotempo = new AGTimer(7000);

        imagem = createSprite(R.drawable.imagem2, 1,1);
        imagem.setScreenPercent(40, 40);
        imagem.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        imagem.fadeIn(6000);

        Config.tocarMusica(new Random().nextInt(2)); // randômico para as musicas aleatorias

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        if (imagem.fadeEnded())
        {
            imagem.fadeOut(3000);
        }
        intervalotempo.update();
        if (intervalotempo.isTimeEnded())
        {
            vrGameManager.setCurrentScene(1);
            return;
        }
        if (AGInputManager.vrTouchEvents.screenClicked())
        {
            vrGameManager.setCurrentScene(1);
            return;
        }

    }

}
