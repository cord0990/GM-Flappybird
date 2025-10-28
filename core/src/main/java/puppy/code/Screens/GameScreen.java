
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
    //private Tubo[] pipes;
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
        batch.draw(assets.getBackground(), 0, 0);
        for (Colision p : obstaculos.getColisiones()) p.draw(batch, worldHeight);
        bird.draw(batch);
        batch.draw(assets.getGround(), 0, 0);
        font.draw(batch, "Puntaje: " + score, 10, worldHeight - 10);
        batch.end();
    }

    private void update(float dt) {
    	
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game, this));
            return;
        }
        if (gameOver) {
            game.setScreen(new GameOverScreen(game));
            return;
        }
        
        bird.movimiento(dt,gravity);
        
        //Accion de cada tubo
        for (Colision p : obstaculos.getColisiones()) {
            p.update(dt);
            if (p.fueraDePantalla()) {
                float max = 0;
                for (Colision other : obstaculos.getColisiones()) if (other.getX() > max) max = other.getX();
                p.reposicionar(max + 200);
            }
            if (bird.getBounds().overlaps(p.getBounds()[0]) || bird.getBounds().overlaps(p.getBounds()[1])) {
                gameOver = true;
                if (score > game.getHigherScore()) {game.setHigherScore(score);}
            }
            if (!gameOver && bird.pos.x > p.getX() + assets.getTuboTex().getWidth()/2f && bird.pos.x - dt * p.getVelocidad() <= p.getX() + assets.getTuboTex().getWidth()/2f) {
                score++;
                if (score >= 100) {
                    if (score > game.getHigherScore()) game.setHigherScore(score);
                    game.setScreen(new EndScreen(game, score));
                    return;
                }
            }
        }
        if (bird.pos.y <= 96 || bird.pos.y + 24 >= worldHeight) {
            gameOver = true;
            if (score > game.getHigherScore()) game.setHigherScore(score);
        }
    }

    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { /* keep state when hidden (pause) */ }
    @Override
    public void dispose() {
        batch.dispose(); font.dispose();
        assets.getBackground().dispose(); assets.getGround().dispose(); assets.getTuboTex().dispose();
        for (Texture t : assets.getBirdFrames()) t.dispose();
    }
}
