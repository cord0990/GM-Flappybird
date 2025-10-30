package puppy.code.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.Asset;
import puppy.code.FlappyGameMenu;
import puppy.code.Screens.UIBase.BackgroundRenderer;
import puppy.code.Screens.UIBase.BaseUIScreen;

public class MainMenuScreen extends BaseUIScreen {

    private BackgroundRenderer bgRenderer;
    private Asset localAssets; // Assets compartidos hacia el GameScreen

    public MainMenuScreen(FlappyGameMenu game) {
        super(game);
    }

    @Override
    public void show() {
        // Cargamos una sola vez los assets y los compartimos con el GameScreen.
        if (this.localAssets == null) {
            this.localAssets = new Asset();
        }
        initBase(localAssets);
        this.bgRenderer = new BackgroundRenderer(this.assets, worldWidth, worldHeight);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            GameScreen gs = new GameScreen(game);
            gs.setAssets(localAssets);
            game.setScreen(gs);
            return;
        }

        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        sharedBatch.setProjectionMatrix(camera.combined);
        sharedBatch.begin();

        // Fondo + suelo
        bgRenderer.render(sharedBatch);

        // Asegurar color blanco ANTES de texturas decorativas (por si algún draw anterior cambió el color)
        sharedBatch.setColor(1f, 1f, 1f, 1f);

        // Tubo decorativo
        if (assets.getTuboTex() != null) {
            float tuboX = worldWidth - 140f;
            float tuboY = 230f;
            sharedBatch.draw(assets.getTuboTex(), tuboX, tuboY);
        }

        // Pajarito decorativo
        if (assets.getBirdFrames() != null && assets.getBirdFrames().length > 0 && assets.getBirdFrames()[0] != null) {
            float birdW = 48f, birdH = 34f;
            float birdX = worldWidth / 4f - birdW / 2f;
            float birdY = worldHeight / 2f - 75f;
            sharedBatch.draw(assets.getBirdFrames()[0], birdX, birdY, birdW, birdH);
        }

        // Paneles + textos (estos sí cambian el color internamente, pero se restauran en drawPanel)
        drawPanel(sharedBatch, 0, worldHeight - 110, worldWidth, 110, 0.35f);
        drawCenteredScaled(sharedBatch, "¡Cómo Jugar!", worldWidth / 2f, worldHeight - 60, 1.4f);

        drawCenteredScaled(sharedBatch, "Presiona espacio para saltar", 130, worldHeight / 2f - 100, 0.9f);
        drawCenteredScaled(sharedBatch, "Esquiva los tubos", worldWidth - 120, worldHeight / 2f - 100, 0.9f);

        drawPanel(sharedBatch, 0, 0, worldWidth, 80, 0.35f);
        drawCenteredScaled(sharedBatch, "Presiona ESPACIO o CLICK para empezar", worldWidth / 2f, 40, 1.0f);

        sharedBatch.end();
    }


    // No disponer aquí assets compartidos; se usan en otras pantallas.
    @Override public void hide() { }
    @Override public void dispose() { }
}
