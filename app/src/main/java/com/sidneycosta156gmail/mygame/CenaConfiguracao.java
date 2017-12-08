package com.sidneycosta156gmail.mygame;

import com.sidneycosta156gmail.mygame.AndGraph.AGGameManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGInputManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGScene;
import com.sidneycosta156gmail.mygame.AndGraph.AGScreenManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGSoundManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGSprite;

import java.util.Random;

/**
 * Created by sidney on 24/11/17.
 */

public class CenaConfiguracao extends AGScene{
    AGSprite imagemfundo = null;
    AGSprite som = null;

    public CenaConfiguracao(AGGameManager manager) { super(manager);}

    @Override
    public void init()
    {
        this.imagemfundo = this.createSprite(R.drawable.fundo6, 1, 1);
        imagemfundo.setScreenPercent(100, 100);
        imagemfundo.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);



        if(Config.som){
            this.som = this.createSprite(R.drawable.off,1,1);
        }
        else{
            this.som = this.createSprite(R.drawable.som,1,1);
        }

        this.som.setScreenPercent(30,15);
        this.som.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);
        this.som.fadeIn(3000);

    }

    @Override
    public void restart() {

    }
    public void render() {
        super.render();

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop()
    {


        if(AGInputManager.vrTouchEvents.backButtonClicked())
        {
            vrGameManager.setCurrentScene(1);
            return;
        }

        if(AGInputManager.vrTouchEvents.screenClicked()){

            if(this.som.collide(AGInputManager.vrTouchEvents.getLastPosition())){

                if(Config.som){
                    AGSoundManager.vrMusic.stop();

                    Config.som = false;
                }

                else {

                    Config.som = true;
                    Config.tocarMusica(new Random().nextInt(2));
                }

                vrGameManager.setCurrentScene(1);

            }

        }

    }

    }

