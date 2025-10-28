package puppy.code.Colisiones;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Colision {
    // Actualiza posición/estado con delta time
    void update(float delta);

    // ¿Colisiona con el jugador/ave?
    boolean colisiona(Rectangle boundsJugador);

    // ¿Ya salió por la izquierda de la pantalla?
    boolean fueraDePantalla();

    // Reposiciona para reciclar (por ejemplo, más a la derecha)
    void reposicionar(float nuevoX);

    // Coordenadas/medidas útiles
    float getX();
    float getAncho();

	void draw(SpriteBatch batch, float worldheight);

	//Rectangle[] getBounds(); //Hitboxs

	float getVelocidad();

	Rectangle[] getBounds();

    // Para la clase de lista de obstrucciones
    //boolean otorgoPunto();
    //void marcarPuntoOtorgado();

    // (Opcional) dibujado; si usas LibGDX, pasa SpriteBatch
    //default void render(Object batch) { /* no-op por defecto */ }
}
