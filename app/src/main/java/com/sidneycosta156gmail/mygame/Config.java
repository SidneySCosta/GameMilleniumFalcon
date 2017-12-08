package com.sidneycosta156gmail.mygame;

import com.sidneycosta156gmail.mygame.AndGraph.AGSoundManager;

/**
 * Created by sidney on 24/11/17.
 */

public abstract class Config
{
    public static boolean som = true;

    public static void tocarMusica(int musica){
        AGSoundManager.vrMusic.stop();
        switch (musica){
            case 0:
                AGSoundManager.vrMusic.loadMusic("falcon.mp3", true);
               AGSoundManager.vrMusic.play();
                return;
            case 1:
                AGSoundManager.vrMusic.loadMusic("starwars.mp3", true);
               AGSoundManager.vrMusic.play();
                return;
            default: AGSoundManager.vrMusic.loadMusic("falcon.mp3", true);
                AGSoundManager.vrMusic.play();
                return;

        }
    }

}
