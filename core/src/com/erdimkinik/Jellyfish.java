package com.erdimkinik;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Jellyfish{
    public int enemySet = 6;
    public float[] jellyfish1Y = new float[enemySet];
    public float[] jellyfish2Y = new float[enemySet];
    public float[] jellyfish3Y = new float[enemySet];
    public float[] jellyfishX = new float[enemySet];
    public Rectangle[] jellyfish1YRect = new Rectangle[enemySet];
    public Rectangle[] jellyfish2YRect = new Rectangle[enemySet];
    public Rectangle[] jellyfish3YRect =  new Rectangle[enemySet];



    public void jellyFishCreate(int i){

        //jellyfish1
        if (i == 0){
            jellyfish1Y[0] = Gdx.graphics.getHeight() / 6f;
            jellyfish2Y[0] = Gdx.graphics.getHeight() / 2.5f;
            jellyfish3Y[0] = Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/7f;
        }

        //jellyfish 2
        if (i == 1){
            jellyfish1Y[1] = Gdx.graphics.getHeight() / 6f;
            jellyfish2Y[1] = Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/3f;
            jellyfish3Y[1] = Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/7f;
        }

        //jellyfish 3
        if (i == 2){
            jellyfish1Y[2] =  Gdx.graphics.getHeight() / 7f;
            jellyfish2Y[2] =  Gdx.graphics.getHeight() / 2f;
            jellyfish3Y[2] =  Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/3f;
        }

        //jellyfish 4
        if (i == 3){
            jellyfish1Y[3] = Gdx.graphics.getHeight() / 2f;
            jellyfish2Y[3] = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3f;
            jellyfish3Y[3] = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 7f;
        }
        //jellyfish 5
        if (i == 4){
            jellyfish1Y[4] = Gdx.graphics.getHeight() / 7f;
            jellyfish2Y[4] = Gdx.graphics.getHeight() / 2f;
            jellyfish3Y[4] = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 5f ;
        }
        //jellyfish 6
        if (i == 5){
            jellyfish1Y[5] = Gdx.graphics.getHeight() / 7f;
            jellyfish2Y[5] = Gdx.graphics.getHeight() / 2f;
            jellyfish3Y[5] = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3f ;
        }

    }

}
