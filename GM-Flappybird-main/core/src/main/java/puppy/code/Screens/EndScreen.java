package puppy.code.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.Asset;
import puppy.code.FlappyGameMenu;
import puppy.code.Screens.UIBase.BackgroundRenderer;
import puppy.code.Screens.UIBase.BaseUIScreen;

public class EndScreen extends BaseUIScreen {

    private final GameScreen mainScreen; // para obtener assets ya cargados
    private final int score;
    private BackgroundRenderer bgRenderer;

    public EndScreen(FlappyGameMenu game, GameScreen mainScreen, int score) {
        super(game);
        this.mainScreen = mainScreen;
        this.score = score;
    }

    @Override
    public void show() {
        Asset assets = mainScreen.getAssets();
        initBase(assets);
        this.bgRenderer = new BackgroundRenderer(this.assets, worldWidth, worldHeight);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        sharedBatch.setProjectionMatrix(camera.combined);
        sharedBatch.begin();

        bgRenderer.render(sharedBatch);

        drawPanel(sharedBatch, 40, worldHeight/2f - 100, worldWidth - 80, 200, 0.45f);
        drawCenteredScaled(sharedBatch, "¡Victoria!", worldWidth/2f, worldHeight/2f + 40, 1.4f);
        drawCenteredScaled(sharedBatch, "Puntaje: " + score + "   |   ESPACIO para volver",
                worldWidth/2f, worldHeight/2f - 20, 1.0f);

        // Adorno opcional con frame del ave:
        // sharedBatch.draw(assets.getBirdFrames()[1], worldWidth/2f - 24, worldHeight/2f + 60, 48, 34);

        sharedBatch.end();
    }
}
