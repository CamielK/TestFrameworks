package com.test.jbehave;

import com.test.jbehave.steps.AfterStories;
import com.test.jbehave.steps.backend.BackendLoginSteps;
import com.test.jbehave.steps.backend.MakeUserSteps;
import com.test.jbehave.steps.frontend.AanmeldingClientSteps;
import com.test.jbehave.steps.frontend.AnamneseFormSteps;
import com.test.jbehave.steps.frontend.FrontendLoginSteps;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;

/**
 * This class contains configuration elements to setup the JBehave test suite
 */
public class MyStories extends JUnitStories {

    private final CrossReference xref = new CrossReference();
    private String executionTime, outputPath;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HHmmss");
    private Date date;
    
    public MyStories() {
        EmbedderControls embedderControls = configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(true)
                .doIgnoreFailureInView(true).useThreads(1).useStoryTimeoutInSecs(240); //set ammount of concurrent threads and general timeout time

        //move output files to new directory
        date = new Date();
        executionTime = dateFormat.format(date);
        outputPath = "outputs/"+ new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date())+"/";
        System.out.println("output files can be found in: "+outputPath); System.out.println("");
    }

    //various configurations, including output
    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        // Start from default ParameterConverters instance
        ParameterConverters parameterConverters = new ParameterConverters();
        // factory to allow parameter conversion and loading from external resources (used by StoryParser too)
        ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(), new LoadFromClasspath(embeddableClass), parameterConverters);
        // add custom converters
        parameterConverters.addConverters(new DateConverter(new SimpleDateFormat("yyyy-MM-dd")),
                new ExamplesTableConverter(examplesTableFactory));
        return new MostUsefulConfiguration()
                .useStoryControls(new StoryControls().doDryRun(false).doSkipScenariosAfterFailure(false))
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryParser(new RegexStoryParser(examplesTableFactory))
                .useStoryPathResolver(new UnderscoredCamelCaseResolver())
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                //.withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                                .withCodeLocation(CodeLocations.codeLocationFromPath("outputs/"+ new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date()) + "/testDir"))
                                .withDefaultFormats().withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
                                .withViewResources(viewResources).withFormats(CONSOLE, HTML)
                                //.withViewResources(viewResources).withFormats(CONSOLE, TXT, HTML, XML)
                                .withFailureTrace(true).withFailureTraceCompression(true).withCrossReference(xref))
                .useParameterConverters(parameterConverters)
                // use '%' instead of '$' to identify parameters
                .useStepPatternParser(new RegexPrefixCapturingPatternParser("%"))
                .useStepMonitor(xref.getStepMonitor());
    }

    //add new step classes here so that they may be called when executing story's
    //if a class is not added here the tests will return 'Pending()' when executed
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new BackendLoginSteps(), new FrontendLoginSteps(), new MakeUserSteps(), new AanmeldingClientSteps(), new AfterStories(), new AnamneseFormSteps());
    }

    //point to the story or story's you want to execute
    @Override
    protected List<String> storyPaths() {
        //switch comments to run a single story only
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/backendLogin.story", "");
        //return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/*.story", "**/excluded*.story");

    }
        
}