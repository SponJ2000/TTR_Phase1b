package dao;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlugInManager {
    private static IDaoFactory factory;

    public static IDaoFactory getFactory() {
        return factory;
    }
    // call this to dynamically load jar files
    public static void main(String[] args) throws Exception {
        if(args.length != 3) {
            printUsage();
        } else {
            PlugInManager plugInManager = new PlugInManager();
            plugInManager.start(args[0], args[1], args[2]);
        }
    }

    public static void printUsage() {
        System.out.println("Usage: java Application pluginDirectory pluginJarFileName pluginClassName");
    }

    private void start(String pluginDirectory, String pluginJarName, String pluginClassName) throws Exception {
        IDaoFactory daoFactory = getDaoFactoryinInstance(pluginDirectory, pluginJarName, pluginClassName);
        factory = daoFactory;
        DAOFacade.getInstance().setDaoFactory(daoFactory);
//        System.out.println(messagePlugin.getMessage());
    }

    private IDaoFactory getDaoFactoryinInstance(String pluginDirectory, String pluginJarName, String pluginClassName) throws Exception {
        // Get a class loader and set it up to load the jar file
        File pluginJarFile = new File(pluginDirectory, pluginJarName);
        URL pluginURL = pluginJarFile.toURI().toURL();
        URLClassLoader loader = new URLClassLoader(new URL[]{pluginURL});

        // Load the jar file's plugin class, create and return an instance
        Class<? extends IDaoFactory> messagePluginClass = (Class<IDaoFactory>) loader.loadClass(pluginClassName);
        return messagePluginClass.getDeclaredConstructor(null).newInstance();
    }
}
