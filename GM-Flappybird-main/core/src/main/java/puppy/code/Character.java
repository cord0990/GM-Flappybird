
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import puppy.code.Screens.GameScreen;

public class Character {
    private Texture[] frames;
    private float animTimer = 0f;
    private int frameIndex = 0;
    public Vector2 pos = new Vector2(0, 0); //Posicion  default;
    public Vector2 vel = new Vector2(0, 0); //Velocidad default;
    public Vector2 size = new Vector2(32,32); //tamaño default
    private Rectangle bounds = new Rectangle();
    private boolean alive = true;

    
    public Character(float x, float y, Texture[] sprite) {
        this.pos.set(x, y); //posicion
        this.size.set(32,32); //tamaño pajaro 
        this.frames = sprite;
        this.bounds.set(x, y, 32, 32); //hitbox y su posicion
        }

    public void flap() { vel.y = 260; } //Salto 

    public void presionAtmosferica(float dt, float gravity) {
        vel.y += gravity * dt; //Modifica la velocidad de caida
        //vel.x += 2 * dt; //IDEA DE PARAMETRO PARA POWERUP!!!!!
        pos.add(vel.x * dt, vel.y * dt); //modifica la posicion
        animTimer += dt; 
        if (animTimer > 0.12f) { animTimer = 0f; frameIndex = (frameIndex + 1) % frames.length; } //Animacion de las alas.
        bounds.setPosition(pos.x, pos.y); //Posiciona la hitbox
    }
    
    public void movimiento(float dt, float gravity) {
    	if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            this.flap();
        }
    	
    	this.presionAtmosferica(dt, gravity);
    	
    }
    
    public void fueraDePantalla(GameScreen screen, FlappyGameMenu game) {
    	if (screen.getBird().pos.y <= 96 || screen.getBird().pos.y + 24 >= GameScreen.getWorldheight()) {
            screen.setGameOver(true);
            if (screen.getScore() > game.getHigherScore()) game.setHigherScore(screen.getScore());
        }
    	
    }

    public Rectangle getBounds() { return bounds; } //hitbox
    
    public boolean isAlive() { return alive; }

    public void draw(SpriteBatch batch) { batch.draw(frames[frameIndex], pos.x, pos.y); }

    public void reset(float y) { pos.set(80, y); vel.set(0,0); }
    
    //public float getGravity() { return -900f;}
}
