/*
package org.app.AirConditioningApplication.Utilities;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.app.AirConditioningApplication.Model.Budget;
import org.app.AirConditioningApplication.Model.Product;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


*/
/**
 * The type User pdf exporter.
 *//*

public class PDFExporter {
    final private List<Budget> budgetList;

    */
/**
     * Instantiates a new User pdf exporter.
     *
     * @param budgetList the list users
     *//*

    public PDFExporter(List<Budget> budgetList) {
        this.budgetList = budgetList;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        PdfPCell cell1 = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Budget ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Customer Name", font));
        table.addCell(cell);

// Stream working
//        if(budgetList.stream().anyMatch(budget -> null != budget.getProductList())){
//
//        }

        cell.setPhrase(new Phrase("UNknown mara", font));
        table.addCell(cell);

//        cell1.setPhrase(new Phrase("Product ID", font));
//        table.addCell(cell1);
//
//        cell1.setPhrase(new Phrase("Product Name", font));
//        table.addCell(cell1);

        cell1.setPhrase(new Phrase("Product Price", font));
        table.addCell(cell1);

    }




    private void writeTableData(PdfPTable table) {
        int total =0;
        for (Budget budget : budgetList) {
            table.addCell(String.valueOf(budget.getBudgetId()));
            table.addCell(budget.getBudgetStatus());
            table.addCell(budget.getCustomer().getName());

//            List<Product> products = budget.getProductList();
//            if (!products.isEmpty()) {
//                for (Product product : budget.getProductList()) {
//                    table.addCell(product.getProductId().toString());
//                    table.addCell(product.getName());
//                    table.addCell(String.valueOf(product.getPrice()));
//                    total += product.getPrice();
//
//                }
//            }
            table.addCell(String.valueOf(total));
        }
    }

    */
/**
     * Export.
     *
     * @param response the response
     * @throws DocumentException the document exception
     * @throws IOException       the io exception
     *//*

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("BUDGET", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 1.5f, 1.5f, 3.5f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}*/
