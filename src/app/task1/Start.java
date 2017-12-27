package app.task1;

public class Start {
	public static void main(String[] args) {
		XmlReader xml = new XmlReader();
		DocGenerator doc = new DocGenerator();
		doc.createDoc(xml.readXml());
	}
}