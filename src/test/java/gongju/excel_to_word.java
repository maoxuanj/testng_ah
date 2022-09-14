package gongju;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileOutputStream;

public class excel_to_word {
    public static void main(String[] args) throws Exception {
        // Blank Document
        XWPFDocument document = new XWPFDocument();

        // Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File("create_table.docx"));

        // create table
        XWPFTable table = document.createTable();

        table.setWidth(1000);

        // create first row
        XWPFTableRow tableRowOne = table.getRow(0);

        tableRowOne.getCell(0).setText("1 x 1");
        tableRowOne.addNewTableCell().setText("2 x 1");
        tableRowOne.addNewTableCell().setText("3 x 1");

        // create second row
        XWPFTableRow tableRowTwo = table.createRow();

        tableRowTwo.getCell(0).setText("1 x 2");
        tableRowTwo.getCell(1).setText("2 x 2");
        tableRowTwo.getCell(2).setText("3 x 2");

        // create third row
        XWPFTableRow tableRowThree = table.createRow();

        tableRowThree.getCell(0).setText("1 x 3");
        tableRowThree.getCell(1).setText("2 x 3");
        tableRowThree.getCell(2).setText("3 x 3");

        document.write(out);
        out.close();

        System.out.println("create_table.docx written successully");
    }
}


