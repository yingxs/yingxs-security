package com.yingxs.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.yingxs.security.aop.SysLog;
import com.yingxs.security.support.SimpleResponse;
import com.yingxs.security.validate.ImageCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE_";

    @SysLog("获取图形验证码")
    @GetMapping("/code/image")
    public SimpleResponse createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode ( new ServletWebRequest(request) );
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY+imageCode.getCodeId(), imageCode);
        return new SimpleResponse("SUCCESS",imageCode);
//      ImageIO.write(imageCode.getImage(), "PNG", response.getOutputStream());
    }

    public ImageCode createImageCode(ServletWebRequest request) throws IOException {
        int width = 67;
        int height = 23;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();

        Random random = new Random();
        g.setColor(getRandColor(200,250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ITALIC,20));
        g.setColor(getRandColor(160, 200));

        for (int i = 0 ;i < 155 ; i++ ) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x+xl, y+yl);
        }

        String sRand = "";
        for (int i = 0; i < 4 ; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        ImageCode resultImageCode = new ImageCode(image, sRand, 600);
        ByteArrayOutputStream imageByte = new ByteArrayOutputStream();
        ImageIO.write(resultImageCode.getImage(), "JPG", imageByte);
        resultImageCode.setBase64String("data:image/jpg;base64,"+Base64.getEncoder().encodeToString(imageByte.toByteArray()));
        resultImageCode.setCodeId(UUID.randomUUID().toString());
        return resultImageCode;
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
