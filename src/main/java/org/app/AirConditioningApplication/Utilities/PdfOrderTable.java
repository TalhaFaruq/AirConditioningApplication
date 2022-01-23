package org.app.AirConditioningApplication.Utilities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.app.AirConditioningApplication.Model.Order;
import org.app.AirConditioningApplication.Model.Product;

import java.io.FileOutputStream;
import java.util.List;

public class PdfOrderTable {
    private final Order order;

    public PdfOrderTable(Order order) {
        this.order = order;
    }

    public void pdfdownload() {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Huawei\\Downloads\\order.pdf"));
            document.open();

            Paragraph p = new Paragraph();
            p.add("Customer Name: "+ order.getCustomer().getName());
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);

            PdfPTable orderTable = new PdfPTable(3); // 3 columns.
            orderTable.setWidthPercentage(100); //Width 100%
            orderTable.setSpacingBefore(10f); //Space before orderTable
            orderTable.setSpacingAfter(10f); //Space after orderTable

            //Set Column widths
            float[] columnWidths = {1f, 1f, 1f};
            orderTable.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Order ID"));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);


            PdfPCell cell2 = new PdfPCell(new Paragraph("Employee Price"));
            cell2.setBorderColor(BaseColor.BLUE);
            cell2.setPaddingLeft(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Total Price"));
            cell3.setBorderColor(BaseColor.BLUE);
            cell3.setPaddingLeft(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPTable productTable = null;

            //To avoid having the cell border and the content overlap, if you are having thick cell borders
            //cell1.setUserBorderPadding(true);
            //cell2.setUserBorderPadding(true);
            //cell3.setUserBorderPadding(true);
            orderTable.addCell(cell1);
            orderTable.addCell(cell2);
            orderTable.addCell(cell3);
            orderTable.addCell(order.getOrderId().toString());
            orderTable.addCell(String.valueOf(order.getEmpPrice()));
            List<Product> productList = order.getProductList();
            if (!productList.isEmpty())
                productTable = products(productList);
            orderTable.addCell(String.valueOf(order.getTotalPrice())); // The total price will be by quotation

            document.add(orderTable);
            document.add(productTable);

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public PdfPTable products(List<Product> productList) throws DocumentException {
        PdfPTable productTable = new PdfPTable(3); // 3 columns.
        productTable.setWidthPercentage(100); //Width 100%
        productTable.setSpacingBefore(10f); //Space before productTable
        productTable.setSpacingAfter(10f); //Space after productTable


        //Set Column widths
        float[] columnWidths = {1f, 1f, 1f};
        productTable.setWidths(columnWidths);

        PdfPCell cell1 = new PdfPCell(new Paragraph("Product ID"));
        cell1.setBorderColor(BaseColor.BLUE);
        cell1.setPaddingLeft(10);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);


        PdfPCell cell2 = new PdfPCell(new Paragraph("Product Name"));
        cell2.setBorderColor(BaseColor.BLUE);
        cell2.setPaddingLeft(10);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell3 = new PdfPCell(new Paragraph("Product Price"));
        cell3.setBorderColor(BaseColor.BLUE);
        cell3.setPaddingLeft(10);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

        productTable.addCell(cell1);
        productTable.addCell(cell2);
        productTable.addCell(cell3);
        for (Product product : productList) {
            productTable.addCell(product.getProductId().toString());
            productTable.addCell(product.getName());
            productTable.addCell(String.valueOf(product.getPrice()));
        }
        return productTable;
    }
}
