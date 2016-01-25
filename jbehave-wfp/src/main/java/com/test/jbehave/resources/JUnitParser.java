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
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class generates a JUnit compatible XML output based on the JBehave test results
 *
 * Created by camiel on 1/25/16.
 */
public class JUnitParser {

    private static String outputDirectory = "test123.xml";
    private static List<Testcase> testcaseNodeList = new ArrayList<Testcase>();

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
            try { readHtmlSource(doc, new File(outputDirectory + "jbehave/storyDurations.props")); }
            catch (IOException e) { e.printStackTrace(); }

            for (int i = 0; i < testcaseNodeList.size(); i++) {
                Testcase testcase = testcaseNodeList.get(i);
                rootElement.appendChild(getTestcaseNode(doc, testcase.storyTime, testcase.storyClassname, testcase.storyName));
            }

            //write to xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputDirectory + "test-results.xml"));

            //result
            transformer.transform(source, result);
            System.out.println("XML output file saved in " + outputDirectory + "test-results.xml");

        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    private static Node getTestcaseNode(Document doc, String time, String classname, String name) {
        Element testcase = doc.createElement("testcase");
        testcase.setAttribute("time", time);
        testcase.setAttribute("classname", classname);
        testcase.setAttribute("name", name);
        return testcase;
    }

    private void readHtmlSource(Document doc, File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {
            //System.out.println(line);

            String storyName;
            String storyTime;
            String storyClassname;
            if (line.contains("com/test/jbehave/stories/")) {
                int seperatorIndex = line.indexOf("=");
                int lineLength = line.length();
                storyTime = line.substring(seperatorIndex+1);
                storyName = line.substring(33, seperatorIndex);
                storyClassname = line.substring(0, seperatorIndex);

                testcaseNodeList.add(new Testcase(storyTime, storyName, storyClassname));
                //System.out.println(storyName + " " + storyTime + " " + storyClassname);
            }


        }
        br.close();
    }

    private class Testcase {
        public String storyName;
        public String storyClassname;
        public String storyTime;

        public Testcase(String storyTime, String storyName, String storyClassname) {
            this.storyName = storyName;
            this.storyTime = storyTime;
            this.storyClassname = storyClassname;
        }
    }
}
