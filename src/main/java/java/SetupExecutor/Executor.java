package java.SetupExecutor;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.turnxcel.converter.ConfigXmlToExcel;
import com.turnxcel.xmlreader.XmlParserCORE;
import com.turnxcel.xmlreader.XmlParserLTE;
import com.turnxcel.xmlreader.XmlParserT5G;

public class Executor {
	static Logger log = Logger.getLogger(Executor.class);
	public static void execute(String[] args) throws ParserConfigurationException, IOException, SAXException {
			Log4JProperty.configureLog4j();
			CreateInputOutputFolders();
			log.info("CMConfigurationTester- Credits: Aniket Jain (aniket.jain@rakuten.com/aniket.jain5256@gmail.com)");
			JOptionPane.showMessageDialog(null, "Keep all files in InputData folder");
			log.info("Keep all files in InputData folder");
			String[] techOptions = {"LTE","T5G","CORE"};
			int op = JOptionPane.showOptionDialog(null, "Please select the technology", "CMConfigurationTester- Credits: Aniket Jain (aniket.jain@rakuten.com/aniket.jain5256@gmail.com)", 0, 1, null, techOptions, null);
			if(Optional.ofNullable(op) == null) {
				throw new RuntimeException("Technology not selected, Get out !!");
			}
			Variables.XMLFilePath += JOptionPane.showInputDialog("Enter XML Configuration Filepath: ");
			if(Variables.XMLFilePath.endsWith("null")) {
				throw new RuntimeException("You have not provided the XML Config file path. Get out !!");
			}
			CheckXMLFileExtension();
			try {
				if(op==0) {
					XmlParserLTE.ReadXML(args);
					log.info("XML Parsed Successfully");
				}
				if(op==1) {
					XmlParserT5G.ReadXML(args);
					log.info("XML Parsed Successfully");
				}
				if(op==2) {
					XmlParserCORE.ReadXML(args);
					log.info("XML Parsed Successfully");
				}
				log.info("You will get XPaths, Values and Identifiers in the XMLtoExcel.xlsx file saved in OutputData folder");
				log.info("Going to Export Excel File at path: "+Variables.XMLtoExcelOutputDirectory);
				JOptionPane.showMessageDialog(null, "XMLtoExcel.xlsx file saved at: "+Variables.XMLtoExcelOutputDirectory);
				ConfigXmlToExcel.WriteExcel();
				log.info("XML to Excel file exported successfully");
				
				JOptionPane.showMessageDialog(null, "Logs are saved at: "+Variables.LogDirectory);
				log.info("Thanks for using");
			}
			catch (SAXParseException s) {
				log.error(s.getMessage());
				throw new RuntimeException(s.getMessage());
			}
			catch (ParserConfigurationException p) {
				throw new RuntimeException(p.getMessage());
			}
			catch(IOException i) {
				log.error(i.getMessage());
				throw new RuntimeException(i.getMessage());
			}
			catch (SAXException e) {
				log.error(e.getMessage());
				throw new RuntimeException(e.getMessage());
			}
	}
	
	public static void CreateInputOutputFolders() {
		File InputDataFolder = new File(Variables.InputDataDirectory);
		if(!InputDataFolder.exists()) {
			log.info("InputData folder does not exist, going to create");
			InputDataFolder.mkdir();
			log.info("InputData folder created successfully");
		}
		else {
			log.info("InputData folder already exist");
		}
		File OutputDataFolder = new File(Variables.OutputDataDirectory);
		if(!OutputDataFolder.exists()) {
			log.info("OutputData folder does not exist, going to create");
			OutputDataFolder.mkdir();
			log.info("OutputData folder created successfully");
		}
		else {
			log.info("OutputData folder already exist");
		}
	}
	
	public static void CheckXMLFileExtension() {
		if(Variables.XMLFilePath.endsWith(".xml")) {
			log.info("XML File path contains extension");
			log.info("XML File Path: "+Variables.XMLFilePath);
		}
		else {
			log.info("XML File Path does not contain extension");
			Variables.XMLFilePath = Variables.XMLFilePath+".xml";
			log.info("XML Extension added in xml filepath");
			log.info("XML File Path: "+Variables.XMLFilePath);
		}
	}
}