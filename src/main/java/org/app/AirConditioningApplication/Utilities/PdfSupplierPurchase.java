package org.app.AirConditioningApplication.Utilities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.app.AirConditioningApplication.Model.SupplierProduct;
import org.app.AirConditioningApplication.Model.SupplierPurchasedHistory;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

public class PdfSupplierPurchase {
    private final SupplierPurchasedHistory supplierPurchasedHistory;
    private final int quantityToBuy;

    public PdfSupplierPurchase(SupplierPurchasedHistory supplierPurchasedHistory, int quantityToBuy) {
        this.supplierPurchasedHistory = supplierPurchasedHistory;
        this.quantityToBuy = quantityToBuy;
    }

    public void pdfdownload() {
        Document document = new Document();
        try {
            String path = Paths.get("").toAbsolutePath().toString();
            String downloadFolderPath = path + "/src/main/resources/downloads/SupplierOrders/";
/*            String home = System.getProperty("user.home");
            String downloadFolderPath = home+"/Downloads/";*/
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(downloadFolderPath + "SupplierOrder " +
                    supplierPurchasedHistory.getSupplierOrderId() + ".pdf"));
            document.open();

            Paragraph p = new Paragraph();
            p.add("Supplier Purchased Table");
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);

            PdfPTable orderTable = new PdfPTable(2); // 2 columns.
            orderTable.setWidthPercentage(100); //Width 100%
            orderTable.setSpacingBefore(10f); //Space before orderTable
            orderTable.setSpacingAfter(10f); //Space after orderTable

            //Set Column widths
            float[] columnWidths = {2f, 2f};
            orderTable.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Supplier ID"));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);


            PdfPCell cell2 = new PdfPCell(new Paragraph("Total Price"));
            cell2.setBorderColor(BaseColor.BLUE);
            cell2.setPaddingLeft(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPTable supplierProductTable = null;

            //To avoid having the cell border and the content overlap, if you are having thick cell borders
            //cell1.setUserBorderPadding(true);
            //cell2.setUserBorderPadding(true);
            orderTable.addCell(cell1);
            orderTable.addCell(cell2);

            orderTable.addCell(supplierPurchasedHistory.getSupplierOrderId());
            orderTable.addCell(String.valueOf(supplierPurchasedHistory.getTotalPrice()));

            List<SupplierProduct> supplierProducts = supplierPurchasedHistory.getSupplierProducts();
            if (!supplierProducts.isEmpty())
                supplierProductTable = supplierProduct(supplierProducts);

            document.add(orderTable);
            document.add(supplierProductTable);

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PdfPTable supplierProduct(List<SupplierProduct> supplierProducts) throws DocumentException {
        PdfPTable productTable = new PdfPTable(6); // 6 columns.
        productTable.setWidthPercentage(100); //Width 100%
        productTable.setSpacingBefore(10f); //Space before productTable
        productTable.setSpacingAfter(10f); //Space after productTable


        //Set Column widths
        float[] columnWidths = {1f, 1f, 1f, 1f, 1f, 1f};
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

        PdfPCell cell3 = new PdfPCell(new Paragraph("Characteristics"));
        cell3.setBorderColor(BaseColor.BLUE);
        cell3.setPaddingLeft(10);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell4 = new PdfPCell(new Paragraph("Base Price"));
        cell3.setBorderColor(BaseColor.BLUE);
        cell3.setPaddingLeft(10);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell5 = new PdfPCell(new Paragraph("Tax"));
        cell3.setBorderColor(BaseColor.BLUE);
        cell3.setPaddingLeft(10);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell6 = new PdfPCell(new Paragraph("Quantity"));
        cell3.setBorderColor(BaseColor.BLUE);
        cell3.setPaddingLeft(10);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

        productTable.addCell(cell1);
        productTable.addCell(cell2);
        productTable.addCell(cell3);
        productTable.addCell(cell4);
        productTable.addCell(cell5);
        productTable.addCell(cell6);
        for (SupplierProduct supplierProduct : supplierProducts) {

            productTable.addCell(supplierProduct.getProductId().toString());
            productTable.addCell(supplierProduct.getName());
            productTable.addCell(String.valueOf(supplierProduct.getCharacteristics()));
            productTable.addCell(String.valueOf(supplierProduct.getBasePrice()));
            productTable.addCell(String.valueOf(supplierProduct.getTax()) + "%");
            productTable.addCell(String.valueOf(quantityToBuy));
        }
        return productTable;
    }
}
