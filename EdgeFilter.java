import java.io.*;
import java.util.Random;

// EdgeFilter is one type of Filter
public class EdgeFilter extends Filter {
	
	// Overrides the applyFilter() method constructed in the interface.
	@Override
	public void applyFilter(int height, int width, RGBTRIPLE[][] image, String randB, String randG, String randR) {
		
		// Converts command-line arguments for random RGB values from Strings to ints
		int x = Integer.parseInt(randB);
		int y = Integer.parseInt(randG);
		int z = Integer.parseInt(randR);
		
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
				float xB = 0, xG = 0, xR = 0, yB = 0, yG = 0, yR = 0;

				// Iteratives 3 across in the x-matrix
				for (int k = -1; k < 2; k++) {
					// Iteratives 3 up in the y-matrix
					for (int l = -1; l < 2; l++) {
						// Edge cases => skip iteration (eg. i=0, k=-1 clearly doesn't exist as a pixel x,y value)
						if (i + k < 0 || i + k >= height || j + l < 0 || j + l >= width) {
							continue;
						}

						// Multiplies each RGB component by the scalar corresponding to its relative position in
						// the 3x3 matrix. E.g., the pixel in the top left corner is multiplied by -1x and 1y while
						// the pixel in the bottom right corner is multiplied by 1x and -1y.
						xB += temp[i + k][j + l].rgbtBlue * Sx[k + 1][l + 1];
						xG += temp[i + k][j + l].rgbtGreen * Sx[k + 1][l + 1];
						xR += temp[i + k][j + l].rgbtRed * Sx[k + 1][l + 1];
						yB += temp[i + k][j + l].rgbtBlue * Sy[k + 1][l + 1];
						yG += temp[i + k][j + l].rgbtGreen * Sy[k + 1][l + 1];
						yR += temp[i + k][j + l].rgbtRed * Sy[k + 1][l + 1];
					}
				}
				
				// Randomizes the strength of the edge detection since different strengths may yield better results in
				// difference images.
				// Determine the maximum possible color difference based on the Sobel operator coefficients
				int maxColorDifference = 255 * 2 * 3; // Sobel coefficients range from -2 to 2

				// Adjust the threshold dynamically based on the maximum possible color difference
				int threshold = (int) (maxColorDifference * 0.2); // Example: using half of the maximum difference
				
				// If the threshold for color difference is great enough (as measured by RGB values between 0 and 255),
				// change the color of the pixel in question by the random value as dictated by the randomizer in the UI.
				if (Math.sqrt(xB * xB + yB * yB) > threshold && Math.sqrt(xG * xG + yG * yG) > threshold &&
				Math.sqrt(xR * xR + yR * yR) > threshold){
					image[i][j].rgbtBlue = (byte) x;	// Currently x, y, z are ints. Need to convert to (byte)s.
					image[i][j].rgbtGreen = (byte) y;
					image[i][j].rgbtRed = (byte) z;
				}
			}
		}
		return;
	}
	
	public static void main(String[] args) {
		
		// Need 5 CLI arguments: input.bmp, output.bmp, rgbBlue, rgbGreen, rgbRed.
        if (args.length < 5) {
            System.out.println("java <filterType> <input> <output> <> <> <> (for testing purposes)");
            return;
        }

        String in = args[0];
        String out = args[1];
		String randB = args[2];
		String randG = args[3];
		String randR = args[4];

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
			Filter filter = new EdgeFilter();
			
			// Actually applies the image filtering algorithm
			if (filter != null) {
				filter.applyFilter(height, width, image, randB, randG, randR);
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