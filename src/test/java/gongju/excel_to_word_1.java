package gongju;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.xwpf.usermodel.*;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class excel_to_word_1 {
        @Test(groups = "ignore")
        public void excel_towww() throws BiffException, IOException {
       XWPFDocument document = new XWPFDocument();

       // Write the Document in file system
            File aaaa = new File("D:\\zhuomian\\Excel\\mxjtestTable.docx");
       FileOutputStream out = new FileOutputStream(aaaa);
       XWPFTable table = document.createTable();
       table.setWidth(1000);
       String path = "D:\\zhuomian\\Excel\\5.xls";
       Workbook wrb = Workbook.getWorkbook(new File(path));
       Sheet rs = wrb.getSheet(0);
            XWPFParagraph p3 = document.createParagraph();
            XWPFRun r3 = p3.createRun();
       XWPFTableRow tableRowOne ;
       int clos = rs.getColumns();
       int rows= rs.getRows();
       int m=0;
       for(int i=0;i<20;i++){
           for(int j=0;j<1;j++){
               if(i%8==0){
                   r3.addBreak();
                   table = document.createTable(8,4);
                   tableRowOne = table.getRow(0);
                   tableRowOne.getCell(0).setText(rs.getCell(j,i).getContents());
                   tableRowOne.addNewTableCell().setText(rs.getCell(j+1,i).getContents());
                   tableRowOne.addNewTableCell().setText(rs.getCell(j+2,i).getContents());
                   tableRowOne.addNewTableCell().setText(rs.getCell(j+3,i).getContents());
//                   document.write(out);
                   table.createRow();
                   m=1;
               }else{
                   tableRowOne = table.getRow(m);
                   tableRowOne.getCell(0).setText(rs.getCell(j,i).getContents());
                   tableRowOne.getCell(1).setText(rs.getCell(j+1,i).getContents());
                   tableRowOne.getCell(2).setText(rs.getCell(j+2,i).getContents());
                   tableRowOne.getCell(3).setText(rs.getCell(j+3,i).getContents());
//                   document.write(out);
                   m=m+1;
                  table.createRow();

               }

           }
       }
       document.write(out);
       out.close();
   }




}


