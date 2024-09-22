import java.io.*;
import java.util.Random;

// FlipFilter is one type of Filter
public class FlipFilter extends Filter {
	
	// Overrides the applyFilter() method constructed in the interface.
	@Override
	public void applyFilter(int height, int width, RGBTRIPLE[][] image, String randB, String randG, String randR) {
		
			// Creates new RGBTRIPLE array to store copied data from original .bmp
			RGBTRIPLE[][] temp = new RGBTRIPLE[height][width];
			for (int i = 0; i < height; i++) {
				System.arraycopy(image[i], 0, temp[i], 0, width);
			}
			
			// Randomizes the orientation of the flip over either the x or y axis for fun.
			Random random = new Random();
			int orientation = random.nextInt(2);
			System.out.println(orientation);
			
			// Flips over the y-axis by copying pixels on the left to the right and vice versa
			if (orientation == 0){
				for (int i = 0; i < height; i++){
					for (int j = 0, n = width / 2; j < n; j++){
						image[i][j] = image[i][width - j - 1];
						image[i][width - j - 1] = temp[i][j];
					}
				}
			}
			// Flips over the x-axis by copying pixels on the bottom to the top and vice versa
			else {
				for (int i = 0, n = height / 2; i < n; i++) {
					for (int j = 0; j < width; j++) {
						image[i][j] = image[height - i - 1][j];
						image[height - i - 1][j] = temp[i][j];
					}
				}
			}
		}
	
	public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("java <filterType> <input> <output> (for testing purposes)");
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
			Filter filter = new FlipFilter();
			
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