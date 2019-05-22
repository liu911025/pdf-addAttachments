package com.design.patterns.designpatterns.pdf;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.annotations.*;
import com.spire.pdf.attachments.PdfAttachment;
import com.spire.pdf.graphics.*;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AttachFiles {

    static String src = "E:\\pdf\\qqq.pdf";     // 原文件
    static String dest = "E:\\pdf\\ccc.pdf";    // 最终文件
    static String att = "E:\\pdf\\666.mp4";     // 附件
    static String desc = "附件";                 // 附件注释

    public static void main(String[] args) throws IOException {

        //创建PdfDocument对象
        PdfDocument doc = new PdfDocument();

        //加载PDF文档
        doc.loadFromFile(src);

        //添加附件到PDF
        PdfAttachment attachment = new PdfAttachment(att);
        attachment.setDescription(desc);
        doc.getAttachments().add(attachment);

        int count = doc.getPages().getCount() - 1;

        //绘制标签
        String label = "视频.mp4";
        PdfTrueTypeFont font = new PdfTrueTypeFont(new Font("Arial Unicode MS",Font.PLAIN,12),true);
        double x = 35;
        double y = doc.getPages().get(count).getActualSize().getHeight() - 800;
        doc.getPages().get(count).getCanvas().drawString(label, font, PdfBrushes.getOrange(), x, y);

        //添加注释附件到PDF
        String filePath = att;
        byte[] data = toByteArray(filePath);
        Dimension2D size = font.measureString(label);
        Rectangle2D bound = new Rectangle2D.Float((float) (x + size.getWidth() + 2), (float) y, 10, 15);
        PdfAttachmentAnnotation annotation = new PdfAttachmentAnnotation(bound, filePath, data);
        annotation.setColor(new PdfRGBColor(new Color(0, 128, 128)));
        annotation.setFlags(PdfAnnotationFlags.Default);
        annotation.setIcon(PdfAttachmentIcon.Graph);
        annotation.setText("双击打开文件");
        doc.getPages().get(0).getAnnotationsWidget().add(annotation);

        //保存文档
        doc.saveToFile(dest);
    }

    //读取文件到byte数组
    public static byte[] toByteArray(String filePath) throws IOException {

        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }
}