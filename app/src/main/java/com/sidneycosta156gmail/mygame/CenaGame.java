package com.sidneycosta156gmail.mygame;
import android.content.SharedPreferences;

import com.sidneycosta156gmail.mygame.AndGraph.AGGameManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGInputManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGScene;
import com.sidneycosta156gmail.mygame.AndGraph.AGScreenManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGSoundManager;
import com.sidneycosta156gmail.mygame.AndGraph.AGSprite;
import com.sidneycosta156gmail.mygame.AndGraph.AGTimer;
import com.sidneycosta156gmail.mygame.AndGraph.AGVector2D;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sidney on 24/11/17.
 */

public class CenaGame extends AGScene {
    private ArrayList<AGSprite> arrayNaves = null;
    private ArrayList<AGSprite> arrayTiros = null;
    private ArrayList<AGSprite> arrayExplosoes = null;
    private AGSprite navePrincipal = null;
    private AGSprite superExplosao = null;
    private AGSprite[] imagemFundo = null;

    private int somExplosao = 0;             // variavel para o som de explosao das naves inimigas (TIE FIGHTS)
    private int somExplosaoPrincipal = 0;   // varivael para o som de explosao da nave  principal (MILLENNIUM FALCON)
    private int somTiroNavePrincipal = 0;  // varivael para o som do tiro da nave principal (MILLENNIUM FALCON)
    private Random sorteiaPosicao = null;

    private AGSprite[] placar = null;
    private AGTimer tempoNave = null;
    private int numeroColisoes = 0;
    private AGTimer tempoGameOver = null;
    private int iAux = 0; //variavel para score

    public CenaGame(AGGameManager manager) {
        super(manager);
    }

    @Override
    public void init()
    {
        this.setSceneBackgroundColor(0, 0, 0);
        this.arrayNaves = new ArrayList<>();
        this.arrayTiros = new ArrayList<>();
        this.arrayExplosoes = new ArrayList<>();
        this.superExplosao = null;
        this.sorteiaPosicao = new Random(AGScreenManager.iScreenWidth);
        this.imagemFundo = new AGSprite[2];

        tempoNave = new AGTimer(2000);
        numeroColisoes = 0;


        imagemFundo[0] = this.createSprite(R.drawable.stars, 1, 1);
        imagemFundo[0].setScreenPercent(110, 110);
        imagemFundo[0].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);


        imagemFundo[1] = this.createSprite(R.drawable.stars, 1, 1);
        imagemFundo[1].setScreenPercent(110, 110);
        imagemFundo[1].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, imagemFundo[0].getSpriteHeight() + imagemFundo[0].vrPosition.getY());

        //nave principal (Millenium Falcon)
        navePrincipal = this.createSprite(R.drawable.millenium, 1, 1);
        navePrincipal.setScreenPercent(30, 20);
        navePrincipal.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, (navePrincipal.getSpriteHeight() / 2));
        navePrincipal.bAutoRender = false;


        //carregando imagen na memoria
        createSprite(R.drawable.explosion, 9, 9).bVisible = false;   // sprite de explosões
        createSprite(R.drawable.bala, 1, 1).bVisible = false;       // imagem tiro da nave principal (MILLENNIUM FALCON)
        createSprite(R.drawable.tiefc2, 1, 1).bVisible = false;     // imagem nave inimiga (TIE FIGHTERS)

        //instancia o placar
        placar = new AGSprite[3];
        for (int i = 2; i >= 0; i--) {
            placar[i] = createSprite(R.drawable.fonte, 4, 4);
            placar[i].setScreenPercent(10, 10);
            placar[i].bAutoRender = false;


            for (int quadro = 0; quadro < 10; quadro++) {
                placar[i].addAnimation(1, true, quadro);
            }
        }
        placar[0].vrPosition.setXY(AGScreenManager.iScreenWidth - placar[0].getSpriteWidth()/3,
                AGScreenManager.iScreenHeight - placar[0].getSpriteHeight() / 2.5f);//primeira posicao do placar
        placar[1].vrPosition.setXY(placar[0].vrPosition.getX() - placar[1].getSpriteWidth() / 2.5f,
                AGScreenManager.iScreenHeight - placar[0].getSpriteHeight() / 2.5f);//segunda posicao do placar
        placar[2].vrPosition.setXY(placar[1].vrPosition.getX() - placar[1].getSpriteWidth() / 2.5f,
                AGScreenManager.iScreenHeight - placar[0].getSpriteHeight() / 2.5f);//segunda posicao do placar

        placar[0].setCurrentAnimation(0);
        placar[1].setCurrentAnimation(0);
        placar[2].setCurrentAnimation(0);

        if (Config.som)
        {
            Config.tocarMusica(new Random().nextInt(2));
        }
        this.somExplosao = AGSoundManager.vrSoundEffects.loadSoundEffect("explosao.mp3");
        this.somExplosaoPrincipal = AGSoundManager.vrSoundEffects.loadSoundEffect("fight.mp3"); // nave principal
        somTiroNavePrincipal = AGSoundManager.vrSoundEffects.loadSoundEffect("milennium.mp3");  //som de tiro da nave principal

    }
