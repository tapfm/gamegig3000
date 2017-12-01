package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.scene.Camera;
import org.lwjgl.input.Keyboard;

public class GameCamera extends Camera{

    private float theta;

    private float turnRate;
    private boolean turning;

    private float thetaPrime;

    private GameWorld world;

    public GameCamera(GameWorld world) {
        this.world = world;
        turnRate = 0.001f;

        this.setPosition(new Vector3f(0f,0f,1f));
        this.setAngle(theta,(float) Math.PI / 2);
    }

    public float getTheta() {
        return theta;
    }

    public float getThetaPrime() {
        return thetaPrime;
    }

    public void turn() {
        if (!turning) turning = true;
    }

    public boolean isTurning() {
        return turning;
    }

    @Override
    public void update(Game game, int delta) {
        if (turning) theta += turnRate * delta;
        if (turning && theta > thetaPrime + (Math.PI /2f)) {
            turning = false;
            thetaPrime += (float) Math.PI / 2f;
            theta = thetaPrime;
        }

        this.setPosition((float)Math.sin(theta),0f,(float)Math.cos(theta));
        this.setAngle(theta,(float) Math.PI / 2);

        if (game.getInput().isKeyPressed(Keyboard.KEY_E))
            turnRight();
        if (game.getInput().isKeyPressed(Keyboard.KEY_Q))
            turnLeft();
        if (game.getInput().isKeyDown(Keyboard.KEY_W))
            this.setPosition(this.getPosition().add(new Vector3f(0f,delta * 0.0001f,0f)));

        if (turning) {
            if (theta + turnRate * delta >= thetaPrime + (Math.PI /2) && turnRate > 0) {
                turning = false;
                turnRate = 0;
                theta = thetaPrime + (float) (Math.PI / 2);
                thetaPrime = theta;
            } else if (theta + turnRate * delta <= thetaPrime - (Math.PI /2) && turnRate < 0) {
                turning = false;
                turnRate = 0;
                theta = thetaPrime - (float) (Math.PI / 2);
                thetaPrime = theta;
            } else
                theta += turnRate * delta;
            this.setPosition((float)Math.sin(theta),this.getY(),(float)Math.cos(theta));
            this.setAngle(theta,(float) Math.PI / 2);
        }
            turn();
    }
}
