import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Helper {


    public static Reader openReader(String filepath) {
        Reader reader = null;
        try {
            reader = new FileReader(filepath);
        } catch (FileNotFoundException ex) {

        }
        return reader;
    }

    public static Writer openWriter(String filepath) {
        Writer writer = null;

        try {
            writer = new FileWriter(filepath);
        } catch (IOException ex) {

        }
        return writer;
    }


    public static Properties loadProperties() {
        Properties userProps = null;
        try {
            //create default properties
            Properties defaultProps  = new Properties();

            //load default values from DefaultValues.xml
            try (InputStream inputStream = Main.class.getResourceAsStream("DefaultValues.xml")) {
                defaultProps.loadFromXML(inputStream);
            }

            //create userProps based off defaultProps
            userProps = new Properties(defaultProps);
            loadUserProps(userProps);
        } catch (Exception ex) {
            System.out.println("Exeception - " + ex.getClass().getSimpleName() + ": " + ex.getMessage());

        }
        return userProps;
    }

    private static Properties loadUserProps(Properties userProps) throws IOException {
        //get the path of the user file
        Path userFile = Paths.get("userValues.xml");

        //check if user file exists
        if (Files.exists(userFile)) {
            //if so, load the xml file via input stream
            try (InputStream inputStream = Files.newInputStream(userFile)) {
                //and load user props from the input stream
                userProps.loadFromXML(inputStream);
            }
        }

        //else return passed user props if file path doesn't exist
        return userProps;
    }


    public static void cleanUp(Properties userProps) {
        if (userProps != null) {
            try {
                String currentRun = userProps.getProperty("runNumber");

                System.out.println(String.format("Run #%s", currentRun));

                int currentRunInt = Integer.parseInt(currentRun);

                userProps.setProperty("runNumber", String.valueOf(++currentRunInt));

                saveUserProps(userProps);
            } catch (IOException ex) {
                System.out.println("Exeception - " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
        } else {
            System.out.println("Could not find the user properties.");
        }

    }

    private static void saveUserProps(Properties userProps) throws IOException {
        //get the path of the user file
        Path userFile = Paths.get("userValues.xml");

        try (OutputStream outputStream = Files.newOutputStream(userFile)) {
            userProps.storeToXML(outputStream, "Test");
        }

    }

}
