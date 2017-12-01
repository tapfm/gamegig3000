package net.industrial.src;

import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.SpriteSheet;

import java.awt.*;

public class BackgroundTiles {

    private int heightLevel;
    private SpriteSheet spritesheet;

    public BackgroundTiles(int height, SpriteSheet ss) {
        this.heightLevel = height;
        this.spritesheet = ss;

    }

    public int[][] generateLevel(int height, int width) {
        int[][] section = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                section[i][j] = 1;
            }
        }
        return section;
    }

    public void render(Graphics graphics) {
        int height = 8;
        int width = 8;

        int[][] forward  = generateLevel(height, width);
        int[][] backward = generateLevel(height, width);
        int[][] right = generateLevel(height, width);
        int[][] left = generateLevel(height, width);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                graphics.fillQuad(new Vector3f(0.2f,-0.175f + (float)row * 0.05f,-0.175f + (float)col * 0.05f),
                        new Vector3f(1f,0f,0f),
                        new Vector3f(0f,0f,1f),
                        0.05f,0.05f, spritesheet.getSprite(0,0));
                graphics.fillQuad(new Vector3f(-0.2f,-0.175f + (float)row * 0.05f,-0.175f + (float)col * 0.05f),
                        new Vector3f(-1f,0f,0f),
                        new Vector3f(0f,0f,1f),
                        0.05f,0.05f, spritesheet.getSprite(0,0));
                graphics.fillQuad(new Vector3f(-0.175f + (float)col * 0.05f,-0.175f + (float)row * 0.05f,0.2f),
                        new Vector3f(0f,0f,1f),
                        new Vector3f(1f,0f,1f),
                        0.05f,0.05f, spritesheet.getSprite(0,0));
                graphics.fillQuad(new Vector3f(-0.175f + (float)col * 0.05f,-0.175f + (float)row * 0.05f,-0.2f),
                        new Vector3f(0f,0f,-1f),
                        new Vector3f(0f,0f,1f),
                        0.05f,0.05f, spritesheet.getSprite(0,0));
            }
        }

    }

}
