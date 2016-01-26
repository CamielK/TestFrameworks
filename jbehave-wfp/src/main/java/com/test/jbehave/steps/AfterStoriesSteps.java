package com.test.jbehave.steps;

import com.test.jbehave.resources.Driver;
import com.test.jbehave.resources.JUnitParser;

/**
 *
 * This class is executed after all story's
 *
 * Created by camiel on 12/12/15.
 */
public class AfterStoriesSteps {

    private Driver driver = new Driver();
    private JUnitParser junitParser = new JUnitParser();

    //this method is automaticly called when all story's have been executed
    @org.jbehave.core.annotations.AfterStories
    public void afterAllStories() {
        System.out.println("closing all left open drivers...");
        driver.closeAllDrivers(); //call to Driver to close all drivers

//        try {
//            Thread.sleep(100000);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }

        junitParser.generateXML();
    }

}
