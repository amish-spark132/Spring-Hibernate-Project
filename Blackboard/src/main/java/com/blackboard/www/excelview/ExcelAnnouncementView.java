package com.blackboard.www.excelview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.blackboard.www.pojo.Announcement;


public class ExcelAnnouncementView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Content-disposition", "attachment; filename=\"announcement_list.xls\"");
		@SuppressWarnings("unchecked")
		ArrayList<Announcement> list = (ArrayList<Announcement>)model.get("list");
		//System.out.println(list.size());
		HSSFSheet sheet = workbook.createSheet("Announcement");

		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("AnnouncementId");
		header.createCell(1).setCellValue("Course");
		header.createCell(2).setCellValue("CreationDate");
		header.createCell(3).setCellValue("Message");
		header.createCell(4).setCellValue("UpdateDate");
		header.createCell(5).setCellValue("Posted By");
		

		int rowNum = 1;
		for (Announcement data : list) {
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(data.getId());
			row.createCell(1).setCellValue(data.getCourse().toString());
			row.createCell(2).setCellValue(data.getCreateDateTime().toString());
			row.createCell(3).setCellValue(data.getMessage());
			row.createCell(4).setCellValue(data.getUpdateDateTime().toString());
			row.createCell(5).setCellValue(data.getPostedBy().toString());
		}
	}
}
