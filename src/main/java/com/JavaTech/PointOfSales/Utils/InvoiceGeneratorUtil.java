package com.JavaTech.PointOfSales.utils;

import com.JavaTech.PointOfSales.dto.CustomerDTO;
import com.JavaTech.PointOfSales.model.OrderDetail;
import com.JavaTech.PointOfSales.model.OrderProduct;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGeneratorUtil {

    public static void main(String[] args) throws FileNotFoundException {
//        Generate();
    }

    public static ByteArrayOutputStream  Generate(CustomerDTO customerDTO, OrderProduct orderProduct) throws FileNotFoundException, MalformedURLException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        Document document = new Document(pdfDocument);

        String imagePath = "src/main/resources/static/assets/logo.png";
        ImageData imageData = ImageDataFactory.create(imagePath);
        Image image = new Image(imageData);

        float x = pdfDocument.getDefaultPageSize().getWidth()/2;
        float y = pdfDocument.getDefaultPageSize().getHeight()/2;
        image.setFixedPosition(x,y);
        image.setOpacity(0.1f);
        document.add(image);

        float fourcol=142.5f;
        float threecol=190f;
        float twocol=285f;
        float twocol150=twocol+150f;
        float[] twocolumnWidth = {twocol150, twocol};
        float[] threecolumnWidth = {threecol, threecol, threecol};
        float[] fourcolumnWidth = {fourcol, fourcol, fourcol, fourcol};
        float[] fullWidth = {threecol*3};
        Paragraph onesp = new Paragraph("\n");

        Table table = new Table(twocolumnWidth);
        table.addCell(new Cell().add("Invoice").setFontSize(20f).setBorder(com.itextpdf.layout.border.Border.NO_BORDER).setBold());

        Table nestTable = new Table(new float[]{twocol/2, twocol/3});
        nestTable.addCell(getHeaderTextCell("Invoice No: "));
        nestTable.addCell(getHeaderTextCellValue(String.valueOf(orderProduct.getId())));
        nestTable.addCell(getHeaderTextCell("Invoice Date: "));
        nestTable.addCell(getHeaderTextCellValue(String.valueOf(orderProduct.getCreatedAt())));

        table.addCell(new Cell().add(nestTable).setBorder(com.itextpdf.layout.border.Border.NO_BORDER).setBold());

        Border gb = new SolidBorder(Color.GRAY, 2f);
        Table divider = new Table(fullWidth);
        divider.setBorder(gb);
        document.add(table);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);

        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getBillingandShippingCell("Customer information"));
        document.add(twoColTable.setMarginBottom(12f));

//        Table twoColTable2 = new Table(twocolumnWidth);
//        twoColTable2.addCell(getCell10Left("Company", true));
//        twoColTable2.addCell(getCell10Left("Name", true));
//        twoColTable2.addCell(getCell10Left("CIT", false));
//        twoColTable2.addCell(getCell10Left("Company", false));
//        document.add(twoColTable2);

