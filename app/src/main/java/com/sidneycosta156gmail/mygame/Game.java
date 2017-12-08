package com.sidneycosta156gmail.mygame;

import android.os.Bundle;

import com.sidneycosta156gmail.mygame.AndGraph.AGActivityGame;
import com.sidneycosta156gmail.mygame.AndGraph.AGGameManager;

public class Game extends AGActivityGame {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instancia do objeto manager
        vrManager = new AGGameManager(this, true);

        //instancia das cenas graficas
        CenaAbertura abertura = new CenaAbertura(vrManager); // instancia da cena  abertura
        CenaMenu menu = new CenaMenu(vrManager);   // instancia da cena do menu
        CenaGame game = new CenaGame(vrManager);   // instancia da cena game
        CenaConfiguracao configuracao = new CenaConfiguracao(vrManager);  //instancia da cena config
        CenaSobre sobre = new CenaSobre(vrManager); // instancia da cena sobre
        Score score = new Score(vrManager); // instancia do score
        CenaGameOver cenaGameOver = new CenaGameOver(vrManager); // instancia do game Over

//adicionando as cenas
        vrManager.addScene(abertura); // 0 ==> primeira tela
        vrManager.addScene(menu);    //  1 ==> segunda tela
        vrManager.addScene(game);   // 2 ==> terceira tela
        vrManager.addScene(configuracao); //3 ==> quarta tela
        vrManager.addScene(sobre); //4 ==> quinta tela
        vrManager.addScene(score); //5 ==> sexta tela
        vrManager.addScene(cenaGameOver); //6 ==> sexta tela
    }
}
