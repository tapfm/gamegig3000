package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.scene.Camera;
import org.lwjgl.input.Keyboard;

public class GameCamera extends Camera{

    private float theta;
    private float y;

    private float turnRate;
    private float upRate;

    private boolean turning;
    private boolean goingUp;

    private float thetaPrime;
    private float yPrime;

    private GameWorld world;

    public GameCamera(GameWorld world) {
        this.world = world;
        turnRate = 0.001f;
        upRate = turnRate * 0.8f / (float)Math.PI;

        y = 0;
        yPrime = y;

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
        if (!turning) {
            turning = true;
            goingUp = true;
        }
    }

    public boolean isTurning() {
        return turning || goingUp;
    }

    @Override
    public void update(Game game, int delta) {
        if (turning) {
            theta += turnRate * delta;
        }
        if (goingUp) {
            y += upRate * delta;
        }
        if (turning && theta > thetaPrime + (Math.PI /2f)) {
            turning = false;
            thetaPrime += (float) Math.PI / 2f;
            theta = thetaPrime;
        }
        if (goingUp && y > yPrime + 0.4f) {
            goingUp = false;
            yPrime += 0.4f;
            y = yPrime;
        }

        this.setPosition((float)Math.sin(theta),y,(float)Math.cos(theta));
        this.setAngle(theta,(float) Math.PI / 2);
    }
}