//        Table twoColTable3 = new Table(twocolumnWidth);
//        twoColTable3.addCell(getCell10Left("Name", true));
//        twoColTable3.addCell(getCell10Left("Address", true));
//        twoColTable3.addCell(getCell10Left(customerDTO.getName(), false));
//        twoColTable3.addCell(getCell10Left("HCM", false));
//        document.add(twoColTable3);

        float[] oneColumnwidth = {twocol150};
        Table oneColTable1 = new Table(oneColumnwidth);
        oneColTable1.addCell(getCell10Left("Name", true));
        oneColTable1.addCell(getCell10Left(customerDTO.getName(), false));
        oneColTable1.addCell(getCell10Left("Address", true));
        oneColTable1.addCell(getCell10Left(customerDTO.getAddress(), false));
        oneColTable1.addCell(getCell10Left("Phone", true));
        oneColTable1.addCell(getCell10Left(customerDTO.getPhone(), false));
        document.add(oneColTable1.setMarginBottom(10f));

        Table divider2 = new Table(fullWidth);
        Border dgb = new DashedBorder(Color.GRAY, 0.5f);
        divider2.setBorder(dgb);
        Paragraph productPara = new Paragraph("Products");
        document.add(productPara.setBold());

        Table threeColTable1 = new Table(fourcolumnWidth);
        threeColTable1.setBackgroundColor(Color.BLACK, 0.7f);

        threeColTable1.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable1.addCell(new Cell().add("Price").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(com.itextpdf.layout.border.Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(com.itextpdf.layout.border.Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Sub Total").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginRight(15f);
        document.add(threeColTable1);

        Table threeColTable2 = new Table(fourcolumnWidth);
        for (OrderDetail OrderDetail: orderProduct.getOrderItems()){
            float total =  OrderDetail.getQuantity()*OrderDetail.getProduct().getRetailPrice();
            threeColTable2.addCell(new Cell().add(OrderDetail.getProduct().getName()).setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(String.valueOf(OrderDetail.getProduct().getRetailPrice())).setTextAlignment(TextAlignment.CENTER).setBorder(com.itextpdf.layout.border.Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(String.valueOf(OrderDetail.getQuantity())).setTextAlignment(TextAlignment.CENTER).setBorder(com.itextpdf.layout.border.Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(String.valueOf(total)).setTextAlignment(TextAlignment.RIGHT).setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginRight(15f);
        }

        document.add(threeColTable2.setMarginBottom(20f));
        float[] onetwo = {threecol+125f, threecol*2};
        Table threeColTable4 = new Table(onetwo);
        threeColTable4.addCell(new Cell().add("").setBorder(com.itextpdf.layout.border.Border.NO_BORDER));
        threeColTable4.addCell(new Cell().add(divider2).setBorder(com.itextpdf.layout.border.Border.NO_BORDER));
        document.add(threeColTable4);

        //====================================total amount====================================
        Table threeColTable3 = new Table(threecolumnWidth);
        threeColTable3.addCell(new Cell().add("").setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable3.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.CENTER).setBorder(com.itextpdf.layout.border.Border.NO_BORDER));
        threeColTable3.addCell(new Cell().add(String.valueOf(orderProduct.getTotalAmount())).setTextAlignment(TextAlignment.RIGHT).setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginRight(15f);
        document.add(threeColTable3);

        document.add(threeColTable4);
        //====================================cash====================================
        Table threeColTable5 = new Table(threecolumnWidth);
        threeColTable5.addCell(new Cell().add("").setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable5.addCell(new Cell().add("Cash").setTextAlignment(TextAlignment.CENTER).setBorder(com.itextpdf.layout.border.Border.NO_BORDER));
        threeColTable5.addCell(new Cell().add(String.valueOf(orderProduct.getCash())).setTextAlignment(TextAlignment.RIGHT).setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginRight(15f);
        document.add(threeColTable5);

        document.add(threeColTable4);
        //====================================change====================================
        Table threeColTable6 = new Table(threecolumnWidth);
        threeColTable6.addCell(new Cell().add("").setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable6.addCell(new Cell().add("Change").setTextAlignment(TextAlignment.CENTER).setBorder(com.itextpdf.layout.border.Border.NO_BORDER));
        threeColTable6.addCell(new Cell().add(String.valueOf(orderProduct.getCash() - orderProduct.getTotalAmount())).setTextAlignment(TextAlignment.RIGHT).setBorder(com.itextpdf.layout.border.Border.NO_BORDER)).setMarginRight(15f);
        document.add(threeColTable6);

        document.add(divider2);
        document.add(new Paragraph("\n"));
        document.add(divider.setBorder(new SolidBorder(Color.GRAY,1)).setMarginBottom(15f));

        Table tb = new Table(fullWidth);
        tb.addCell(new Cell().add("TERMS AND CONDITION\n").setBold().setBorder(Border.NO_BORDER));
        List<String> TncList = new ArrayList<>();
        TncList.add("1. 7-Day Payment Terms Template");
        TncList.add("2. 14-Day Payment Terms Template");
        TncList.add("3. 30-Day Payment Terms Template");
        TncList.add("4. Generic Payment Terms Template");
        TncList.add("5. C.O.D. (Cash on Delivery) Payment Terms Template");

        for(String tnc: TncList){
            tb.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER));
        }
        document.add(tb);

        document.close();

        return outputStream;
    }

    static Cell getHeaderTextCell(String textValue){
        return new Cell().add(textValue).setBold().setBorder(com.itextpdf.layout.border.Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }
    static Cell getHeaderTextCellValue(String textValue){
        return new Cell().add(textValue).setBorder(com.itextpdf.layout.border.Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getBillingandShippingCell(String textValue){
        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(com.itextpdf.layout.border.Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCell10Left(String textValue, Boolean isBold){
        Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(com.itextpdf.layout.border.Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? myCell.setBold() : myCell;
    }
}