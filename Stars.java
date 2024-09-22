import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

// Stars is one type of Filter
public class Stars extends Filter {

	// Overrides the applyFilter() method constructed in the interface.
	@Override
	public void applyFilter(int height, int width, RGBTRIPLE[][] image, String randB, String randG, String randR){
		
		// Creates new RGBTRIPLE array to store copied data from original .bmp
        RGBTRIPLE[][] temp = new RGBTRIPLE[height][width];
        for (int i = 0; i < height; i++) {
            System.arraycopy(image[i], 0, temp[i], 0, width);
        }

        // Sobel operator matrices used to calculate magnitudes of color differences between pixels
		int[][] Sx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
		int[][] Sy = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        // Nested forloops that iterative over each successive pixel in the .bmp, checking the surrounding 3x3 matrix of
		// pixels and multiplying by positive or negative scalars. Adds everything together at end.
		// Iteratives across rows
        for (int i = 0; i < height; i++) {
			// Iteratives across columns
            for (int j = 0; j < width; j++) {
				
				// NEEDS COMMENTS
                byte xLum = 0, yLum = 0;
                int lum = image[i][j].luminanceGrey;

				// Iteratives 3 across in the x-matrix
                for (int k = -1; k < 2; k++) {
					// Iteratives 3 up in the y-matrix
                    for (int l = -1; l < 2; l++) {
                        // Edge cases => skip iteration (eg. i=0, k=-1 clearly doesn't exist as a pixel x,y value)
                        if (i + k < 0 || i + k >= height || j + l < 0 || j + l >= width) {
                            continue;
                        }
						
						// NEEDS COMMENTS
                        xLum += temp[i + k][j + l].luminanceGrey * Sx[k + 1][l + 1];
                        yLum += temp[i + k][j + l].luminanceGrey * Sy[k + 1][l + 1];

                    }
                }

				// NEEDS COMMENTS
                lum = (byte) Math.min(255, Math.round(Math.sqrt(xLum*xLum + yLum*yLum)));

				// NEEDS COMMENTS
                if (lum < 125){
                    image[i][j].setWhite();
                } else image[i][j].setBlack();

            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("java Main <input> <output> (for testing purposes)");
            return;
        }

        String in = args[0];
        String out = args[1];
		String x = null;
		String y = null;
		String z = null;

		// File reader
        try (FileInputStream input = new FileInputStream(in);
             FileOutputStream output = new FileOutputStream(out)) {
            byte[] bfData = new byte[14];
            input.read(bfData);
            BITMAPFILEHEADER bf = new BITMAPFILEHEADER(bfData);

            byte[] biData = new byte[40];
            input.read(biData);
            BITMAPINFOHEADER bi = new BITMAPINFOHEADER(biData);

			// Use math functions to determine the height and width of the .bmp
            int height = Math.abs(bi.biHeight);
            int width = bi.biWidth;
			
			// Create the image[][] array that will eventually become the output .bmp
            RGBTRIPLE[][] image = new RGBTRIPLE[height][width];
			
			// Standard .bmp image padding
            int padding = (4 - (width * 3) % 4) % 4;

			// Reads in the data from the original .bmp to the image[][] array
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    byte blue = (byte) input.read();
                    byte green = (byte) input.read();
                    byte red = (byte) input.read();
                    image[i][j] = new RGBTRIPLE(blue, green, red);
                }

                input.skip(padding);
            }
			
			// Creates a new filter of the type of this class
			Filter filter = new Stars();
			
			// Actually applies the image filtering algorithm
			if (filter != null) {
				filter.applyFilter(height, width, image, x, y, z);
			}

			// Writes the changed data to the output .bmp
            output.write(bfData);
            output.write(biData);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    output.write(image[i][j].rgbtBlue);
                    output.write(image[i][j].rgbtGreen);
                    output.write(image[i][j].rgbtRed);
                }

				// Notice that this accounts for padding
                for (int k = 0; k < padding; k++) {
                    output.write(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
