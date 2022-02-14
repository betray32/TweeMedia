package cl.twitter.tweemedia.infrastructure.spring;

import cl.twitter.tweemedia.utils.PropDefinitions;
import cl.twitter.tweemedia.management.TwitterController;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class AutomaticLauncher {

    private TwitterController twitter;
    private PropDefinitions prop;
    private ConfigurableApplicationContext context;

    private void initProgram() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--------------------------");
        System.out.println("Initializing...");
        System.out.println("Welcome to TweeMedia!");

        System.out.println("Choose your preference");
        System.out.println("1. Configure with file (application.properties)");
        System.out.println("2. Keyboard input");

        int selectedOption = scanner.nextInt();

        System.out.println("--------------------------");

        switch (selectedOption) {
            case 1:
                System.out.println("Initializing with Properties File");
                System.out.println("Showing Sets");
                System.out.println("--------------------------");
                System.out.println("Profile ID = " + prop.profile);
                System.out.println("Download Folder = " + prop.directory);
                System.out.println("Records = " + prop.registryCount);
                System.out.println("Photos ? (1 for yes, 2 for no) = " + prop.photos);
                System.out.println("Videos ? (1 for yes, 2 for no) = " + prop.videos);
                System.out.println("--------------------------");

                twitter.saveMedia(prop.photos, prop.videos, prop.profile, prop.directory, prop.registryCount);
                break;

            case 2:
                System.out.println("Please, input your options");

                System.out.println("--------------------------");
                System.out.println("Twitter ID");
                String sProfile = scanner.next();

                System.out.println("--------------------------");
                System.out.println("Download Folder");
                String sDirectory = scanner.next();

                System.out.println("--------------------------");
                System.out.println("How Many Records?");
                String sRegistryCount = scanner.next();

                System.out.println("--------------------------");
                System.out.println("Include Photos? (1 for yes, 2 for no)");
                String sPhotos = scanner.next();

                System.out.println("--------------------------");
                System.out.println("Include Videos? (1 for yes, 2 for no)");
                String sVideos = scanner.next();

                scanner.close();
                twitter.saveMedia(Integer.parseInt(sPhotos), Integer.parseInt(sVideos), sProfile, sDirectory, Integer.parseInt(sRegistryCount));
                break;
            default:
                System.err.println("Incorrect Option");
                break;
        }
        System.out.println("Finished");
    }
}
