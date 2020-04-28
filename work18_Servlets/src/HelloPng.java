import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class HelloPng extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws IOException {
        try (OutputStream out = response.getOutputStream()) {
            int frameWidth = 640;
            int frameHeight = 190;
            int x = 100;
            int y = 100;
            Font font = new Font("Arial", Font.BOLD, 72);
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            Color color = new Color(r, g, b);
            BufferedImage bufferedImage = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = bufferedImage.createGraphics();
            graphics2D.setBackground(Color.WHITE);
            graphics2D.fillRect(0, 0, frameWidth, frameHeight);
            graphics2D.setColor(color);
            graphics2D.setFont(font);
            graphics2D.drawString("Hello, World!", x, y);
            graphics2D.dispose();
            ImageIO.write(bufferedImage, "png", out);
            response.setContentType("image/png");
        }
    }
}
