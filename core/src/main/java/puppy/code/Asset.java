package puppy.code;


import com.badlogic.gdx.graphics.Texture;

public class Asset {
	private Texture background;
	private Texture ground;
	private Texture tuboTex;
	private Texture[] birdFrames;
	
	public Asset(){
	setBackground(new Texture("flappy/background.png"));
    setGround(new Texture("flappy/ground.png"));
    setTuboTex(new Texture("flappy/pipe.png"));
    birdFrames = new Texture[] {
    	        new Texture("flappy/bird0.png"),
    	        new Texture("flappy/bird1.png"),
    	        new Texture("flappy/bird2.png")
    	    };
	}

	public Texture getBackground() {
		return background;
	}

	public void setBackground(Texture background) {
		this.background = background;
	}

	public Texture getGround() {
		return ground;
	}

	public void setGround(Texture ground) {
		this.ground = ground;
	}

	public Texture getTuboTex() {
		return tuboTex;
	}

	public void setTuboTex(Texture tuboTex) {
		this.tuboTex = tuboTex;
	}
	
	public Texture[] getBirdFrames(){
		return this.birdFrames;
	}
}
