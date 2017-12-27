package app.task1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader {

	private String file = "./listOfEmployees.xml";
	private ArrayList<HashMap <String, String>> dataXml = new ArrayList<HashMap <String, String>>(); 
	
	public ArrayList<HashMap <String, String>> readXml() {
		try {
			System.out.println("Start ...");
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(this.file);
			Node root = document.getDocumentElement();
			NodeList employees = root.getChildNodes();
			int index = 0;
			for (int i=0; i < employees.getLength(); i++) {
				Node employee = employees.item(i);
				if (employee.getNodeType() != Node.TEXT_NODE) {
					NodeList employeeProps = employee.getChildNodes();
					HashMap <String, String> nameAndValue = new HashMap<>();
					dataXml.add(nameAndValue);
					for (int j=0; j < employeeProps.getLength(); j++) {
						Node employeeProp = employeeProps.item(j);
						if (employeeProp.getNodeType() != Node.TEXT_NODE) {
							dataXml.get(index).put(employeeProp.getNodeName(), employeeProp.getChildNodes().item(0).getTextContent());
						}
					}
					index++;
				}
			}
		} catch (ParserConfigurationException ex){
			System.out.println("Error ParserConfigurationException: " + ex.getMessage());
		} catch (SAXException ex) {
            System.out.println("Error SAXException: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error IOException: " + ex.getMessage());
        }
		return dataXml;
	}
}