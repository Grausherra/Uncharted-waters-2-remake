package game;

import org.newdawn.slick.SpriteSheet;

/**
 * @author Junjie CHEN(jacky.jjchen@gmail.com)
 */
public class World implements GameMap {

    public static final int WIDTH = 2158;
    public static final int HEIGHT = 1080;

    private final byte[][] map;
    private SpriteSheet sheet;

    public World(byte[][] map, SpriteSheet sheet) {
        setSpriteSheet(sheet);
        this.map = map;
    }

    public void render(double mapX, double mapY, int x, int y, int screenWidth, int screenHeight) {

        int numRow = screenHeight / sheet.getSubImage(0, 0).getHeight() + 1;
        int numCol = screenWidth / sheet.getSubImage(0, 0).getWidth() + 1;

        int upperLeftX = (int) Math.floor(mapX);
        int upperLeftY = (int) Math.floor(mapY);

        int offsetX = (int) ((mapX - upperLeftX) * 16);
        int offsetY = (int) ((mapY - upperLeftY) * 16);

        for (int row = 0; row < numRow; row++) {
            for (int col = 0; col < numCol; col++) {

                if (upperLeftY + row < HEIGHT) {

                    int id = map[row + upperLeftY][((int) wrapCol(col + upperLeftX))];
                    int sprite_row = id / 10;
                    int sprite_col = id % 10;

                    sheet.getSubImage(sprite_col, sprite_row).draw((x - offsetX + col * 16),
                            (y - offsetY + row * 16));
                }
            }
        }
    }

    public boolean isEnterable(int col, int row, Direction dir) {

        if (row < 0 || row >= HEIGHT)
            return false;

        return Tile.isSea(map[row][((int) wrapCol(col))]);
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    public void setTile(int col, int row, int id) {
        map[row][col] = (byte) id;
    }

    public void setSpriteSheet(SpriteSheet sheet) {
        this.sheet = sheet;
    }

    public byte[][] getMap() {
        return map;
    }

    public double wrapCol(double x) {
        if (x < 0) {
            x = -(-x % WIDTH);
        }
        return (x + WIDTH) % WIDTH;
    }
}
