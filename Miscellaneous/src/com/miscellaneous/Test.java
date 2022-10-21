package com.miscellaneous;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

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
import java.util.Map;


public class Test {
	 @SuppressWarnings( "unchecked" )
//	@SuppressWarnings("resource")
	public static void main(String[] args) {

		String oldContent = new String();
		StringBuffer  newContent = new StringBuffer();
		// TODO Auto-generated method stub
		try {
			//	File inputFile = new File("C:\\Users\\cb8804\\OneDrive - SKF\\Desktop\\New folder (4)\\Schema\\AcknowledgeSalesOrder_2.4.xsd");
			File inputFile = new File("C:\\Users\\cb8804\\OneDrive - SKF\\Desktop\\New folder (4)\\New folder\\Schema\\AcknowledgeSalesOrder_4.2_PSF.xsd");
			File fileToBeModified  = new File("C:\\Users\\cb8804\\OneDrive - SKF\\Desktop\\New folder (4)\\New folder\\Schema\\AcknowledgeSalesOrder_4.2_PSF_openapplications.org_oagis.xsd");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
		 	HashMap<String, String> codenames = new HashMap<String, String>();
			HashMap<String, String> updateString = new HashMap<String, String>();
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
				String out = " ";
				String updatevalue ="";
				String updatekey ="";
				FileWriter writer = null;
				int x = 0 ;
				//one
				for(java.util.Map.Entry<String, String> mapping : entrySetSortedByValue)
				{
					
			 
					 
					if (!out.equals(mapping.getValue())) 
					{
						if (x != 0) {
							updateString.put(updatekey, updatevalue);
							updatekey = "";
//							System.out.println("\n reached");
						}
						else {
							
							
						}
						
						  out = mapping.getValue(); 
						updatevalue = new String( " <xs:element ref=\""+ out +"\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>");
						System.out.println(out);
					 
						
					
					}
					System.out.println(mapping.getKey());
					updatekey = updatekey +"\n"+ mapping.getKey();
					
					out = mapping.getValue();
					x++; 
					if ( x+1 == entrySetSortedByValue.size() )updateString.put(updatekey, updatevalue);
				}
			
				
				//oldContent = new String(Files.readAllBytes(Paths.get("C:\\Users\\cb8804\\OneDrive - SKF\\Desktop\\New folder (4)\\New folder\\Schema\\AcknowledgeSalesOrder_4.2_PSF_openapplications.org_oagis.xsd")));
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(fileToBeModified)));
				oldContent =  reader.lines().collect(Collectors.joining(System.getProperty("line.separator")));
				System.out.println("beforechange---------->---------------------------->--------------------->");
			System.out.println(oldContent);
//				BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified));
//				String line = reader.readLine();
//				while (line != null)
//				{
//				
//						oldContent.append(   line + System.lineSeparator());
//					
//					line = reader.readLine();
//				}
				System.out.println(updateString.size());
				
			 
				for (Map.Entry<String, String> set :
					updateString.entrySet()) {

					System.out.println(set.getKey()+"---->"+set.getValue());
					String value = set.getValue();									
					String key = set.getKey();
					oldContent = oldContent.replaceFirst( value ,  value + key  );
									 

				}
				System.out.println("afterchange---------->---------------------------->--------------------->");
				System.out.println(oldContent);
				
				Files.write(Paths.get("C:\\Users\\cb8804\\OneDrive - SKF\\Desktop\\New folder (4)\\New folder\\Schema\\files.txt"), oldContent.getBytes());
//				writer = new FileWriter(fileToBeModified);
//
//				writer.write(oldContent);
//				
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