//render do placar
    public void render() {
        super.render();
        placar[0].render();
        placar[1].render();
        placar[2].render();
        navePrincipal.render();
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }


    @Override
    public void loop() {

        atualizaFundo();
        // ###  ACELEROMETER  #####
        //movimento da nave
        if (navePrincipal.vrPosition.getX() < (AGScreenManager.iScreenWidth - navePrincipal.getSpriteWidth() / 2) && AGInputManager.vrAccelerometer.getAccelX() > 2) {
            navePrincipal.vrPosition.setX(navePrincipal.vrPosition.getX() + 20);

        } else if (navePrincipal.vrPosition.getX() > navePrincipal.getSpriteWidth() / 2 && AGInputManager.vrAccelerometer.getAccelX() < -2) {
            navePrincipal.vrPosition.setX(navePrincipal.vrPosition.getX() - 20);


        }


        if (superExplosao != null && superExplosao.getCurrentAnimation().isAnimationEnded()) {
            if (Config.som) {
                Config.tocarMusica(new Random().nextInt(2));
            }

            vrGameManager.setCurrentScene(6);
            return;
        }

        atualizaTiro();
        atualizaNave();
        dificuldade();
        colide();
        atualizaPlacar();
        atualizaExplosao();
        colideNaveMae();

        if (AGInputManager.vrTouchEvents.backButtonClicked()) {

            if (Config.som) {
                Config.tocarMusica(new Random().nextInt(2));
            }
            salvaScore();
            vrGameManager.setCurrentScene(1);
            return;
        }


        if (AGInputManager.vrTouchEvents.screenClicked() && navePrincipal.bVisible) {
            AGSoundManager.vrSoundEffects.play(somTiroNavePrincipal);
            criaTiro();
        }


    }

    //################### TIROS ############################
    public void criaTiro() {

        for (AGSprite reciclado : arrayTiros) {

            if (reciclado.bRecycled) {
                reciclado.vrPosition.setXY(navePrincipal.vrPosition.getX(), navePrincipal.getSpriteHeight());
                reciclado.bRecycled = false;
                reciclado.bVisible = true;
                if (Config.som) {
                    AGSoundManager.vrSoundEffects.play(somExplosao);
                }
                return;
            }
        }

        AGSprite tiro = null;
        tiro = createSprite(R.drawable.bala, 2, 4);
        tiro.setScreenPercent(8, 3);
        tiro.vrPosition.setXY(navePrincipal.vrPosition.getX(), navePrincipal.getSpriteHeight());


        arrayTiros.add(tiro);
    }

    public void atualizaTiro() {

        for (AGSprite tiro : arrayTiros) {
            tiro.vrPosition.setY(tiro.vrPosition.getY() + 20);


            //reciclagem do tiro
            if (tiro.vrPosition.getY() > AGScreenManager.iScreenHeight + tiro.getSpriteHeight()) {
                tiro.bRecycled = true;
                tiro.bVisible = false;
            }
        }
    }
