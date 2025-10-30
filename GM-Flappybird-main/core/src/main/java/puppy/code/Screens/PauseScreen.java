package puppy.code.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.Asset;
import puppy.code.FlappyGameMenu;
import puppy.code.Screens.UIBase.BackgroundRenderer;
import puppy.code.Screens.UIBase.BaseUIScreen;

public class PauseScreen extends BaseUIScreen {

    private final GameScreen previousGame;
    private BackgroundRenderer bgRenderer;

    public PauseScreen(FlappyGameMenu game, GameScreen previousGame) {
        super(game);
        this.previousGame = previousGame;
    }

    @Override
    public void show() {
        // Consumimos assets ya cargados por el GameScreen.
        Asset assets = previousGame.getAssets();
        initBase(assets);
        this.bgRenderer = new BackgroundRenderer(this.assets, worldWidth, worldHeight);
    }

    @Override
    public void render(float delta) {
        // Reanudar
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            game.setScreen(previousGame);
            return;
        }
        // Salir al menú
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        sharedBatch.setProjectionMatrix(camera.combined);
        sharedBatch.begin();

        // Fondo + suelo (sin crear texturas nuevas)
        bgRenderer.render(sharedBatch);

        // Panel y textos
        drawPanel(sharedBatch, 40, worldHeight/2f - 100, worldWidth - 80, 200, 0.45f);
        drawCenteredScaled(sharedBatch, "Pausa", worldWidth/2f, worldHeight/2f + 40, 1.4f);
        drawCenteredScaled(sharedBatch, "ESPACIO: continuar   |   ESC: menú", worldWidth/2f, worldHeight/2f - 20, 1.0f);

        sharedBatch.end();
    }
}
