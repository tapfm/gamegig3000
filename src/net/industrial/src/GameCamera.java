package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.scene.Camera;
import org.lwjgl.input.Keyboard;

import java.security.Key;

public class GameCamera extends Camera{

    private float theta;

    public GameCamera() {
        theta = 0f;
        this.setPosition(new Vector3f(0f,0f,1f));
        this.setAngle(theta,(float) Math.PI / 2);
    }

    @Override
    public void update(Game game, int delta) {
        this.setPosition((float)Math.sin(theta),0f,(float)Math.cos(theta));
        this.setAngle(theta,(float) Math.PI / 2);
    }
}
