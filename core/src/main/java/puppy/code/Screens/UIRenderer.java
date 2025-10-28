
package puppy.code.Screens;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

public class UIRenderer {
    private final Texture white1x1;
    private final BitmapFont font;

    public UIRenderer() {
        Pixmap pm = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.WHITE);
        pm.fill();
        white1x1 = new Texture(pm);
        pm.dispose();
        font = new BitmapFont();
    }

    public void drawPanel(SpriteBatch batch, float x, float y, float w, float h, float alpha) {
        Color prev = batch.getColor();
        batch.setColor(0f, 0f, 0f, alpha);
        batch.draw(white1x1, x, y, w, h);
        batch.setColor(prev);
    }

    public void drawCentered(SpriteBatch batch, String text, float cx, float cy) {
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, layout, cx - layout.width/2f, cy + layout.height/2f);
    }

    public void drawCenteredScaled(SpriteBatch batch, String text, float cx, float cy, float scale) {
        float prev = font.getData().scaleX;
        font.getData().setScale(scale);
        drawCentered(batch, text, cx, cy);
        font.getData().setScale(prev);
    }

    public void draw(SpriteBatch batch, String text, float x, float y) {
        font.draw(batch, text, x, y);
    }

    public void drawScaled(SpriteBatch batch, String text, float x, float y, float scale) {
        float prev = font.getData().scaleX;
        font.getData().setScale(scale);
        font.draw(batch, text, x, y);
        font.getData().setScale(prev);
    }

    public void dispose() {
        white1x1.dispose();
        font.dispose();
    }
}
