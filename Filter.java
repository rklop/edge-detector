import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Filter {
	
	// http://msdn.microsoft.com/en-us/library/dd183374(VS.85).aspx
	// Lifted from the official Microsoft documentation for how .bmp metadata is attached to the header
    public static class BITMAPFILEHEADER {
        short bfType;
        int bfSize;
        short bfReserved1;
        short bfReserved2;
        int bfOffBits;

        public BITMAPFILEHEADER(byte[] data) {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            bfType = buffer.getShort();
            bfSize = buffer.getInt();
            bfReserved1 = buffer.getShort();
            bfReserved2 = buffer.getShort();
            bfOffBits = buffer.getInt();
        }
    }

	// http://msdn.microsoft.com/en-us/library/dd183376(VS.85).aspx
    public static class BITMAPINFOHEADER {
        int biSize;
        public int biWidth;
        public int biHeight;
        short biPlanes;
        short biBitCount;
        int biCompression;
        int biSizeImage;
        int biXPelsPerMeter;
        int biYPelsPerMeter;
        int biClrUsed;
        int biClrImportant;

        public BITMAPINFOHEADER(byte[] data) {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            biSize = buffer.getInt();
            biWidth = buffer.getInt();
            biHeight = buffer.getInt();
            biPlanes = buffer.getShort();
            biBitCount = buffer.getShort();
            biCompression = buffer.getInt();
            biSizeImage = buffer.getInt();
            biXPelsPerMeter = buffer.getInt();
            biYPelsPerMeter = buffer.getInt();
            biClrUsed = buffer.getInt();
            biClrImportant = buffer.getInt();
        }
    }
	
	// Creates a class to contain RGB values similar to how the Pair class was implemented from the homework.
	public static class RGBTRIPLE {
		public byte rgbtBlue;
		public byte rgbtGreen;
		public byte rgbtRed;

		// NEEDS COMMENTS
		public byte luminanceGrey;

		public RGBTRIPLE(byte blue, byte green, byte red) {
			rgbtBlue = blue;
			rgbtGreen = green;
			rgbtRed = red;
			
			// NEEDS COMMENTS
			luminanceGrey = (byte) (0.29 * rgbtRed + .589 * rgbtGreen + .114 * rgbtRed);
		}
		
		// NEEDS COMMENTS
		public int getLumninanceGrey(){
            return luminanceGrey;
        }
		
		// NEEDS COMMENTS
		public void setWhite(){
            rgbtRed = 0;
            rgbtGreen = 0;
            rgbtBlue = 0;
        }
		
		// NEEDS COMMENTS
		public void setBlack(){
            rgbtRed = (byte) 255;
            rgbtGreen = (byte) 255;
            rgbtBlue = (byte) 255;
        }
	}
	
	public void applyFilter(int height, int width, RGBTRIPLE[][] image, String randB, String randG, String randR){};

}