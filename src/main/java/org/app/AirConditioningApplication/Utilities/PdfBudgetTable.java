package org.app.AirConditioningApplication.Utilities;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Model.Services;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
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
            String downloadFolderPath = path + "/src/main/resources/downloads/Budgets/";
/*            String home = System.getProperty("user.home");
            String downloadFolderPath = home+"/Downloads/";*/

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(downloadFolderPath +
                    "Budget " + budget.getBudgetId() + ".pdf"));
            document.open();

            Header header = new Header("Invoice", "Budget");


            Paragraph p = new Paragraph();
            p.add("Customer Name: " + budget.getCustomer().getName());
            p.setAlignment(Element.ALIGN_LEFT);

            Paragraph date = new Paragraph();
            date.add(LocalDate.now().toString());
            date.setAlignment(Element.ALIGN_RIGHT);

            document.add(header);
            document.add(p);
            document.add(date);


            PdfPTable budgetTable = new PdfPTable(4); // 4 columns.
            budgetTable.setWidthPercentage(100); //Width 100%
            budgetTable.setSpacingBefore(10f); //Space before budgetTable
            budgetTable.setSpacingAfter(10f); //Space after budgetTable

            //Set Column widths
            float[] columnWidths = {1f, 1f, 1f, 1f};
            budgetTable.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Budget ID"));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell4 = new PdfPCell(new Paragraph("Budget Name"));
            cell4.setBorderColor(BaseColor.BLUE);
            cell4.setPaddingLeft(10);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);


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


            //To avoid having the cell border and the content overlap, if you are having thick cell borders
            //cell1.setUserBorderPadding(true);
            //cell2.setUserBorderPadding(true);
            //cell3.setUserBorderPadding(true);
            budgetTable.addCell(cell1);
            budgetTable.addCell(cell4);
            budgetTable.addCell(cell2);
            budgetTable.addCell(cell3);

            budgetTable.addCell(budget.getBudgetId().toString());
            budgetTable.addCell(budget.getBudgetName());
            budgetTable.addCell(budget.getBudgetStatus());
            budgetTable.addCell(String.valueOf(budget.getTotalPrice()));

            document.add(budgetTable);

            List<Product> productList = budget.getProductList();
            if (!productList.isEmpty()) {

                Paragraph p2 = new Paragraph();
                p2.add("Products");
                p2.setAlignment(Element.ALIGN_CENTER);
                document.add(p2);

                PdfPTable productTable = products(productList);
                document.add(productTable);
            }

            List<Services> servicesList = budget.getService();
            if (!servicesList.isEmpty()) {

                Paragraph p3 = new Paragraph();
                p3.add("Services");
                p3.setAlignment(Element.ALIGN_CENTER);
                document.add(p3);

                PdfPTable serviceTable = services(servicesList);
                document.add(serviceTable);
            }

            document.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PdfPTable products(List<Product> productList) throws DocumentException {
        PdfPTable productTable = new PdfPTable(4); // 4 columns.
        productTable.setWidthPercentage(100); //Width 100%
        productTable.setSpacingBefore(10f); //Space before productTable
        productTable.setSpacingAfter(10f); //Space after productTable


        //Set Column widths
        float[] columnWidths = {1f, 1f, 1f, 1f};
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

        PdfPCell cell4 = new PdfPCell(new Paragraph("Product Quantity"));
        cell4.setBorderColor(BaseColor.BLUE);
        cell4.setPaddingLeft(10);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

        productTable.addCell(cell1);
        productTable.addCell(cell2);
        productTable.addCell(cell3);
        productTable.addCell(cell4);
        for (Product product : productList) {
            productTable.addCell(product.getProductId().toString());
            productTable.addCell(product.getName());
            productTable.addCell(String.valueOf(product.getPrice()));
            productTable.addCell(String.valueOf(product.getProductQuantity()));
        }
        return productTable;
    }

    public PdfPTable services(List<Services> servicesList) throws DocumentException {

        PdfPTable servicesTable = new PdfPTable(2); // 2 columns.
        servicesTable.setWidthPercentage(100); //Width 100%
        servicesTable.setSpacingBefore(10f); //Space before servicesTable
        servicesTable.setSpacingAfter(10f); //Space after servicesTable


        //Set Column widths
        float[] columnWidths = {1f, 1f};
        servicesTable.setWidths(columnWidths);

        PdfPCell cell1 = new PdfPCell(new Paragraph("Service ID"));
        cell1.setBorderColor(BaseColor.BLUE);
        cell1.setPaddingLeft(10);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);


        PdfPCell cell2 = new PdfPCell(new Paragraph("Service Name"));
        cell2.setBorderColor(BaseColor.BLUE);
        cell2.setPaddingLeft(10);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

        servicesTable.addCell(cell1);
        servicesTable.addCell(cell2);
        for (Services services : servicesList) {
            servicesTable.addCell(services.getServiceId().toString());
            servicesTable.addCell(services.getType());
        }
        return servicesTable;
    }

}

