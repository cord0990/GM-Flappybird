package puppy.code.Screens.UIBase;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;

import puppy.code.Asset;
import puppy.code.FlappyGameMenu;

public abstract class BaseUIScreen implements Screen {

    // Recursos compartidos entre pantallas (para ahorrar memoria/CPU).
    protected static SpriteBatch sharedBatch;
    protected static BitmapFont sharedFont;
    protected static GlyphLayout sharedLayout;
    protected static Texture sharedPixel; // 1x1 blanco para paneles

    protected final FlappyGameMenu game;
    protected OrthographicCamera camera;
    protected float worldWidth = 480f;
    protected float worldHeight = 800f;
    protected Asset assets;

    protected BaseUIScreen(FlappyGameMenu game) {
        this.game = game;
    }

    /** Llama esto en show() de las subclases con el Asset proveniente de GameScreen.getAssets() */
    protected void initBase(Asset assets) {
        this.assets = assets;

        if (sharedBatch == null) sharedBatch = new SpriteBatch();
        if (sharedFont == null)  sharedFont  = new BitmapFont();
        if (sharedLayout == null) sharedLayout = new GlyphLayout();
        if (sharedPixel == null) {
            Pixmap pm = new Pixmap(1,1, Pixmap.Format.RGBA8888);
            pm.setColor(Color.WHITE);
            pm.fill();
            sharedPixel = new Texture(pm);
            pm.dispose();
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, worldWidth, worldHeight);
    }

    // --------- Utilidades UI (evitan UIRenderer externo) ----------
    protected void drawPanel(SpriteBatch batch, float x, float y, float w, float h, float alpha) {
        Color old = batch.getColor();
        batch.setColor(0, 0, 0, alpha);
        batch.draw(sharedPixel, x, y, w, h);
        batch.setColor(old);
    }

    protected void drawCenteredScaled(SpriteBatch batch, String text, float centerX, float centerY, float scale) {
        sharedFont.getData().setScale(scale);
        sharedLayout.setText(sharedFont, text);
        float x = centerX - sharedLayout.width / 2f;
        float y = centerY + sharedLayout.height / 2f;
        sharedFont.draw(batch, sharedLayout, x, y);
        sharedFont.getData().setScale(1f);
    }

    // Por defecto, las subclases no disponen nada compartido aquí.
    @Override public void dispose() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
