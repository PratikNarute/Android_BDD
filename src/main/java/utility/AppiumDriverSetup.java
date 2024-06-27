package utility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;

public class AppiumDriverSetup {


    private static AppiumDriverLocalService service;
    public static AndroidDriver androidDriver;
    public static IOSDriver IOSdriver;
    public static UiAutomator2Options uiAutomator2Options;
    public static XCUITestOptions xcuitestOptions;

    public static void startAppiumServer() {
        try {
            AppiumServiceBuilder builder = new AppiumServiceBuilder();
            File nodeFile = null;

            String os = System.getProperty("os.name").toLowerCase();
            System.out.println("OS: " + os);
            if (os.contains("mac")) {
                nodeFile = new File(findNodePathInMac());
                System.out.println("NodePath: " + findNodePathInMac());
            } else if (os.contains("win")) {
                nodeFile = new File(findNodePathInWindow());
                System.out.println("NodePath: " + findNodePathInWindow());
            }

            if (!nodeFile.exists()) {
//                throw new IllegalStateException("Node executable not found at: " + nodeFile.getPath());
                Assert.fail("Node executable not found at: " + nodeFile.getPath());
            }
            builder.usingDriverExecutable(nodeFile);

            // Set the path to the Appium main.js
//            File appiumJSFile = new File("/usr/local/lib/node_modules/appium/build/lib/main.js");
            File appiumJSFile = new File(findAppiumPath());
            System.out.println("AppiumJsPath: " + findAppiumPath());
            if (!appiumJSFile.exists()) {
                Assert.fail("Appium main.js not found at: " + appiumJSFile.getPath());
            }
            builder.withAppiumJS(appiumJSFile);


            // Specify other uiAutomator2Options
            builder.withIPAddress("127.0.0.1");
            builder.usingPort(4723);
            builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            builder.withArgument(GeneralServerFlag.LOG_LEVEL, "info");

            // Build and start the service
            service = AppiumDriverLocalService.buildService(builder);
            service.start();

        } catch (Exception e) {
            System.out.println("Exception while starting Appium server: " + e.getMessage());
        }
    }

