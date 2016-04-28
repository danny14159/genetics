package genetics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import bean.Disease;
import bean.Rna;
import bean.RnaDisease;
import orm.daoutils.Cnd;
import orm.daoutils.DBUtil;

public class ImportData {

	static DBUtil<Rna> rna = new DBUtil<Rna>("tb_rna", Rna.class);
	static DBUtil<Disease> disease = new DBUtil<Disease>("tb_disease", Disease.class);
	static DBUtil<RnaDisease> rel = new DBUtil<RnaDisease>("tb_rna_disease", RnaDisease.class);

	public static void main(String[] args) throws FileNotFoundException, IOException {

		File excelFile = new File("C:\\Users\\Danny\\Desktop\\genetics.xls");

		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelFile));

		// 获得指定的表
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 获得数据总行数
		int rowCount = sheet.getLastRowNum();
		
		for (int i = 0; i < rowCount; i++) {
			HSSFRow row =sheet.getRow(i);
			HSSFCell cellA=row.getCell(0);
			HSSFCell cellB=row.getCell(1);
			String cellAString = (String) getCellString(cellA);
			String cellBString = (String) getCellString(cellB);
			
			if(rna.load(Cnd.where("rna", "=", cellAString)) == null){
				
				rna.insert(new Rna(cellAString));
			}
			
			String[] diseases = cellBString.split(", ");
			for(String disease:diseases){
				
				if(ImportData.disease.load(Cnd.where("disease", "=", disease)) == null){
					
					ImportData.disease.insert(new Disease(disease));
				}
				
				if(rel.load(Cnd.where("rna", "=", cellAString).and("disease", "=", disease)) == null){
					
					
					rel.insert(new RnaDisease(rna.load(Cnd.where("rna", "=", cellAString)).getId(), ImportData.disease.load(Cnd.where("disease", "=", disease)).getId()));
				}
			}
			
			//System.out.println(cellAString+cellBString);
		}
		
		workbook.close();
	}

	/**
	 * 获得单元格中的内容
	 * 
	 * @param cell
	 * @return
	 */
	protected static Object getCellString(HSSFCell cell) {
		Object result = null;
		if (cell != null) {

			int cellType = cell.getCellType();

			switch (cellType) {

			case HSSFCell.CELL_TYPE_STRING:
				result = cell.getRichStringCellValue().getString();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				result = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				result = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				result = null;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				result = null;
				break;
			}
		}
		return result;
	}
}
