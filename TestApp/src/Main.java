import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    static Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        Properties userProps = null;
        try {
            userProps = Helper.loadProperties();

            logger.setLevel(Level.INFO);

            logger.info("info level log\n");

            logger.log(Level.INFO, "My first log message.");
            logger.log(Level.INFO, "Another message'");

        } catch (Exception ex){
            System.out.println("Exeception - " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
        } finally {
            Helper.cleanUp(userProps);
        }
    }




    private static void doTryCatchFinally() {
        int length;
        char[] charBuff = new char[8];

        try (Reader reader = Helper.openReader("file1.txt");
             Writer writer = Helper.openWriter("file2.txt")){

            while ((length = reader.read(charBuff)) >= 0) {
                System.out.println(String.format("\nlength: %s", length));
                writer.write(charBuff, 0, length);
            }
        } catch (IOException ioe) {
        }
    }
}
