
package puppy.code.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.Asset;
import puppy.code.Character;
import puppy.code.FlappyGameMenu;
import puppy.code.Obstaculo;
import puppy.code.Colisiones.Colision;

public class GameScreen implements Screen {

	public static final float worldHeight = 512;

	private final FlappyGameMenu game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private Character bird;


	private Obstaculo obstaculos;
    private float worldWidth = 288;
    private float gravity = -600f;
    private int score = 0;


	private boolean gameOver = false;
    private boolean initialized = false;
    
    private Asset assets;


	public GameScreen(final FlappyGameMenu game) { this.game = game; }

    
@Override
	public void show() {
	    if (initialized) return;
	    batch = new SpriteBatch();
	    font = new BitmapFont();
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, worldWidth, worldHeight);
	
	    assets = new Asset();
	    
	    //Creacion
	    bird = new Character(20,350,assets.getBirdFrames()); //Donde se crea y sus sprites
	
	    obstaculos = new Obstaculo(assets);	
	    score = 0;
	    gameOver = false;
	    initialized = true;
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(assets.getBackground(), 0, 0, worldWidth, worldHeight);
        for (Colision p : obstaculos.getColisiones()) p.draw(batch, worldHeight);
        bird.draw(batch);
        batch.draw(assets.getGround(), 0, 0);
        font.draw(batch, "Puntaje: " + score, 10, worldHeight - 10);
        batch.end();
    }

    private void update(float dt) {
    	
    	
    	if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
    		this.pause();
    	}
    	
    	accesoPantallaGameOver(game); //accede si se cumple la condicion de acceso
        
        bird.movimiento(dt,gravity); // Genera el movimiento del pajaro en gameScreen.
        
        //Accion de cada tubo
        obstaculos.actualizarColision(dt,this.game, this); //Accion de cada tubo
        
        bird.fueraDePantalla(this, game);
    }

    @Override public void resize(int width, int height) { }
    
    @Override 
    public void pause() { 
    	game.setScreen(new PauseScreen(game, this));
        return;
    }
    @Override public void resume() { }
    @Override public void hide() { /* keep state when hidden (pause) */ }

    
    //Acceso a pantallas
    
    public void accesoPantallaGameOver(FlappyGameMenu game) {
    	if (gameOver) {
            game.setScreen(new GameOverScreen(game, this));
            return;
        }
    }
    
    //getters y setters
    
    public boolean getGameOver() {
    	return this.gameOver;
    }
    public void setGameOver(boolean b) {
		this.gameOver = b;
		
	}
    
    public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public Asset getAssets() {
		return assets;
	}

	public void setAssets(Asset assets) {
		this.assets = assets;
	}
	
	public Character getBird() {
		return bird;
	}


	public void setBird(Character bird) {
		this.bird = bird;
	}
	
	public static float getWorldheight() {
		return worldHeight;
	} 
    
    
    public void dispose() {
        batch.dispose(); font.dispose();
        assets.getBackground().dispose(); assets.getGround().dispose(); assets.getTuboTex().dispose();
        for (Texture t : assets.getBirdFrames()) t.dispose();
    }


}