//atualiza imagem principal do fundo do jogo
    public void atualizaFundo() {

        this.imagemFundo[0].vrPosition.setY(imagemFundo[0].vrPosition.getY() - 25);
        this.imagemFundo[1].vrPosition.setY(imagemFundo[1].vrPosition.getY() - 25);


        if (imagemFundo[0].vrPosition.getY() + (imagemFundo[0].getSpriteHeight() / 2) < 0) {
            this.imagemFundo[0].vrPosition.setY((imagemFundo[0].getSpriteHeight() / 2) + AGScreenManager.iScreenHeight);
        }
        if (imagemFundo[1].vrPosition.getY() + (imagemFundo[1].getSpriteHeight() / 2) < 0) {
            this.imagemFundo[1].vrPosition.setY((imagemFundo[1].getSpriteHeight() / 2) + AGScreenManager.iScreenHeight);
        }
    }


    //###############  NAVES INIMIGAS (TIE FIGHTERS) #################################
    public void criaNave() {

        for (AGSprite reciclado : arrayNaves) {
            if (reciclado.bRecycled) {
                reciclado.vrPosition.setXY((reciclado.getSpriteWidth() / 2) + sorteiaPosicao.nextInt((int) (AGScreenManager.iScreenWidth - reciclado.getSpriteWidth())), AGScreenManager.iScreenHeight + reciclado.getSpriteHeight());
                reciclado.bRecycled = false;
                reciclado.bVisible = true;
                return;
            }
        }
        AGSprite nave = null;
        nave = createSprite(R.drawable.tiefc2, 2, 4);
        nave.setScreenPercent(15, 8);
        nave.vrPosition.setXY((nave.getSpriteWidth() / 2) + sorteiaPosicao.nextInt((int) (AGScreenManager.iScreenWidth - nave.getSpriteWidth())), AGScreenManager.iScreenHeight + nave.getSpriteHeight());

        arrayNaves.add(nave);

    }

    public void atualizaNave() {

        for (AGSprite nave : arrayNaves) {
            nave.vrPosition.setY(nave.vrPosition.getY() - 13);

            //reciclagem da nave
            if (nave.vrPosition.getY() < -(nave.getSpriteHeight() / 2)) {
                nave.bRecycled = true;
                nave.bVisible = false;
            }
        }
    }

    public void colideNaveMae() {
        for (AGSprite nave : arrayNaves) {

            if (!nave.bRecycled && navePrincipal.collide(nave) && navePrincipal.bVisible) {
                if (Config.som) {
                    AGSoundManager.vrSoundEffects.play(somExplosaoPrincipal);
                }
                criaSuperExplosao();

            }

        }
    }

    //################# COLISÃO & EXPLOSÃO ###########################

    public void colide() {

        for (AGSprite nave : arrayNaves) {

            if (!nave.bRecycled) {
                for (AGSprite tiro : arrayTiros) {

                    if (!tiro.bRecycled && nave.collide(tiro.vrPosition)) {
                        nave.bRecycled = true;
                        nave.bVisible = false;

                        tiro.bRecycled = true;
                        tiro.bVisible = false;
                        criaExplosao(nave.vrPosition);
                        if (Config.som) {
                            AGSoundManager.vrSoundEffects.play(somExplosao);
                        }

                        numeroColisoes++;

                    }
                }

            }

        }
    }

    public void criaExplosao(AGVector2D posicao) {


        for (AGSprite reciclado : arrayExplosoes) {

            if (reciclado.bRecycled) {
                reciclado.vrPosition.setXY(posicao.getX(), posicao.getY());
                reciclado.bRecycled = false;
                reciclado.bVisible = true;
                reciclado.getCurrentAnimation().restart();
                return;
            }
        }
        AGSprite exposao = null;
        exposao = createSprite(R.drawable.explosion, 9, 9);
        exposao.addAnimation(100, false, 0, 72);
        exposao.setScreenPercent(20, 10);
        exposao.vrPosition.setXY(posicao.getX(), posicao.getY());
        arrayExplosoes.add(exposao);

    }

    public void atualizaExplosao() {

        for (AGSprite explosao : arrayExplosoes) {

            //quando a nave sumir, ele será reciclada
            if (explosao.getCurrentAnimation().isAnimationEnded()) {
                explosao.bRecycled = true;
                explosao.bVisible = false;
            }
        }
    }

    //######################### PLACAR DO PLAYER ################################

    public void atualizaPlacar() {

        if(numeroColisoes<100){
            placar[1].setCurrentAnimation(numeroColisoes / 10);
            placar[0].setCurrentAnimation(numeroColisoes % 10);
        }
        else{
            placar[2].setCurrentAnimation(Integer.parseInt(String.valueOf(numeroColisoes).substring(0,1)));
            placar[1].setCurrentAnimation(Integer.parseInt(String.valueOf(numeroColisoes).substring(1,2)));
            placar[0].setCurrentAnimation(Integer.parseInt(String.valueOf(numeroColisoes).substring(2,3)));
        }
    }

    public void criaSuperExplosao() {
        superExplosao = createSprite(R.drawable.explosion, 9, 9); //sprite da explosão principal
        superExplosao.addAnimation(100, false, 0, 80);
        superExplosao.setScreenPercent(20, 10);
        superExplosao.vrPosition.setXY(navePrincipal.vrPosition.getX(), navePrincipal.vrPosition.getY());

        navePrincipal.bVisible = false;
        salvaScore();

    }

    //######################  DIFICULDADES ########################

    public void dificuldade() {
        tempoNave.update();
        if (tempoNave.isTimeEnded()) {
            criaNave();

            if (numeroColisoes <= 15) {
                tempoNave.restart(2000);//2000


            } else if (numeroColisoes <= 25) {
                tempoNave.restart(1750);//1750


            } else if (numeroColisoes <= 35) {
                tempoNave.restart(1500);//1500

            } else if (numeroColisoes <= 45) {
                tempoNave.restart(1250);//1250

            } else if (numeroColisoes <= 55) {
                tempoNave.restart(900);//900

            } else if (numeroColisoes <= 70) {
                tempoNave.restart(500);//500

            } else {
                tempoNave.restart();

            }

        }
    }

    //############# METODO PARA SALVAR OS ṔONTOS FEITOS PELO USUARIO ####################################

    private void salvaScore(){
        try{
            //se os pontos que estão salvos for maior que o placar
            if(iAux > numeroColisoes){
                //placar recebe esses pontos
                numeroColisoes = iAux;
            }
            //Cria objeto responsavel pela gravacao dos dados em arquivo
            SharedPreferences vrShared = vrGameManager.vrActivity.getSharedPreferences("BD_TextOfColors", vrGameManager.vrActivity.MODE_PRIVATE);
            //Cria o editor para escrita de dados
            SharedPreferences.Editor vrEditor = vrShared.edit();
            //Escreve os dados no Shared e confirma
            vrEditor.putInt("score", numeroColisoes);
            vrEditor.commit();
        }
        catch (Exception e){

        }
    }



}

