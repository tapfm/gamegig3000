package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.scene.Camera;
import org.lwjgl.input.Keyboard;

import java.security.Key;

public class GameCamera extends Camera{

    private float theta;

    private float turnRate;
    private boolean turning;

    private float thetaPrime;

    public GameCamera() {
        theta = 0f;
        turnRate = 0f;
        turning = false;

        thetaPrime = theta;

        this.setPosition(new Vector3f(0f,0f,1f));
        this.setAngle(theta,(float) Math.PI / 2);
    }

    public float getTheta() {
        return theta;
    }

    public void turnRight() {
        if (!turning) {
            thetaPrime = theta;
            turning = true;
            turnRate = 0.001f;
        }
    }

    public void turnLeft() {
        if (!turning) {
            thetaPrime = theta;
            turning = true;
            turnRate = -0.001f;
        }
    }

    @Override
    public void update(Game game, int delta) {

        if (game.getInput().isKeyPressed(Keyboard.KEY_E))
            turnRight();
        if (game.getInput().isKeyPressed(Keyboard.KEY_Q))
            turnLeft();

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
            this.setPosition((float)Math.sin(theta),0f,(float)Math.cos(theta));
            this.setAngle(theta,(float) Math.PI / 2);
        }
    }
}
