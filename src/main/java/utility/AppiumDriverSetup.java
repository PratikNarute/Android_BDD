package utility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppiumDriverSetup {


    private static AppiumDriverLocalService service;
    public static AndroidDriver driver;
    public static UiAutomator2Options options;
    public static String platformExecution= "Lambda Cloud";

    public static void startAppiumServer() {
        try {
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            File nodeFile = null;

            String os = System.getProperty("os.name").toLowerCase();
            System.out.println("OS: "+os);
            if(os.contains("mac")){
                nodeFile = new File(findNodePathInMac());
                System.out.println("NodePath: "+findNodePathInMac());
            }else if(os.contains("win")){
                nodeFile = new File(findNodePathInWindow());
                System.out.println("NodePath: "+findNodePathInWindow());
            }

            if (!nodeFile.exists()) {
//                throw new IllegalStateException("Node executable not found at: " + nodeFile.getPath());
                Assert.fail("Node executable not found at: " + nodeFile.getPath());
            }
            builder.usingDriverExecutable(nodeFile);

            // Set the path to the Appium main.js
//            File appiumJSFile = new File("/usr/local/lib/node_modules/appium/build/lib/main.js");
            File appiumJSFile = new File(findAppiumPath());
            System.out.println("AppiumJsPath: "+findAppiumPath());
            if (!appiumJSFile.exists()) {
                Assert.fail("Appium main.js not found at: " + appiumJSFile.getPath());
            }
            builder.withAppiumJS(appiumJSFile);



            // Specify other options
            builder.withIPAddress("127.0.0.1");
            builder.usingPort(4723);
            builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            builder.withArgument(GeneralServerFlag.LOG_LEVEL, "info");

            // Build and start the service
            service = AppiumDriverLocalService.buildService(builder);
            service.start();

        } catch (Exception e) {
            System.out.println("Exception while starting Appium server: "+e.getMessage());
        }
    }

    public static String stopAppiumLocalServer() {
        if (service != null && service.isRunning()) {
             service.stop();
            return "Running Appium Local server stopped: ";
        }else{
            return "Currently Appium Local server not running: ";
        }
    }

    public static void lunchAndroidDriver(String appPackageName) throws MalformedURLException {
        if(platformExecution.equalsIgnoreCase("Local")){
            // Set capabilities using UiAutomator2Options for local test execution
            options = new UiAutomator2Options();
            options.setDeviceName(getDeviceName());
//    	options.setApp(appPath);
            options.setAutomationName("UiAutomator2");
            options.setAppPackage(appPackageName);
            options.setAppActivity("com.lycamobile.MainActivity");
            options.setCapability("autoDismissAlerts", true);
//		options.setNoReset(true);
//		options.setFullReset(true);
            options.autoGrantPermissions();
            options.setUiautomator2ServerInstallTimeout(Duration.ofMillis(60000));
            options.setAndroidInstallTimeout(Duration.ofMillis(10000));
            options.setAdbExecTimeout(Duration.ofSeconds(20));
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"),options);
        }
        else{
            // Set capabilities using UiAutomator2Options for lambda cloud test execution
            options = new UiAutomator2Options();
            options.setPlatformName("ANDROID");
            options.setDeviceName("Galaxy .*");
            options.setPlatformVersion("13");
            options.setCapability("build", "LT-appium-java-cucumber");
            options.setCapability("name", "Android Test");
            options.setCapability("isRealMobile", true);
            options.setCapability("app", "lt://APP1016034991715000332382475");
            options.setCapability("devicelog", true);
            options.setCapability("autoGrantPermissions", true);
            options.setCapability("network", false);
            options.setCapability("video", true);
            options.setCapability("visual", true);

            String gridURL = "https://" + "krushna.jenaqualitrix" + ":" + "ZyDVxJ8RZPug8JkmTnWaMLcLf1LPTMxkWzwX6nIWWPbgfrQyjT" + "@" + "mobile-hub.lambdatest.com" + "/wd/hub";
//      String gridURL = "https://krushna.jenaqualitrix:ZyDVxJ8RZPug8JkmTnWaMLcLf1LPTMxkWzwX6nIWWPbgfrQyjT@mobile-hub.lambdatest.com/wd/hub";

            // Initialize the AndroidDriver
            driver = new AndroidDriver(new URL(gridURL), options);
        }
    }

    public static String getDeviceName(){
        String deviceName = getConnectedDeviceUDIDUsing_adb();
        if (deviceName == null || deviceName.isEmpty()) {
            deviceName = "Cloud Device"; // Fallback to a default name
        }
        System.out.println("getDeviceName: "+deviceName);
        return deviceName;
    }

    public static String getConnectedDeviceUDIDUsing_adb() {
        String udid = null;
        try {
            // Execute adb command to get list of connected devices
            Process process = Runtime.getRuntime().exec("adb devices");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            // Skip the first line (header) of adb devices output
            reader.readLine();

            // Iterate through each subsequent line of the command output
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    udid = line.split("\\s+")[0].trim(); // Extract the device ID (first column)
                    break;
                }
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return udid;
    }

    public static String findNodePathInMac() {
        String nodePath = null;
        try {
            // Execute adb command to run 'which node' on the development machine
            Process process = Runtime.getRuntime().exec("which node");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            // Read the command output
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Check for any errors
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorOutput.append(errorLine).append("\n");
            }

            // Wait for the command to complete
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                // Successful execution, extract node path
                nodePath = output.toString().trim();
            } else {
                // Command failed, print error output
                System.err.println("Command failed with error: " + errorOutput.toString());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return nodePath;
    }
    public static String findNodePathInWindow() {
        String nodePath = null;
        try {
            // Execute command to run 'where node' in Windows Command Prompt
            Process process = Runtime.getRuntime().exec("cmd /c where node");

            // Read the command output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            // Wait for the command to complete
            process.waitFor();

            // Check if the process completed successfully
            int exitCode = process.exitValue();
            if (exitCode == 0) {
                nodePath = output.toString().trim();
            } else {
                System.err.println("Command returned non-zero exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return nodePath;
    }
    public static String findAppiumPath() {
        String appiumPath = null;
        String os = System.getProperty("os.name").toLowerCase();
        Process npmProcess = null;


        try {

            if(os.contains("mac")){
                // Execute command to run 'npm root -g' to get global npm modules path
                npmProcess = Runtime.getRuntime().exec("npm root -g");
                // Read the npm root output
                BufferedReader npmReader = new BufferedReader(new InputStreamReader(npmProcess.getInputStream()));
                String npmRoot;
                while ((npmRoot = npmReader.readLine()) != null) {
                    npmRoot = npmRoot.trim();
                    break; // Read only the first line (npm root -g should return only one line)
                }

                // Wait for the npm root command to complete
                npmProcess.waitFor();

                // Check if the npm root command completed successfully
                int npmExitCode = npmProcess.exitValue();
                if (npmExitCode == 0) {
                    // Construct the full path to the Appium main.js file
                    appiumPath = npmRoot + "/appium/build/lib/main.js";
                } else {
                    System.err.println("npm root -g command returned non-zero exit code: " + npmExitCode);
                }
            }else if(os.contains("win")){
                String jsPaths = null;
                if (os.contains("Win")) {
                    String whereAppium = "where" + " " + "appium";
                    Process p = Runtime.getRuntime().exec(whereAppium);
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

                    while ((jsPaths = stdInput.readLine()) != null) {
                        appiumPath = jsPaths.replace("appium", "node_modules\\appium\\build\\lib\\main.js");
                        break;
                    }
                    p.waitFor();
                    p.destroy();

                    if (appiumPath == null) {
                        System.exit(0);
                    }
                } else {
                    appiumPath = "//usr//local//lib//node_modules//appium//build//lib//main.js";
                }


                return appiumPath;
            }



        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return appiumPath;
    }
    public static String getAppiumJSPath() throws IOException, Exception {

        String jsPaths = null;
        String actualJSPath = null;
        String operatingSystem = System.getProperty("os.name");

        if (operatingSystem.contains("Win")) {
            String whereAppium = "where" + " " + "appium";
            Process p = Runtime.getRuntime().exec(whereAppium);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((jsPaths = stdInput.readLine()) != null) {
                actualJSPath = jsPaths.replace("appium", "node_modules\\appium\\build\\lib\\main.js");
                break;
            }
            p.waitFor();
            p.destroy();

            if (actualJSPath == null) {
                System.exit(0);
            }
        } else {
            actualJSPath = "//usr//local//lib//node_modules//appium//build//lib//main.js";
        }

        System.out.println("AppiumJSPath: " + actualJSPath);
        return actualJSPath;
    }

}
