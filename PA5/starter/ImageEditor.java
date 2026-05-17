/*
 * Name: Aryan Pershad
 * Email: apershad@ucsd.edu
 * PID: A18938806
 * Sources used: Notes, Google
 *
 * This file contains an ImageEditor class that implements a simple
 * image editor allowing users to rotate, downsample, and patch images.
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is the main class for the image editor.
 */
public class ImageEditor {
    /* Constants (Magic numbers) */
    private static final String PNG_FORMAT = "png";
    private static final String NON_RGB_WARNING =
            "Warning: we do not support the image you provided. \n" +
            "Please change another image and try again.";
    private static final String RGB_TEMPLATE = "(%3d, %3d, %3d) ";
    private static final int BLUE_BYTE_SHIFT = 0;
    private static final int GREEN_BYTE_SHIFT = 8;
    private static final int RED_BYTE_SHIFT = 16;
    private static final int ALPHA_BYTE_SHIFT = 24;
    private static final int BLUE_BYTE_MASK = 0xff << BLUE_BYTE_SHIFT;
    private static final int GREEN_BYTE_MASK = 0xff << GREEN_BYTE_SHIFT;
    private static final int RED_BYTE_MASK = 0xff << RED_BYTE_SHIFT;
    private static final int ALPHA_BYTE_MASK = ~(0xff << ALPHA_BYTE_SHIFT);

    private static final int QUARTER_TURN = 90;
    private static final int FULL_CIRCLE = 360;
    private static final int MIN_SCALE = 1;

    /* Static variables - DO NOT add any additional static variables */
    static int[][] image;

    /**
     * Open an image from disk and return a 2D array of its pixels.
     * Use 'load' if you need to load the image into 'image' 2D array instead
     * of returning the array.
     *
     * @param pathname path and name to the file, e.g. "input.png",
     *                 "D:\\Folder\\ucsd.png" (for Windows), or
     *                 "/User/username/Desktop/my_photo.png" (for Linux/macOS).
     *                 Do NOT use "~/Desktop/xxx.png" (not supported in Java).
     * @return 2D array storing the rgb value of each pixel in the image
     * @throws IOException when file cannot be found or read
     */
    public static int[][] open(String pathname) throws IOException {
        BufferedImage data = ImageIO.read(new File(pathname));
        if (data.getType() != BufferedImage.TYPE_3BYTE_BGR &&
                data.getType() != BufferedImage.TYPE_4BYTE_ABGR) {
            System.err.println(NON_RGB_WARNING);
        }
        int[][] array = new int[data.getHeight()][data.getWidth()];

        for (int row = 0; row < data.getHeight(); row++) {
            for (int column = 0; column < data.getWidth(); column++) {
                /* Images are stored by column major
                   i.e. (2, 10) is the pixel on the column 2 and row 10
                   However, in class, arrays are in row major
                   i.e. [2][10] is the 11th element on the 2nd row
                   So we reverse the order of i and j when we load the image.
                 */
                array[row][column] = data.getRGB(column, row) & ALPHA_BYTE_MASK;
            }
        }

        return array;
    }

    /**
     * Load an image from disk to the 'image' 2D array.
     *
     * @param pathname path and name to the file, see open for examples.
     * @throws IOException when file cannot be found or read
     */
    public static void load(String pathname) throws IOException {
        image = open(pathname);
    }

    /**
     * Save the 2D image array to a PNG file on the disk.
     *
     * @param pathname path and name for the file. Should be different from
     *                 the input file. See load for examples.
     * @throws IOException when file cannot be found or written
     */
    public static void save(String pathname) throws IOException {
        BufferedImage data = new BufferedImage(
                image[0].length, image.length, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < data.getHeight(); row++) {
            for (int column = 0; column < data.getWidth(); column++) {
                // reverse it back when we write the image
                data.setRGB(column, row, image[row][column]);
            }
        }
        ImageIO.write(data, PNG_FORMAT, new File(pathname));
    }

    /**
     * Unpack red byte from a packed RGB int
     *
     * @param rgb RGB packed int
     * @return red value in that packed pixel, 0 <= red <= 255
     */
    private static int unpackRedByte(int rgb) {
        return (rgb & RED_BYTE_MASK) >> RED_BYTE_SHIFT;
    }

    /**
     * Unpack green byte from a packed RGB int
     *
     * @param rgb RGB packed int
     * @return green value in that packed pixel, 0 <= green <= 255
     */
    private static int unpackGreenByte(int rgb) {
        return (rgb & GREEN_BYTE_MASK) >> GREEN_BYTE_SHIFT;
    }

    /**
     * Unpack blue byte from a packed RGB int
     *
     * @param rgb RGB packed int
     * @return blue value in that packed pixel, 0 <= blue <= 255
     */
    private static int unpackBlueByte(int rgb) {
        return (rgb & BLUE_BYTE_MASK) >> BLUE_BYTE_SHIFT;
    }

