package com.design.patterns.designpatterns.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.annotations.PdfAnnotationFlags;
import com.spire.pdf.annotations.PdfAttachmentAnnotation;
import com.spire.pdf.annotations.PdfAttachmentIcon;
import com.spire.pdf.attachments.PdfAttachment;
import com.spire.pdf.graphics.PdfBrushes;
import com.spire.pdf.graphics.PdfTrueTypeFont;

import java.io.FileOutputStream;
import java.io.IOException;

public class CreatePdf {

    static String src = "E:\\pdf\\qqq.pdf";
    static String dest = "E:\\pdf\\ccc.pdf";
    //static String att = "E:\\pdf\\doc.docx";
    //static String att = "E:\\pdf\\qz.png";
    static String att = "E:\\pdf\\666.mp4";

    public static void main(String[] args) throws DocumentException, IOException {

        addAttachment(src, dest,att, "附件");
    }

    /**
     * PDF里添加附件
     * @param src pdf源文件
     * @param dest 目标文件
     * @param att 插入附件的文件路径（带后缀名）
     * @param desc 附件简单描述
     * @throws IOException
     * @throws DocumentException
     */
    public static void addAttachment(String src, String dest, String att,
                                     String desc) throws IOException, DocumentException {

       /* PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(
                stamper.getWriter(), null, att, desc.getBytes());
        stamper.addFileAttachment(desc, fs);
        stamper.close();
        reader.close();*/

        //加载PDF文档
        PdfDocument pdf = new PdfDocument(src);
        //加载需要附加的文档
        PdfAttachment attachment = new PdfAttachment(att);
        attachment.setDescription(desc);
        //将文档添加到原PDF文档的附件集合中
        pdf.getAttachments().add(attachment);
        //保存文档
        pdf.saveToFile(dest);
    }

}
