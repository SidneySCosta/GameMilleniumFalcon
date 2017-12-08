package com.sidneycosta156gmail.mygame;

import com.sidneycosta156gmail.mygame.AndGraph.AGGameManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGInputManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGScene;
import com.sidneycosta156gmail.mygame.AndGraph.AGScreenManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGSprite;
import com.sidneycosta156gmail.mygame.AndGraph.AGTimer;

/**
 * Created by sidney on 23/11/17.
 */

public class CenaGameOver extends AGScene
{
    AGSprite imagemfundo = null;
    AGSprite imagem = null;  // inseri imagem no fundo
    AGTimer intervalotempo = null; // tempo para trocar de cena

    public CenaGameOver(AGGameManager manager )
    {
        super(manager);
    }

    @Override
    public void init() {
        imagemfundo = createSprite(R.drawable.gameover, 1,1); // carregar imagem
        imagemfundo.setScreenPercent(100, 100); // percentual imagem ocupa na tela
        imagemfundo.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2); // percentual que a imagem vai ocupar na tela

        setSceneBackgroundColor(1,1,1);

        intervalotempo = new AGTimer(2000);

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

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
