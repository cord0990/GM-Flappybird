package puppy.code.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;

import puppy.code.Asset;
import puppy.code.FlappyGameMenu;
import puppy.code.Screens.UIBase.BackgroundRenderer;
import puppy.code.Screens.UIBase.BaseUIScreen;

public class GameOverScreen extends BaseUIScreen {

    private final GameScreen mainScreen; // para obtener assets ya cargados
    private BackgroundRenderer bgRenderer;

    public GameOverScreen(FlappyGameMenu game, GameScreen mainScreen) {
        super(game);
        this.mainScreen = mainScreen;
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
            // Nuevo GameScreen (reinicio limpio); puede compartir Asset desde el menú/juego si lo pasas por ctor.
            game.setScreen(new GameScreen(game));
            return;
        }

        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        sharedBatch.setProjectionMatrix(camera.combined);
        sharedBatch.begin();

        bgRenderer.render(sharedBatch);

        drawPanel(sharedBatch, 40, worldHeight/2f - 100, worldWidth - 80, 200, 0.45f);
        drawCenteredScaled(sharedBatch, "¡Game over!", worldWidth/2f, worldHeight/2f + 40, 1.4f);
        drawCenteredScaled(sharedBatch, "Presiona ESPACIO para intentar nuevamente", worldWidth/2f, worldHeight/2f - 20, 1.0f);

        // Si quieres el pajarito como adorno y ya tienes frames:
        // sharedBatch.draw(assets.getBirdFrames()[0], worldWidth/2f - 24, worldHeight/2f + 60, 48, 34);

        sharedBatch.end();
    }
}