    /**
     * Pack RGB bytes back to an int in the format of
     * [byte0: unused][byte1: red][byte2: green][byte3: blue]
     *
     * @param red   red byte, must satisfy 0 <= red <= 255
     * @param green green byte, must satisfy 0 <= green <= 255
     * @param blue  blue byte, must satisfy 0 <= blue <= 255
     * @return packed int to represent a pixel
     */
    private static int packInt(int red, int green, int blue) {
        return (red << RED_BYTE_SHIFT)
                + (green << GREEN_BYTE_SHIFT)
                + (blue << BLUE_BYTE_SHIFT);
    }

    /**
     * Rotate the image clockwise by the given number of degrees. The
     * image is left unchanged if degree is negative or not a multiple
     * of 90.
     *
     * @param degree the clockwise rotation in degrees
     */
    public static void rotate(int degree) {
        if (degree < 0 || degree % QUARTER_TURN != 0) {
            return;
        }
        int times = (degree % FULL_CIRCLE) / QUARTER_TURN;
        for (int t = 0; t < times; t++) {
            int oldRows = image.length;
            int oldCols = image[0].length;
            int[][] rotated = new int[oldCols][oldRows];
            for (int i = 0; i < oldRows; i++) {
                for (int j = 0; j < oldCols; j++) {
                    rotated[j][oldRows - 1 - i] = image[i][j];
                }
            }
            image = rotated;
        }
    }

    /**
     * Down-sample the image by averaging each heightScale x widthScale
     * block of pixels into a single pixel. The image is left unchanged
     * if either scale is less than 1, larger than the corresponding
     * dimension, or does not evenly divide that dimension.
     *
     * @param heightScale vertical down-sampling factor
     * @param widthScale  horizontal down-sampling factor
     */
    public static void downSample(int heightScale, int widthScale) {
        int height = image.length;
        int width = image[0].length;
        if (heightScale < MIN_SCALE || heightScale > height) {
            return;
        }
        if (widthScale < MIN_SCALE || widthScale > width) {
            return;
        }
        if (height % heightScale != 0 || width % widthScale != 0) {
            return;
        }
        int newHeight = height / heightScale;
        int newWidth = width / widthScale;
        int blockSize = heightScale * widthScale;
        int[][] sampled = new int[newHeight][newWidth];
        for (int row = 0; row < newHeight; row++) {
            int rowBase = row * heightScale;
            for (int col = 0; col < newWidth; col++) {
                int colBase = col * widthScale;
                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;
                for (int i = 0; i < heightScale; i++) {
                    for (int j = 0; j < widthScale; j++) {
                        int pixel = image[rowBase + i][colBase + j];
                        redSum += unpackRedByte(pixel);
                        greenSum += unpackGreenByte(pixel);
                        blueSum += unpackBlueByte(pixel);
                    }
                }
                int avgRed = redSum / blockSize;
                int avgGreen = greenSum / blockSize;
                int avgBlue = blueSum / blockSize;
                sampled[row][col] = packInt(avgRed, avgGreen, avgBlue);
            }
        }
        image = sampled;
    }

    /**
     * Patch patchImage onto the current image starting at the given
     * position. Pixels whose RGB matches the transparent color are
     * skipped. If the patch would not fit within the current image,
     * the image is left unchanged and 0 is returned.
     *
     * @param startRow         top row of the patch in the current image
     * @param startColumn      left column of the patch in the current image
     * @param patchImage       the 2D array of pixels to patch in
     * @param transparentRed   red component of the transparent color
     * @param transparentGreen green component of the transparent color
     * @param transparentBlue  blue component of the transparent color
     * @return the number of pixels that were patched
     */
    public static int patch(int startRow, int startColumn,
            int[][] patchImage, int transparentRed, int transparentGreen,
            int transparentBlue) {
        int height = image.length;
        int width = image[0].length;
        int patchHeight = patchImage.length;
        int patchWidth = patchImage[0].length;
        if (startRow < 0 || startRow >= height) {
            return 0;
        }
        if (startColumn < 0 || startColumn >= width) {
            return 0;
        }
        if (startRow + patchHeight > height) {
            return 0;
        }
        if (startColumn + patchWidth > width) {
            return 0;
        }
        int transparentPacked = packInt(
                transparentRed, transparentGreen, transparentBlue);
        int count = 0;
        for (int i = 0; i < patchHeight; i++) {
            for (int j = 0; j < patchWidth; j++) {
                if (patchImage[i][j] != transparentPacked) {
                    image[startRow + i][startColumn + j] = patchImage[i][j];
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Print the current image 2D array in (red, green, blue) format.
     * Each line represents a row in the image.
     */
    public static void printImage() {
        for (int[] ints : image) {
            for (int pixel : ints) {
                System.out.printf(
                        RGB_TEMPLATE,
                        unpackRedByte(pixel),
                        unpackGreenByte(pixel),
                        unpackBlueByte(pixel));
            }
            System.out.println();
        }
    }
}