    public static String stopAppiumLocalServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            return "Running Appium Local server stopped: ";
        } else {
            return "Currently Appium Local server not running: ";
        }
    }

    public static void lunchAndroidDriver(String appPackageName, String country) throws IOException {

        if (ReadProperty.getPropertiesData("Platform_Execution").equalsIgnoreCase("Local")) {
            // Set capabilities using UiAutomator2uiAutomator2Options for local test execution
            uiAutomator2Options = new UiAutomator2Options();
            uiAutomator2Options.setDeviceName(getDeviceName());
//    	uiAutomator2Options.setApp(appPath);
            uiAutomator2Options.setAutomationName("UiAutomator2");
            uiAutomator2Options.setAppPackage(appPackageName);
            uiAutomator2Options.setAppActivity("com.lycamobile.MainActivity");
            uiAutomator2Options.setCapability("autoDismissAlerts", true);
//		uiAutomator2Options.setNoReset(true);
//		uiAutomator2Options.setFullReset(true);
            uiAutomator2Options.autoGrantPermissions();
            uiAutomator2Options.setUiautomator2ServerInstallTimeout(Duration.ofMillis(60000));
            uiAutomator2Options.setAndroidInstallTimeout(Duration.ofMillis(10000));
            uiAutomator2Options.setAdbExecTimeout(Duration.ofSeconds(20));
            androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), uiAutomator2Options);
        } else if (ReadProperty.getPropertiesData("Platform_Execution").equalsIgnoreCase("Lambda_Cloud")) {
            // Set capabilities using UiAutomator2uiAutomator2Options for lambda cloud test execution
            uiAutomator2Options = new UiAutomator2Options();
            uiAutomator2Options.setPlatformName("ANDROID");
            uiAutomator2Options.setDeviceName("Galaxy .*");
            uiAutomator2Options.setPlatformVersion("13");
            uiAutomator2Options.setCapability("build", "App: " + appPackageName);
            uiAutomator2Options.setCapability("name", "Android Test");
            uiAutomator2Options.setCapability("isRealMobile", true);
            uiAutomator2Options.setCapability("app", getAppID(country));
            uiAutomator2Options.setCapability("devicelog", true);
            uiAutomator2Options.setCapability("autoGrantPermissions", true);
            uiAutomator2Options.setCapability("network", false);
            uiAutomator2Options.setCapability("video", true);
            uiAutomator2Options.setCapability("visual", true);

            String gridURL = "https://" + "pratik.narute" + ":" + "fOIiegGtqWxn5suxSquY8PaYEUYpnvbSb8ZX4agqNWng9TAkNu" + "@" + "mobile-hub.lambdatest.com" + "/wd/hub";
//      String gridURL = "https://krushna.jenaqualitrix:ZyDVxJ8RZPug8JkmTnWaMLcLf1LPTMxkWzwX6nIWWPbgfrQyjT@mobile-hub.lambdatest.com/wd/hub";

            // Initialize the AndroidDriver
            androidDriver = new AndroidDriver(new URL(gridURL), uiAutomator2Options);
        }
    }

    public static void lunchIOSDriver(String appPackageName, String country) throws IOException {

        if (ReadProperty.getPropertiesData("Platform_Execution").equalsIgnoreCase("Local")) {
            xcuitestOptions = new XCUITestOptions();
            xcuitestOptions.setPlatformName("iOS");
            xcuitestOptions.setDeviceName("iPhone 14");
            xcuitestOptions.setAutomationName("XCUITest");
            xcuitestOptions.setBundleId("com.example.myapp");
            xcuitestOptions.setUdid("your-device-udid");


            // Initialize XCUITestDriver
            IOSdriver = new IOSDriver(new URL("http://localhost:4723/wd/hub"), xcuitestOptions);

        } else if (ReadProperty.getPropertiesData("Platform_Execution").equalsIgnoreCase("Lambda_Cloud")) {
            xcuitestOptions = new XCUITestOptions();
            xcuitestOptions.setPlatformName("ios");
            xcuitestOptions.setDeviceName("iPhone.*");
            xcuitestOptions.setPlatformVersion("16");
            xcuitestOptions.setAutomationName("XCUITest");
            xcuitestOptions.setCapability("build", "App: " + appPackageName);
            xcuitestOptions.setCapability("name", "IOS Test");
            xcuitestOptions.setCapability("isRealMobile", true);
            xcuitestOptions.setCapability("app", "lt://APP10160352241719233528625235");
            xcuitestOptions.setCapability("devicelog", true);
            xcuitestOptions.setCapability("autoGrantPermissions", true);
            xcuitestOptions.setCapability("network", false);
            xcuitestOptions.setCapability("video", true);
            xcuitestOptions.setCapability("visual", true);



            String gridURL = "https://" + "pratik.narute" + ":" + "fOIiegGtqWxn5suxSquY8PaYEUYpnvbSb8ZX4agqNWng9TAkNu" + "@" + "mobile-hub.lambdatest.com" + "/wd/hub";
//      String gridURL = "https://krushna.jenaqualitrix:ZyDVxJ8RZPug8JkmTnWaMLcLf1LPTMxkWzwX6nIWWPbgfrQyjT@mobile-hub.lambdatest.com/wd/hub";

            // Initialize XCUITestDriver
            IOSdriver = new IOSDriver(new URL(gridURL), xcuitestOptions);
        }
    }

    public static String getAppID(String country) throws IOException {
        String APP_ID = "";
        if (country.equalsIgnoreCase("AT")) {
            APP_ID = ReadProperty.getPropertiesData("APP_ID_AT");
        } else if (country.equalsIgnoreCase("UK")) {
            APP_ID = ReadProperty.getPropertiesData("APP_ID_UK");
        }
        return APP_ID;
    }

    public static String getDeviceName() {
        String deviceName = getConnectedDeviceUDIDUsing_adb();
        if (deviceName == null || deviceName.isEmpty()) {
            deviceName = "Cloud Device"; // Fallback to a default name
        }
        System.out.println("getDeviceName: " + deviceName);
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

            if (os.contains("mac")) {
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
            } else if (os.contains("win")) {
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
