
package puppy.code.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.FlappyGameMenu;

public class EndScreen implements Screen {

    private final FlappyGameMenu game;
    private final int score;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Texture bg, ground, pipeTex, birdTex;
    private UIRenderer ui;
    private float worldWidth = 480;
    private float worldHeight = 800;

    public EndScreen(FlappyGameMenu game, int score) { this.game = game; this.score = score; }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);

        bg = new Texture("flappy/background.png");
        ground = new Texture("flappy/ground.png");
        pipeTex = new Texture("flappy/pipe.png");
        birdTex = new Texture("flappy/bird2.png");

        ui = new UIRenderer();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }
        ScreenUtils.clear(0,0,0,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        ui.drawPanel(batch, 40, worldHeight/2f-100, worldWidth-80, 200, 0.45f);
        ui.drawCenteredScaled(batch, "¡Victoria!", worldWidth/2f, worldHeight/2f+40, 1.4f);
        ui.drawCenteredScaled(batch, "Puntaje: " + score + "   |   ESPACIO para volver", worldWidth/2f, worldHeight/2f-20, 1.0f);
        batch.draw(birdTex, worldWidth/2f-24, worldHeight/2f+60, 48, 34);

        batch.end();
    }

    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { dispose(); }
    @Override
    public void dispose() {
        batch.dispose(); font.dispose();
        bg.dispose(); ground.dispose(); pipeTex.dispose(); birdTex.dispose();
        ui.dispose();
    }
}
