package com.test.jbehave.resources;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 *
 * This class generates a JUnit compatible XML output of the test results
 *
 * Created by camiel on 1/25/16.
 */
public class JUnitParser {

    private static String outputDirectory = "test123.xml";

    public void generateXML() {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("testsuite");
            doc.appendChild(rootElement);

            //properties
            Element properties = doc.createElement("properties");
            rootElement.appendChild(properties);

            //add testcases
            //TODO get testcase data
            rootElement.appendChild(getTestcase(doc, "0.512","test1","test"));

            //write to xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputDirectory));

            //result
            transformer.transform(source, result);
            System.out.println("XML output file saved in " + outputDirectory);

        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory + "/test-results.xml";
    }

    private static Node getTestcase(Document doc, String time, String classname, String name) {
        Element testcase = doc.createElement("testcase");
        testcase.setAttribute("time", time);
        testcase.setAttribute("classname", classname);
        testcase.setAttribute("name", name);
        return testcase;
    }

}
