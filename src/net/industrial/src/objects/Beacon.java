package net.industrial.src.objects;

import net.industrial.grassland.CollidableGameObject;
import net.industrial.grassland.Game;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector2f;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.src.GameCamera;
import net.industrial.src.GameWorld;
import net.industrial.src.Main;

import java.util.Random;

public class Beacon extends CollidableGameObject {
    private GameWorld world;
    private boolean spent = false;
    private boolean bats = false;

    public Beacon(GameWorld world, Vector3f position) {
        this.world = world;
        setPosition(position);
        setSize(Main.BLOCK_SIZE / 8f, Main.BLOCK_SIZE / 8f, Main.BLOCK_SIZE / 8f);
    }

    @Override
    public void update(Game game, int delta) throws GrasslandException {
        if (!spent) world.addObject(new BeaconSmoke(world, getPosition()));
        if (!spent && collidingWith((Player) world.getPlayer())) {
            world.resetBats();
            world.getPlayer().setPosition(getPosition());
            ((Player) world.getPlayer()).setVelocity(new Vector2f());
            ((GameCamera) world.getCamera()).turn();
            world.incrementScore();
            spent = true;
        }

        if (spent && !bats && !world.locked()) {
           bats = true;
           for (int i = 0; i < 2; i++) {
               Vector3f random = (new Vector3f(0, Main.BLOCK_SIZE, 0)).scale(4 + (new Random()).nextInt(6));
               random = random.add(world.getCamera().axisVector().scale(Main.BLOCK_SIZE).scale(6 + (new Random()).nextInt(6)));
               world.addBat(new Bat(world, world.getPlayer().getPosition().add(random)));
           }
        }
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {

    }
}
