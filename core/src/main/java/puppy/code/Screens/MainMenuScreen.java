
package puppy.code.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.FlappyGameMenu;

public class MainMenuScreen implements Screen {

    private final FlappyGameMenu game;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private Texture bg, ground, pipeTex, birdTex;
    private UIRenderer ui;
    private float worldWidth = 480;
    private float worldHeight = 800;

    public MainMenuScreen(FlappyGameMenu game) { this.game = game; }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);

        bg = new Texture("flappy/background.png");
        ground = new Texture("flappy/ground.png");
        pipeTex = new Texture("flappy/pipe.png");
        birdTex = new Texture("flappy/bird0.png");

        ui = new UIRenderer();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
            return;
        }

        ScreenUtils.clear(0,0,0,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        // Header
        ui.drawPanel(batch, 0, worldHeight-110, worldWidth, 110, 0.35f);
        ui.drawCenteredScaled(batch, "¡Como Jugar!", worldWidth/2f, worldHeight-60, 1.4f);

        // Icons + tips
        batch.setColor(1,1,1,0.95f);
        batch.draw(birdTex, 80, worldHeight/2f-40, 48, 34);
        batch.draw(pipeTex, worldWidth-160, worldHeight/2f-240, 72, 240);
        batch.setColor(Color.WHITE);
        ui.drawCenteredScaled(batch, "Presiona espacio para saltar", 160, worldHeight/2f-100, 0.9f);
        ui.drawCenteredScaled(batch, "Esquiva los tubos (luego pinchos)", worldWidth-120, worldHeight/2f-100, 0.9f);

        // Footer prompt
        ui.drawPanel(batch, 0, 0, worldWidth, 80, 0.35f);
        ui.drawCenteredScaled(batch, "Presiona ESPACIO o CLICK para empezar", worldWidth/2f, 40, 1.0f);

        batch.end();
    }

    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { dispose(); }
    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose(); ground.dispose(); pipeTex.dispose(); birdTex.dispose();
        ui.dispose();
    }
}
