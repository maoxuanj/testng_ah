package gongju;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class excel_change {

    //读取excel

        String path = "D:\\zhuomian\\Excel\\3.xls";
        Workbook wrb = Workbook.getWorkbook(new File(path));
        Sheet rs = wrb.getSheet(0);
    File tempFile=new File("D:\\zhuomian\\Excel\\5.xls");
    WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
    WritableSheet sheet = workbook.createSheet("1111", 1);
        int clos = rs.getColumns();
        int rows= rs.getRows();
        int clos_write =0;
        int rows_write =0;
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
        WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
        String name123;
        String val123;
        Label l=null;
    public excel_change() throws BiffException, IOException {
    }

    @Test
        public void test_excel() throws WriteException, IOException {
            for(int i=0;i<rows;i++){
                l =new Label(clos_write,rows_write,"项目名称",titleFormat);
                sheet.addCell(l);
                clos_write++;
                l =new Label(clos_write,rows_write,"AAA项目",titleFormat);
                sheet.addCell(l);
                clos_write++;
                l =new Label(clos_write,rows_write,"版本",titleFormat);
                sheet.addCell(l);
                clos_write++;
                l =new Label(clos_write,rows_write,"1.0.0",titleFormat);
                sheet.addCell(l);
                clos_write=0;
                rows_write++;
                l =new Label(clos_write,rows_write,"编制时间",titleFormat);
                sheet.addCell(l);
                //列+1
                clos_write++;
                l =new Label(clos_write,rows_write,"2022.07.18",titleFormat);
                sheet.addCell(l);
                //列清空，行+1
                clos_write=0;
                rows_write++;
                for(int j=0;j<1;j++){
                    name123= rs.getCell(j,i).getContents();
                    l =new Label(clos_write,rows_write,"用例编号",titleFormat);
                    sheet.addCell(l);
                    clos_write++;
                    l =new Label(clos_write,rows_write,rs.getCell(j,i).getContents(),titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;
                    l =new Label(clos_write,rows_write,"模块",titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;
                    l =new Label(clos_write,rows_write,rs.getCell(j+1,i).getContents(),titleFormat);
                    sheet.addCell(l);

                    //列清空，行+1
                    clos_write=0;
                    rows_write++;
                    l =new Label(clos_write,rows_write,"标题",titleFormat);
                    sheet.addCell(l);
                    clos_write++;
                    l =new Label(clos_write,rows_write,rs.getCell(j+2,i).getContents(),titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;
                    l =new Label(clos_write,rows_write,"优先级",titleFormat);
                    sheet.addCell(l);

                    clos_write++;
                    l =new Label(clos_write,rows_write,rs.getCell(j+6,i).getContents(),titleFormat);
                    sheet.addCell(l);

                    //列清空，行+1
                    clos_write=0;
                    rows_write++;

                    l =new Label(clos_write,rows_write,"前置条件",titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;
                    l =new Label(clos_write,rows_write,rs.getCell(j+3,i).getContents(),titleFormat);
                    sheet.addCell(l);

                    //列清空，行+1
                    clos_write=0;
                    rows_write++;

                    l =new Label(clos_write,rows_write,"步骤",titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;

                    l =new Label(clos_write,rows_write,"期望结果",titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;

                    l =new Label(clos_write,rows_write,"实际结果",titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;

                    l =new Label(clos_write,rows_write,"是否通过",titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;

                    //列清空，行+1
                    clos_write=0;
                    rows_write++;

                    l =new Label(clos_write,rows_write,rs.getCell(j+4,i).getContents(),titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;

                    l =new Label(clos_write,rows_write,rs.getCell(j+5,i).getContents(),titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;

                    l =new Label(clos_write,rows_write,rs.getCell(j+5,i).getContents(),titleFormat);
                    sheet.addCell(l);
                    //列+1
                    clos_write++;

                    l =new Label(clos_write,rows_write,"P",titleFormat);
                    sheet.addCell(l);

                    clos_write=0;
                    rows_write++;
                    l =new Label(clos_write,rows_write,"",titleFormat);
                    sheet.addCell(l);
                    //列清空，行+1
                    rows_write++;

                    j++;
                }
            }
        workbook.write();
        workbook.close();
    }





}
