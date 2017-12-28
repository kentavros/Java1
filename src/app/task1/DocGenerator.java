package app.task1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DocGenerator {
	
	private String fileName = "Invitation1";
	private String filePath = "./tmp/Invitation1.docx";
	private int countDoc = 0;
	private int countChanges = 0;
	
	@SuppressWarnings("resource")
	public void createDoc(ArrayList<HashMap <String, String>> data) {
		try {
			System.out.println(dateCreatedDoc() + " in process ...");
			for (HashMap <String, String> employee : data) {
				employee.put("date", dateCreatedDoc());
				XWPFDocument doc = new XWPFDocument(OPCPackage.open(filePath));
				for (Map.Entry<String,String> entry : employee.entrySet()) {
					for (XWPFParagraph paragraph : doc.getParagraphs()) {
						List <XWPFRun> runs = paragraph.getRuns();
						if (runs != null) {
							for (XWPFRun text : runs) {
								String str = text.getText(0);
								if (str != null && str.contains("{{{"+ entry.getKey() +"}}}")) {
									str = str.replace("{{{"+ entry.getKey() +"}}}", entry.getValue());
									countChanges++;
									text.setText(str, 0);
								}
							}
						}
					}
				}
				doc.write(new FileOutputStream("./success/"+ fileName +"_"+ employee.get("lastName") +
						employee.get("firstName") +	employee.get("patronymic")+".docx"));
				countDoc++;
			}
			System.out.println("Done!");
			System.out.println("Created documents: " + countDoc + ".");
			System.out.println("Total replacements: " + countChanges + ".");
		} catch (InvalidOperationException e) {
			System.out.println("Error InvalidOperationException: " + e.getMessage());
		} catch (InvalidFormatException e) {
			System.out.println("Error InvalidFormatException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error IOException: " + e.getMessage());
		}
	}
	
	private String dateCreatedDoc() {
		Date dateNow = new Date();
		SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy'-'H:mm");
		String currentDateF = formatForDateNow.format(dateNow);
		return currentDateF;
	}
}