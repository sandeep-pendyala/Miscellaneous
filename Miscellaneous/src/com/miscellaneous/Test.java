package com.miscellaneous;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
import java.util.Collections; 
import java.util.List;

 
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//	File inputFile = new File("C:\\Users\\cb8804\\OneDrive - SKF\\Desktop\\New folder (4)\\Schema\\AcknowledgeSalesOrder_2.4.xsd");
			File inputFile = new File("C:\\Users\\cb8804\\OneDrive - SKF\\Desktop\\New folder (4)\\New folder\\Schema\\AcknowledgeSalesOrder_4.2_PSF.xsd");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			HashMap<String, String> codenames = new HashMap<String, String>();
			////Get XPath expression
			XPathFactory xpathfactory = XPathFactory.newInstance();
			XPath xpath = xpathfactory.newXPath();
			xpath.setNamespaceContext(new NamespaceResolver(doc));
			NodeList nList = doc.getElementsByTagName("xs:element");
			System.out.println("----------------------------");
			int i = 0;
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node node = nList.item(temp);
				if (node.hasAttributes()) {
					Attr attr = (Attr) node.getAttributes().getNamedItem("substitutionGroup");
					if (attr != null) {
						String attribute= attr.getValue();  
						Attr attr1 = (Attr) node.getAttributes().getNamedItem("name");
						String attribute1= attr1.getValue();  
						String A = new String( " <xs:element ref=\"skf:"+ attribute1 +"\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>");
						System.out.print("attribute1: "+ ++i + " -->             "+ attribute1);   
						String B = new String(attribute);
						System.out.println(" "+    "      --->         "+ attribute);   
						codenames.put(A, B);

					}



				}
			}

			// Now let's sort the HashMap by values 
			// there is no direct way to sort HashMap by values but you 
			// can do this by writing your own comparator, which takes
			// Map.Entry object and arrange them in order increasing 
			// or decreasing by values.
			//			TreeMap<String, String> sorted = new TreeMap<>(codenames);
			//			Set<Entry<String, String>> mappings = sorted.entrySet();

			Set<java.util.Map.Entry<String,String>>  entries =  codenames.entrySet();
			Comparator<java.util.Map.Entry<String, String>> valueComparator = new Comparator<java.util.Map.Entry<String,String>>() 
			{
				@Override
				public int compare(java.util.Map.Entry<String, String> e1, java.util.Map.Entry<String, String> e2) { 
					String v1 = e1.getValue(); String v2 = e2.getValue(); return v1.compareTo(v2); 
				}}; 
				// Sort method needs a List, so let's first convert Set to List in Java 
				List<java.util.Map.Entry<String, String>> listOfEntries = new ArrayList<java.util.Map.Entry<String, String>>(entries); 
				// sorting HashMap by values using comparator 
				Collections.sort(listOfEntries, valueComparator); 
				LinkedHashMap<String, String> sortedByValue = new LinkedHashMap<String, String>(listOfEntries.size()); 
				// copying entries from List to Map 
				for(java.util.Map.Entry<String, String> entry : listOfEntries){ 
					sortedByValue.put(entry.getKey(), entry.getValue());
				}
				System.out.println("HashMap after sorting entries by values ");
				Set<java.util.Map.Entry<String, String>> entrySetSortedByValue = sortedByValue.entrySet(); 
				for(java.util.Map.Entry<String, String> mapping : entrySetSortedByValue)
				{ 
					System.out.println(mapping.getKey() + " ==================> " + mapping.getValue()); 

				}


		}

		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
