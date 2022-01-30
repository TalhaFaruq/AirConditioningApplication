package org.app.AirConditioningApplication.Utilities;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Model.Product;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

public class PdfBudgetTable {
    private final Budget budget;

    public PdfBudgetTable(Budget budget) {
        this.budget = budget;
    }


    public void pdfdownload() {
        Document document = new Document();
        try {
            String path = Paths.get("").toAbsolutePath().toString();
            String downloadFolderPath = path + "/src/main/resources/downloads/";

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(downloadFolderPath +
                    "Budget "+budget.getBudgetId()+".pdf"));
            document.open();

            Paragraph p = new Paragraph();
            p.add("Customer Name: "+ budget.getCustomer().getName());
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);

            PdfPTable budgetTable = new PdfPTable(3); // 3 columns.
            budgetTable.setWidthPercentage(100); //Width 100%
            budgetTable.setSpacingBefore(10f); //Space before budgetTable
            budgetTable.setSpacingAfter(10f); //Space after budgetTable

            //Set Column widths
            float[] columnWidths = {1f, 1f, 1f};
            budgetTable.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Budget ID"));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);


            PdfPCell cell2 = new PdfPCell(new Paragraph("Status"));
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
            budgetTable.addCell(cell1);
            budgetTable.addCell(cell2);
            budgetTable.addCell(cell3);
            budgetTable.addCell(budget.getBudgetId().toString());
            budgetTable.addCell(budget.getBudgetStatus());
            List<Product> productList = budget.getProductList();
            if (!productList.isEmpty())
                productTable = products(productList);
            budgetTable.addCell(String.valueOf(budget.getTotalPrice()));

            document.add(budgetTable);
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

