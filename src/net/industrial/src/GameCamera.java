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

        float yVel = 0f;
        if (game.getInput().isKeyDown(Keyboard.KEY_W))
            yVel = 0.0005f * delta;

        this.setPosition((float)Math.sin(theta),getPosition().y + yVel,(float)Math.cos(theta));
        this.setAngle(theta,(float) Math.PI / 2);

        if (game.getInput().isKeyPressed(Keyboard.KEY_E))
            turn();
    }
}
