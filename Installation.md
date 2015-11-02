# Installation #

The installation is simple. Just download the Eclipse Spykit Plugin Jar from the Downloads and place it into your installation\_dir\eclipse\dropins directory.

Next decide whether you want to spy on your base Eclipse installation (use the -debug command line option) or whether you want to spy on your Run-time Workbench (use the Tracing page of its launch configuration).

# Spy over Workbench #
  * Start Eclipse with -debug option
  * This will look for a .option file in the installation\_dir\eclipse folder
  * You can also specify the path of the .option file after - debug
P.S .options file is available for download from the Download Section. It is available in the misc folder if you download the Src.

# Spy over Runtime Workbench #
  * Open the Runtime Configuration Dialog
  * Select the Tracing Tab
  * Enable Debug Mode
  * Check if the org.eclipse.osgi plugin is enabled for Debug on Left Hand Side.
  * Select the Plugin -> on Right Hand Side, tracing/activation should be enabled, tracing/class\_monitor should be enabled.
P.S You can also run the Runtime Workbench using the -debug option as mentioned above. Please provide these arguments in the Program arguments.