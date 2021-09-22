package main;

import java.awt.image.BufferedImage;

public record SpriteSheet(BufferedImage image) {

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
    }


}
