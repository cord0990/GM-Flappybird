
package puppy.code.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.FlappyGameMenu;

public class PauseScreen implements Screen {

    private final FlappyGameMenu game;
    private final Screen previousGame;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture bg, ground, pipeTex;

    private UIRenderer ui;
    private float worldWidth = 480;
    private float worldHeight = 800;

    public PauseScreen(FlappyGameMenu game, Screen previousGame) { this.game = game; this.previousGame = previousGame; }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);

        bg = new Texture("flappy/background.png");
        ground = new Texture("flappy/ground.png");
        pipeTex = new Texture("flappy/pipe.png");

        ui = new UIRenderer();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            game.setScreen(previousGame);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }
        ScreenUtils.clear(0,0,0,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        ui.drawPanel(batch, 40, worldHeight/2f-100, worldWidth-80, 200, 0.45f);
        ui.drawCenteredScaled(batch, "Pausa", worldWidth/2f, worldHeight/2f+40, 1.4f);
        ui.drawCenteredScaled(batch, "ESPACIO: continuar   |   ESC: menu", worldWidth/2f, worldHeight/2f-20, 1.0f);

        batch.end();
    }

    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { dispose(); }
    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose(); ground.dispose(); pipeTex.dispose();
        ui.dispose();
    }
}
