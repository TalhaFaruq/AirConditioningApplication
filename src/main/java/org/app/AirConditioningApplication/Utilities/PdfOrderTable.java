package org.app.AirConditioningApplication.Utilities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.app.AirConditioningApplication.Model.Order;
import org.app.AirConditioningApplication.Model.Product;
import org.app.AirConditioningApplication.Model.Services;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class PdfOrderTable {
    private final Order order;

    public PdfOrderTable(Order order) {
        this.order = order;
    }

    public void pdfdownload() {
        Document document = new Document();
        try {
            String path = Paths.get("").toAbsolutePath().toString();
            String downloadFolderPath = path + "/src/main/resources/downloads/CustomerOrders/";

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(downloadFolderPath +
                    order.getOrderId()+".pdf"));

            document.open();

            Header header = new Header("Invoice","Order");


            Paragraph p = new Paragraph();
            p.add("Customer Name: " + order.getCustomer().getName());
            p.setAlignment(Element.ALIGN_LEFT);

            Paragraph date = new Paragraph();
            date.add(LocalDate.now().toString());
            date.setAlignment(Element.ALIGN_RIGHT);

            document.add(header);
            document.add(p);
            document.add(date);

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

            //To avoid having the cell border and the content overlap, if you are having thick cell borders
            //cell1.setUserBorderPadding(true);
            //cell2.setUserBorderPadding(true);
            //cell3.setUserBorderPadding(true);
            orderTable.addCell(cell1);
            orderTable.addCell(cell2);
            orderTable.addCell(cell3);
            orderTable.addCell(order.getOrderId().toString());
            orderTable.addCell(String.valueOf(order.getEmpPrice()));
            orderTable.addCell(String.valueOf(order.getTotalPrice())); // The total price will be by quotation

            document.add(orderTable);

            List<Product> productList = order.getProductList();
            if (!productList.isEmpty()) {

                Paragraph p2 = new Paragraph();
                p2.add("Products");
                p2.setAlignment(Element.ALIGN_CENTER);
                document.add(p2);

                PdfPTable productTable = products(productList);
                document.add(productTable);
            }

            List<Services> servicesList = order.getService();
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
