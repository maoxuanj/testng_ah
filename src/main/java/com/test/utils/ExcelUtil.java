package com.test.utils;

import jxl.*;

import java.io.File;

public class ExcelUtil {
    public static void main(String[] args) {
        Workbook workbook = null;

        try {
            workbook = Workbook.getWorkbook(new File("d:\\readExcel.xls"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheet(0);
        Cell cell = null;

        int columnCount=4;//代表列
        int rowCount=sheet.getRows();//得到列有多少行
        for (int i = 0; i <rowCount; i++) {
            for (int j = 0; j <columnCount; j++) {
                //注意，这里的两个参数，第一个是表示列的，第二才表示行
                cell=sheet.getCell(j, i);
                //要根据单元格的类型分别做处理，否则格式化过的内容可能会不正确
                if(cell.getType()==CellType.NUMBER){
                    System.out.print(((NumberCell)cell).getValue());
                }
                else if(cell.getType()==CellType.DATE){
                    System.out.print(((DateCell)cell).getDate());
                }
                else{
                    System.out.print(cell.getContents());
                }

                //System.out.print(cell.getContents());
                System.out.print("\t");
            }
            System.out.print("\n");
        }
        //关闭它，否则会有内存泄露
        workbook.close();

    }
}