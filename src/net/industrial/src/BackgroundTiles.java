package net.industrial.src;

import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.SpriteSheet;

import java.awt.*;
import java.util.Random;

public class BackgroundTiles {

    private int heightLevel;
    private SpriteSheet spritesheet;

    private int[][] forward;
    private int[][] backward;
    private int[][] right;
    private int[][] left;

    private static int height = 8;
    private static int width = 8;


    public BackgroundTiles(int heightLevel, SpriteSheet ss) {
        this.heightLevel = heightLevel;
        this.spritesheet = ss;

        if (this.heightLevel == 0) {
            forward = generateBase();
            backward = generateBase();
            right = generateBase();
            left = generateBase();
        }
        else {
            forward = generateLevel();
            backward = generateLevel();
            right = generateLevel();
            left = generateLevel();
        }
    }

    public int[][] generateLevel() {
        Random r = new Random();
        int[][] section = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                section[i][j] = r.nextInt(2);
            }
        }
        return section;
    }

    public boolean cellAboveAlive(int row, int col, int[][] section) {
        if (row == 7)
            return  true;
        else if (section[row + 1][col] != 3)
            return true;
        else
            return false;
    }

    public int[][] generateBase() {
        Random r = new Random();
        int[][] baseSection = new int[height][width];

        for(int i = height - 1; i > 0; i--) {
            for(int j = 0; j < width; j++) {
                if (cellAboveAlive(i,j,baseSection) && r.nextFloat() > baseProb(j))
                    baseSection[i][j] = r.nextInt(2);
                else
                    baseSection[i][j] = 3;
            }
        }

        return baseSection;
    }

    public static float baseProb(int col) {
        int newCol;
        if (col < 4)
            newCol = col;
        else
            newCol = 7 - col;
        return (float) Math.pow(0.5,(double)newCol);
    }

    public void render(Graphics graphics) {

        if (heightLevel == 0){
            renderBase(graphics);
            return;
        }

        graphics.fillCuboid(new Vector3f(0f,0f,0f), new Vector3f(1f,0f,0f), new Vector3f(0f,1f,0f),8f * 0.05f, 6f * 0.05f, 6f * 0.05f, spritesheet.getSprite(3,0));

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                graphics.fillQuad(new Vector3f(0.2f,-0.175f + (float)row * 0.05f + (heightLevel - 1) * 8f * 0.05f,-0.175f + (float)col * 0.05f),
                        new Vector3f(1f,0f,0f),
                        new Vector3f(0f,0f,-1f),
                        0.05f,0.05f, spritesheet.getSprite(forward[row][col],0));
                graphics.fillQuad(new Vector3f(-0.2f,-0.175f + (float)row * 0.05f + (heightLevel - 1) * 8f * 0.05f,-0.175f + (float)col * 0.05f),
                        new Vector3f(-1f,0f,0f),
                        new Vector3f(0f,0f,1f),
                        0.05f,0.05f, spritesheet.getSprite(backward[row][col],0));
                graphics.fillQuad(new Vector3f(-0.175f + (float)col * 0.05f,-0.175f + (float)row * 0.05f + (heightLevel - 1) * 8f * 0.05f,0.2f),
                        new Vector3f(0f,0f,1f),
                        new Vector3f(1f,0f,0f),
                        0.05f,0.05f, spritesheet.getSprite(right[row][col],0));
                graphics.fillQuad(new Vector3f(-0.175f + (float)col * 0.05f,-0.175f + (float)row * 0.05f + (heightLevel - 1) * 8f * 0.05f,-0.2f),
                        new Vector3f(0f,0f,-1f),
                        new Vector3f(-1f,0f,0f),
                        0.05f,0.05f, spritesheet.getSprite(left[row][col],0));
            }
        }

    }

    public void renderBase(Graphics graphics) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (forward[row][col] != 3)
                    graphics.fillCuboid(new Vector3f(0.175f, -0.175f + (float) row * 0.05f + (heightLevel - 1) * 8f * 0.05f, -0.175f + (float) col * 0.05f),
                        new Vector3f(1f, 0f, 0f),
                        new Vector3f(0f, 0f, -1f),
                        0.05f, 0.05f, 0.05f,spritesheet.getSprite(forward[row][col], 0));
                if (backward[row][col] != 3)
                    graphics.fillCuboid(new Vector3f(-0.175f,-0.175f + (float)row * 0.05f + (heightLevel - 1) * 8f * 0.05f,-0.175f + (float)col * 0.05f),
                        new Vector3f(-1f,0f,0f),
                        new Vector3f(0f,0f,1f),
                        0.05f,0.05f, 0.05f,spritesheet.getSprite(backward[row][col],0));
                if (right[row][col] != 3)
                    graphics.fillCuboid(new Vector3f(-0.175f + (float)col * 0.05f,-0.175f + (float)row * 0.05f + (heightLevel - 1) * 8f * 0.05f,0.175f),
                        new Vector3f(0f,0f,1f),
                        new Vector3f(1f,0f,0f),
                        0.05f,0.05f, 0.05f,spritesheet.getSprite(right[row][col],0));
                if (left[row][col] != 3)
                    graphics.fillCuboid(new Vector3f(-0.175f + (float)col * 0.05f,-0.175f + (float)row * 0.05f + (heightLevel - 1) * 8f * 0.05f,-0.175f),
                        new Vector3f(0f,0f,-1f),
                        new Vector3f(-1f,0f,0f),
                        0.05f,0.05f, 0.05f,spritesheet.getSprite(left[row][col],0));
            }
        }
    }

}
