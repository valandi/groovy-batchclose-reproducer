import com.applitools.eyes.BatchInfo
import com.applitools.eyes.RectangleSize
import com.applitools.eyes.config.Configuration
import com.applitools.eyes.fluent.BatchClose
import com.applitools.eyes.fluent.EnabledBatchClose
import com.applitools.eyes.images.Eyes
import com.applitools.eyes.images.Target
import spock.lang.Shared
import spock.lang.Specification

import javax.imageio.ImageIO

class EyesImageTest extends Specification {
    @Shared
    BatchInfo batchInfo;

    @Shared
    Eyes eyes;


    def setupSpec(){
        batchInfo = new BatchInfo("Thumbnail Tests");
        batchInfo.setNotifyOnCompletion(true);
        Configuration configuration = new Configuration();
        eyes = new Eyes();
        configuration.setApiKey("S98YuPCeh7lORRYpAmViIORkTzZf7WSpwYkOLhXUvZ6c110")
        eyes.setConfiguration(configuration);
        eyes.setApiKey("S98YuPCeh7lORRYpAmViIORkTzZf7WSpwYkOLhXUvZ6c110");
        eyes.setBatch(batchInfo);
    }

//    def setup(){
//        eyes.open(specificationContext.currentSpec.name,
//                specificationContext.currentIteration.name);
//    }

    def cleanup(){
        if(eyes.getIsOpen()){
            eyes.close();
        }
    }

    def cleanupSpec() {
        BatchClose batchClose = new BatchClose();
        List<String> batchIds = new ArrayList<String>();
        batchIds.add(batchInfo.getId());
        EnabledBatchClose enabledBatchClose =
                batchClose.setBatchId(batchIds);
        System.out.println("Cleaning up spec...");
        enabledBatchClose.setApiKey(eyes.getApiKey());
        System.out.println(enabledBatchClose.apiKey);
        enabledBatchClose.close();

    }

    def "the light logo should be visually perfect"(){
        setup:

        System.out.println("Env variable" + System.getenv("APPLITOOLS_API_KEY"));
        if(!(new File("./logo.png").exists())){
            throw new Exception("The logo.png file was not found");
        }
        def img = ImageIO.read(new File("./logo.png"));
        eyes.open(specificationContext.currentSpec.name,
                specificationContext.currentIteration.name,
                new RectangleSize(img.width, img.height));

        when:
        eyes.check("Light Logo", Target.image(img));
        cleanupSpec();

        then:
        eyes.close();
    }

    def "the dark logo should be visually perfect"(){
        setup:
        if(!(new File("./logo2.png").exists())){
            throw new Exception("The logo2.png file was not found");
        }
        def img = ImageIO.read(new File("./logo2.png"));
        eyes.open(specificationContext.currentSpec.name,
                specificationContext.currentIteration.name,
                new RectangleSize(img.width, img.height));

        when:
        eyes.check("Dark Logo", Target.image(img));
        //cleanupSpec()

        then:
        eyes.close();


    }
}