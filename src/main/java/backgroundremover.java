import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.FontWeight;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.element.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class backgroundremover {
    public static final String SRC  = "/home/spaceman/IdeaProjects/backgroundremover/PDFs/Burst/";
    public static final String DEST = "/home/spaceman/IdeaProjects/backgroundremover/PDFs/Modified/";


    public static void main(String[] args) throws Exception{
        //burstmain();
        debugmain();
    }

    public static void debugmain() throws Exception{
        String src = "/home/spaceman/IdeaProjects/backgroundremover/pg_0018.pdf";
        String dest = "/home/spaceman/IdeaProjects/backgroundremover/pg_0018_mod.pdf";

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfPage pdfPage = pdfDoc.getFirstPage();
        PdfDictionary pdfDict = pdfPage.getPdfObject();
        PdfDictionary resDict = pdfDict.getAsDictionary(new PdfName("Resources"));
        PdfDictionary objDict = resDict.getAsDictionary(new PdfName("XObject"));

        System.out.println(pdfDict);
        System.out.println(resDict);
        System.out.println(objDict);

        //remove background
        //List<PdfName> excludekeys = new ArrayList<PdfName>();
        //excludekeys.add(new PdfName("Fm0"));
        //PdfDictionary newObjDict = objDict.clone(excludekeys);
        //resDict.put(new PdfName("XObject"), newObjDict);

        //Debug Statements
        PdfStream newStream = pdfPage.newContentStreamBefore();
        PdfStream contentStream = pdfPage.getContentStream(1);
        //System.out.println(pdfPage.getContentStreamCount());
        //System.out.println(newStream.getLength());
        //System.out.println(contentStream.values());



        // recolor
        PdfCanvas canvas = new PdfCanvas(newStream, pdfPage.getResources(), pdfDoc);
        canvas.setFillColor(Color.BLACK);
        canvas.setTextRenderingMode(PdfCanvasConstants.TextRenderingMode.FILL_STROKE);
        canvas.beginText();
        pdfDoc.close();

    }

    public static void burstmain() throws Exception{

        String[] cHeadings = {
                "pg_0001", "pg_0005", "pg_0012", "pg_0078", "pg_0118", "pg_0196", "pg_0278", "pg_0304",
                "pg_0310", "pg_0358", "pg_0446", "pg_0486", "pg_0510", "pg_0540"
        };

        for(int i = 1; i<561; i++){
            String filename = String.format("pg_%04d", i);
            String src = SRC + filename + ".pdf";
            String dest = DEST + filename + ".pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
            if(!Arrays.asList(cHeadings).contains(filename)){
                // remove background
                PdfPage pdfPage = pdfDoc.getFirstPage();
                PdfDictionary pdfDict = pdfPage.getPdfObject();
                PdfDictionary resDict = pdfDict.getAsDictionary(new PdfName("Resources"));
                PdfDictionary objDict = resDict.getAsDictionary(new PdfName("XObject"));
                List<PdfName> excludekeys = new ArrayList<PdfName>();
                excludekeys.add(new PdfName("Fm0"));
                PdfDictionary newObjDict = objDict.clone(excludekeys);
                resDict.put(new PdfName("XObject"), newObjDict);

                // recolor
                PdfCanvas canvas = new PdfCanvas(pdfPage.newContentStreamBefore(), pdfPage.getResources(), pdfDoc);
                canvas.setTextRenderingMode(PdfCanvasConstants.TextRenderingMode.FILL_STROKE);
                canvas.beginText();
            } else {
                //copy file as it is
            }
            pdfDoc.close();
        }
    }


    /*
    public static void fillblack(String[] args) throws Exception{
        String src = "/home/spaceman/IdeaProjects/backgroundremover/pg_0007.pdf";
        String dest = "/home/spaceman/IdeaProjects/backgroundremover/pg_0007_textFill.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfPage pdfPage = pdfDoc.getFirstPage();
        PdfCanvas canvas = new PdfCanvas(pdfPage.newContentStreamBefore(), pdfPage.getResources(), pdfDoc);


        FontProgramFactory.registerFont("/home/spaceman/IdeaProjects/backgroundremover/myriadproregular.ttf", "myriad");
        PdfFont myriad = PdfFontFactory.createRegisteredFont("myriad");
        canvas.setFontAndSize(myriad, 12);
        canvas.setTextRenderingMode(PdfCanvasConstants.TextRenderingMode.STROKE);
        canvas.beginText();

        pdfDoc.close();
    }

    public static void fileloop(String[] args) throws Exception{
        //1. Loop Through Each PDF File
        //2. Remove Background
        //3. Invert Color
        //4. Close Pdf

        String[] cHeadings = {"pg_0001", "pg_0005", "pg_0012"};

        for(int i = 1; i<10; i++){
            String filename = String.format("pg_%04d", i);
            String src = SRC + filename + ".pdf";
            String dest = DEST + filename + ".pdf";
            if(!Arrays.asList(cHeadings).contains(filename)){
                pdfRemoveBackground(src, dest);
            } else {
                //copy file as it is
                PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
                pdfDoc.close();
            }
        }
    }


    public static void pdfRemoveBackground(String src, String dest) throws Exception{
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfPage pdfPage = pdfDoc.getFirstPage();
        PdfDictionary pdfDict = pdfPage.getPdfObject();
        PdfDictionary resDict = pdfDict.getAsDictionary(new PdfName("Resources"));
        PdfDictionary objDict = resDict.getAsDictionary(new PdfName("XObject"));

        List<PdfName> excludekeys = new ArrayList<PdfName>();
        excludekeys.add(new PdfName("Fm0"));
        PdfDictionary newObjDict = objDict.clone(excludekeys);

        resDict.put(new PdfName("XObject"), newObjDict);
        pdfDoc.close();
    }

    */
}

