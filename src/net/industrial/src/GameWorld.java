package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.GameState;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.resources.Font;
import net.industrial.grassland.resources.SpriteSheet;

public class GameWorld extends GameState {
    private Font font;

    @Override
    public void init(Game game) throws GrasslandException {
        font = new Font(new SpriteSheet("res/font.png", 10, 11).scale(2f));
    }

    @Override
    public void update(Game game, int delta) throws GrasslandException {

    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.setBackgroundColour(0f, 0f, 0f);
        graphics.drawString(font, "GAME HERE", 20, 20);
    }

    @Override
    public int getId() {
        return 1;
    }
}
