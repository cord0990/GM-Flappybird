
package puppy.code.Colisiones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class Tubo implements Colision{
	private float xInicio; //posicion x de inicio del tubo
    private float gapY; //Posicion del hueco donde se pasara //a
    private float velocidad = 120f;
    private static final float GAP = 120f; //altura del hueco
    private Texture textura;
    private final Rectangle[] bounds = new Rectangle[2];
    //private Rectangle topRect = new Rectangle();
    //private Rectangle botRect = new Rectangle();
    private Vector2 size = new Vector2();

    //Constructor
    public Tubo(Texture pipeTex, float startX, float worldHeight) {
        this.textura = pipeTex;
        this.xInicio = startX;
        this.size = new Vector2(pipeTex.getWidth(),pipeTex.getHeight()); //Tamaño del objeto
        bounds[0] = new Rectangle(); // superior
        bounds[1] = new Rectangle(); // inferior
        randomizeGap(worldHeight); //Tamaño //Generea el hueco de manera aleatoria desde 150y hasta 800-150y
        updateRects(worldHeight); //Posiciona los tubos y su hitbox a partir de el GAP
    }

    
    //Metodos
    private void randomizeGap(float worldHeight) {
        float minY = 150;
        float maxY = worldHeight - 150;
        gapY = MathUtils.random(minY, maxY);
    }

    private void updateRects(float worldHeight) {
    	this.bounds[0].set(xInicio, gapY + GAP/2f, size.x, size.y);
    	this.bounds[1].set(xInicio, 0, size.x, gapY - GAP/2f);
        //topRect.set(xInicio, gapY + GAP/2f, size.x, size.y);
        //botRect.set(xInicio, 0, size.x, gapY - GAP/2f);
    }

    @Override
    public void update(float dt) { 
    	xInicio -= velocidad * dt; updateRects(512);  //Modificar y ver que hacer con worldheight
    
	    }
    @Override
    public boolean fueraDePantalla() { return xInicio + size.x < 0; }

    @Override
    public void reposicionar(float newX) {
    	xInicio = newX; randomizeGap(512); updateRects(512); }  //Modificar y ver que hacer con worldheight

    public void draw(SpriteBatch batch, float worldHeight) {
        batch.draw(textura, xInicio, 0, size.x, (int)(gapY - GAP/2f));
        batch.draw(textura, xInicio, (int)(gapY + GAP/2f), size.x, (int)(worldHeight - (gapY + GAP/2f))); //Modificar y ver que hacer con worldheight
    }
    
    
    @Override
	public boolean colisiona(Rectangle boundsJugador) {
		if (boundsJugador.overlaps(this.getBounds()[0]) || boundsJugador.overlaps(this.getBounds()[1])) {
            return true;
            }
		return false;
	}

   
    //Getters 
    @Override
    public float getX() { return this.xInicio;}
    
    public float getVelocidad() {return this.velocidad;}
    
    public Rectangle[] getBounds() {return this.bounds;}

	public Vector2 getSize() {return this.size;}

	@Override
	public float getAncho() {return this.size.x;}


}



