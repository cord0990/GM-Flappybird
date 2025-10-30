package puppy.code.Colisiones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

/**
 * Enemigo (pájaro) que sube/baja de forma oscilatoria y aletea con frames.
 * - Usa Vector2 size (mismo ancho que Tubo)
 * - Un solo bound dentro del arreglo, respetando la interfaz
 * - No crea assets: recibe frames por constructor
 */
public class Enemigo implements Colision {

    // Movimiento horizontal (similar a Tubo)
    private float velocidad = 120f;

    // Animación por frames (inyectados desde Asset)
    private final Texture[] frames;
    private int frameIndex = 0;
    private float frameTime = 0f;
    private float frameDuration = 0.12f; // ~8 FPS; ajusta a gusto

    // Posición / oscilación
    private float x;
    private float yBase;
    private float fase;
    private final float amp = 60f;    // amplitud vertical
    private final float velOsc = 2f;  // velocidad angular (rad/seg)
    private final float worldHeight;

    // Tamaño (mismo ancho que Tubo; alto proporcional al 1er frame)
    private Vector2 size;

    // Único bound dentro de la lista
    private final Rectangle[] bounds = new Rectangle[1];

    public Enemigo(Texture[] frames, float anchoTubo, float startX, float worldHeight) {
        this.frames = frames;
        this.x = startX;
        this.worldHeight = worldHeight;

        // MODIFICAR PARA TENER UN TAMAÑO FIJO
        this.size = new Vector2(33, 33); //TAMAÑO FIJO DE ENEMIGO

        // Centro base aleatorio (con margen para no pegar techo/suelo)
        float groundY = 90f; // altura del suelo
        float topLimit = worldHeight - 50f; // límite superior del vuelo
        float bottomLimit = groundY + 50f;  // límite inferior del vuelo

        // Calcula la posición base del centro entre límites válidos
        this.yBase = MathUtils.random(bottomLimit + amp, topLimit - amp);

        this.fase = MathUtils.random(0f, MathUtils.PI2);

        // Bound único
        bounds[0] = new Rectangle(x, yBase - size.y / 2f, size.x, size.y);
    }

    @Override
    public void update(float delta) {
        // Avance horizontal
        x -= velocidad * delta;

        // Oscilación vertical
        fase += velOsc * delta;
        float yCentro = yBase + amp * MathUtils.sin(fase);
        float yInferior = yCentro - (size.y / 2f);
        bounds[0].set(x, yInferior, size.x, size.y);

        // Animación de aleteo
        if (frames != null && frames.length > 0) {
            frameTime += delta;
            while (frameTime >= frameDuration) {
                frameTime -= frameDuration;
                frameIndex = (frameIndex + 1) % frames.length;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch, float worldHeight) {
        Texture current = (frames != null && frames.length > 0) ? frames[frameIndex] : null;
        float yInferior = bounds[0].y;
        if (current != null) {
            batch.draw(current, x, yInferior, size.x, size.y);
        }
    }

    @Override
    public boolean colisiona(Rectangle boundsJugador) {
        return boundsJugador.overlaps(bounds[0]);
    }

    @Override
    public boolean fueraDePantalla() {
        return (x + size.x) < 0;
    }

    @Override
    public void reposicionar(float nuevoX) {
        this.x = nuevoX;

        float margen = 90f;
        this.yBase = MathUtils.random(margen + size.y / 2f, worldHeight - margen - size.y / 2f);
        this.fase = MathUtils.random(0f, MathUtils.PI2);

        // Reubicar bound
        float yInferior = yBase - size.y / 2f;
        bounds[0].set(x, yInferior, size.x, size.y);

        // Opcional: desfasar la animación para variar
        // this.frameIndex = MathUtils.random(frames.length - 1);
        // this.frameTime = 0f;
    }

    // === Getters requeridos por la interfaz ===
    @Override public float getX() { return x; }
    @Override public float getAncho() { return size.x; }
    @Override public float getVelocidad() { return velocidad; }
    @Override public Rectangle[] getBounds() { return bounds; }
    public Vector2 getSize() { return size; }

    // (Opcional) setters para tunear animación en runtime:
    public void setFrameDuration(float frameDuration) { this.frameDuration = frameDuration; }
    public void setVelocidad(float velocidad) { this.velocidad = velocidad; }
}
