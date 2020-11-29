package com.erdimkinik;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Game extends ApplicationAdapter {
	// game
	SpriteBatch batch;
	Texture background;
	Texture jellyfish;
	int gameState;
	int score;
	int enemyScored;
	int gameLevel;
	int check;
	int startBest;
	int bestScore;

	BitmapFont scoreTable;
	BitmapFont level;
	BitmapFont gameOver;
	BitmapFont bestScoreTable;
	BitmapFont tapToStart;
	BitmapFont bestShowStart;
	BitmapFont goScore;
	BitmapFont goBestScore;
	Preferences prefs ;
	FreeTypeFontGenerator fontGen;

	//fish
	Texture fish;
	float velocity;
	float gravity;
	float fishY;
	float fishX;
	// jellyfish
	Jellyfish jellyCreate;
	int enemySet;
	float distance;
	float jellyVelocity;

	//Circles
	Circle fishCircle;
	Rectangle[] jellyfish1YRect;
	Rectangle[] jellyfish2YRect;
	Rectangle[] jellyfish3YRect;
	ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		// game
		batch = new SpriteBatch();
		background = new Texture("background.png");
		fish = new Texture("fish.png");
		jellyfish = new Texture("jellyfish.png");
		gameState = 0;
		score = 0;

		gameLevel = 1;
		enemyScored = 0;
		//Preferences
		prefs = Gdx.app.getPreferences("GameSaveData");

		//fish
		velocity = 1.1f;
		gravity = 0.4f;
		fishY = Gdx.graphics.getHeight() / 2f;
		fishX = Gdx.graphics.getWidth() / 8f;

		//jellyfish
		jellyCreate = new Jellyfish();
		distance =  Gdx.graphics.getWidth() / 2f;
		enemySet = jellyCreate.enemySet;
		jellyVelocity = 7f;

		//Circles
		shapeRenderer = new ShapeRenderer();
		fishCircle = new Circle();
		jellyfish1YRect = jellyCreate.jellyfish1YRect;
		jellyfish2YRect = jellyCreate.jellyfish2YRect;
		jellyfish3YRect = jellyCreate.jellyfish3YRect;


		for (int i = 0 ; i < enemySet ; i++){
			jellyCreate.jellyFishCreate(i);
			jellyCreate.jellyfishX[i] = (distance * i) + Gdx.graphics.getWidth();
			jellyfish1YRect[i] = new Rectangle();
			jellyfish2YRect[i] = new Rectangle();
			jellyfish3YRect[i] = new Rectangle();
		}
		//FontTypes...
		fontGen = new FreeTypeFontGenerator(Gdx.files.internal("robotobold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter tapStart = new FreeTypeFontGenerator.FreeTypeFontParameter();
		tapStart.color = Color.WHITE;
		tapStart.size = 130;
		tapStart.characters = "TAP TOSTART!";
		tapToStart = fontGen.generateFont(tapStart);

		FreeTypeFontGenerator.FreeTypeFontParameter bestStart = new FreeTypeFontGenerator.FreeTypeFontParameter();
		bestStart.color = Color.WHITE;
		bestStart.size = 100;
		bestStart.characters = "BEST SCORE: 0123456789";
		bestShowStart = fontGen.generateFont(bestStart);

		FreeTypeFontGenerator.FreeTypeFontParameter gameOverFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
		gameOverFont.color = Color.WHITE;
		gameOverFont.size = 130;
		gameOverFont.characters = "Game Over!TAPTORESTART";
		gameOver = fontGen.generateFont(gameOverFont);

		FreeTypeFontGenerator.FreeTypeFontParameter scoreTableFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
		scoreTableFont.color = Color.WHITE;
		scoreTableFont.size = 60;
		scoreTableFont.characters = "Score :0123456789";
		scoreTable = fontGen.generateFont(scoreTableFont);

		FreeTypeFontGenerator.FreeTypeFontParameter bestScoreTableFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
		bestScoreTableFont.color = Color.WHITE;
		bestScoreTableFont.size = 60;
		bestScoreTableFont.characters = "Best: 0123456789";
		bestScoreTable = fontGen.generateFont(bestScoreTableFont);

		FreeTypeFontGenerator.FreeTypeFontParameter levelFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
		levelFont.color = Color.WHITE;
		levelFont.size = 60;
		levelFont.characters = "Level : 0123456789";
		level = fontGen.generateFont(levelFont);

		FreeTypeFontGenerator.FreeTypeFontParameter goScoreFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
		goScoreFont.color = Color.WHITE;
		goScoreFont.size = 70;
		goScoreFont.characters = "YourScore :0123456789";
		goScore = fontGen.generateFont(goScoreFont);

		FreeTypeFontGenerator.FreeTypeFontParameter goBestScoreFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
		goBestScoreFont.color = Color.WHITE;
		goBestScoreFont.size = 70;
		goBestScoreFont.characters = "BestScore: 0123456789";
		goBestScore = fontGen.generateFont(goBestScoreFont);
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState == 1){
			scoreTable.draw(batch,"Score : " + score , 50f , 50f);
			if (score >= 210){
				jellyVelocity = 16f;
				level.draw(batch,"Level : Max" ,800f,50f);
			}else{
				level.draw(batch,"Level : " + gameLevel,800f,50f);
			}
			batch.draw(fish,fishX,fishY,Gdx.graphics.getWidth() / 15f , Gdx.graphics.getHeight() / 10f);
			startBest = prefs.getInteger("bestScore",bestScore);
			bestScoreTable.draw(batch,"Best : " + startBest , 1450,50);
			for (int i = 0; i < enemySet ; i++){
				batch.draw(jellyfish,jellyCreate.jellyfishX[i],jellyCreate.jellyfish1Y[i] , Gdx.graphics.getWidth() / 15f , Gdx.graphics.getHeight() / 10f);
				batch.draw(jellyfish,jellyCreate.jellyfishX[i],jellyCreate.jellyfish2Y[i] , Gdx.graphics.getWidth() / 15f , Gdx.graphics.getHeight() / 10f);
				batch.draw(jellyfish,jellyCreate.jellyfishX[i],jellyCreate.jellyfish3Y[i] , Gdx.graphics.getWidth() / 15f, Gdx.graphics.getHeight() / 10f);

				jellyfish1YRect[i] = new Rectangle(jellyCreate.jellyfishX[i] ,jellyCreate.jellyfish1Y[i] , Gdx.graphics.getWidth() / 15f , Gdx.graphics.getHeight() / 10f);
				jellyfish2YRect[i] = new Rectangle(jellyCreate.jellyfishX[i],jellyCreate.jellyfish2Y[i] , Gdx.graphics.getWidth() / 15f , Gdx.graphics.getHeight() / 10f);
				jellyfish3YRect[i] = new Rectangle(jellyCreate.jellyfishX[i] ,jellyCreate.jellyfish3Y[i] , Gdx.graphics.getWidth() / 15f , Gdx.graphics.getHeight() / 10f);

				if (jellyCreate.jellyfishX[enemyScored] < fishX){
					score++;
					check = prefs.getInteger("bestScore",bestScore);
					if (score >= check){
						prefs.putInteger("bestScore",score);
						prefs.flush();
					}

					if (enemyScored < enemySet - 1){
						enemyScored++;
					}
					else {
						enemyScored = 0;
					}
				}

				jellyCreate.jellyfishX[i] -= jellyVelocity;

				if (jellyCreate.jellyfishX[i] <= 0){
					jellyCreate.jellyfishX[i] = enemySet * distance;
				}
			}

			velocity += gravity;
			fishY -= velocity;

			if (fishY >= Gdx.graphics.getHeight() - 100f){
				fishY = Gdx.graphics.getHeight() - 100f;
				velocity = 0f;
			}
			if (fishY <= Gdx.graphics.getHeight() / 10f){
				gameState = 2;
			}

			if (Gdx.input.justTouched()){
				velocity = - 10.1f;
			}
			// LEVELS
			if (score >= 15){
				jellyVelocity = 8f;
				gameLevel = 2;
			}
			if (score >= 30){
				jellyVelocity = 9f;
				gameLevel = 3;
			}
			if (score >= 45){
				jellyVelocity = 10f;
				gameLevel = 4;
			}
			if (score >= 60){
				jellyVelocity = 11f;
				gameLevel = 5;
			}
			if (score >= 90){
				jellyVelocity = 12f;
				gameLevel = 6;
			}
			if (score >= 120){
				jellyVelocity = 13f;
				gameLevel = 7;
			}
			if (score >= 150){
				jellyVelocity = 14f;
				gameLevel = 8;
			}
			if (score >= 180){
				jellyVelocity = 15f;
				gameLevel = 9;
			}
		}
		if (gameState == 2){
			gameOver.draw(batch,"Game Over!",Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3f);
			gameOver.draw(batch,"TAP TO RESTART",Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f);
			goScore.draw(batch,"Your Score : " + score,Gdx.graphics.getWidth() / 5f , Gdx.graphics.getHeight() / 4f);
			startBest = prefs.getInteger("bestScore",bestScore);
			goBestScore.draw(batch,"Best Score : " + startBest, Gdx.graphics.getWidth() / 1.7f , Gdx.graphics.getHeight() / 4f);

			jellyVelocity = 7f;
			velocity = 0;
			gameLevel = 1;
			enemyScored = 0;
			fishY = Gdx.graphics.getHeight() / 2f;
			for (int i = 0 ; i < enemySet ; i++){
				jellyCreate.jellyfishX[i] = (distance * i) + Gdx.graphics.getWidth();
				jellyfish1YRect[i] = new Rectangle();
				jellyfish2YRect[i] = new Rectangle();
				jellyfish3YRect[i] = new Rectangle();
			}

			if (Gdx.input.justTouched()){
				score = 0;
				gameState = 1;
			}
		}
		if (gameState == 0){
			tapToStart.draw(batch,"TAP TOP START",Gdx.graphics.getWidth() / 4f , Gdx.graphics.getHeight() / 2f);
			startBest = prefs.getInteger("bestScore",bestScore);
			bestShowStart.draw(batch,"BEST SCORE :  " + startBest , Gdx.graphics.getWidth() / 4f + 70f,Gdx.graphics.getHeight() / 1.5f);
			velocity = 0;
			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		}

		batch.end();
		fishCircle.set(fishX + Gdx.graphics.getWidth() / 30f,fishY + Gdx.graphics.getHeight() / 20f,Gdx.graphics.getWidth() / 30f);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(fishCircle.x,fishCircle.y,fishCircle.radius);
		for (int i = 0 ; i < enemySet ; i++){
		/*	shapeRenderer.rect(jellyfishX[i] ,jellyfish1Y[i] , Gdx.graphics.getWidth() / 15f , Gdx.graphics.getHeight() / 10f);
			shapeRenderer.rect(jellyfishX[i] ,jellyfish2Y[i] , Gdx.graphics.getWidth() / 15f , Gdx.graphics.getHeight() / 10f);
			shapeRenderer.rect(jellyfishX[i] ,jellyfish3Y[i] , Gdx.graphics.getWidth() / 15f , Gdx.graphics.getHeight() / 10f);
		 */
			if (Intersector.overlaps(fishCircle,jellyfish1YRect[i]) || Intersector.overlaps(fishCircle,jellyfish2YRect[i]) || Intersector.overlaps(fishCircle,jellyfish3YRect[i])){
				gameState = 2;
			}
		}
		//shapeRenderer.end();
	}

	@Override
	public void dispose () {
		background.dispose();
		fish.dispose();
		jellyfish.dispose();
		fontGen.dispose();

	}
}