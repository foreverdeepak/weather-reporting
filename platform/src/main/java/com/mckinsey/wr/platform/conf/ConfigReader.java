package com.mckinsey.wr.platform.conf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by deepakc on 10/04/17.
 */
public final class ConfigReader {

    private static transient Config config;
    private static Map<String, Object> configs = new HashMap<>();

    private static final String DEFAULT_CONFIG = "app-config.yaml";

    public static Config readConfig() throws IOException {
        if (config == null) {
            config = readConfig(DEFAULT_CONFIG, ConfigImpl.class);
        }
        return config;
    }

    public static <T> T  readConfig(Class<T> type) throws IOException {
        return readConfig(DEFAULT_CONFIG, type);
    }

    public static <T> T readConfig(String artifact, Class<T> type) throws IOException {
        @SuppressWarnings("unchecked")
        T conf = (T) configs.get(artifact);
        if (conf == null) {
            InputStream stream = inputStream(artifact);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            conf = mapper.readValue(stream, type);
        }
        return conf;
    }

    private static InputStream inputStream(String artifact) throws IOException {
        File yaml = getConfigFile("wr.home.dir", artifact);

        if (yaml == null || !yaml.exists()) {
            yaml = getConfigFile("WR_HOME", artifact);
        }

        if (yaml == null || !yaml.exists()) {
            yaml = getConfigFile("user.home", artifact);
        }

        if (yaml != null && yaml.exists()) {
            return new FileInputStream(yaml);
        }

        return ConfigReader.class.getClassLoader().getResourceAsStream(artifact);
    }

    private static File getConfigFile(String systemProperty, String artifactName) throws IOException {
        String applicationPath = System.getenv(systemProperty);
        if (applicationPath == null) {
            applicationPath = System.getProperty(systemProperty);
        }

        File artifact = null;
        if (applicationPath != null) {
            artifact = new File(applicationPath + File.separator + "etc" + File.separator + artifactName);
            if (!artifact.exists()) {
                artifact = new File(applicationPath + File.separator + "config" + File.separator + artifactName);
            }
        }

        if (artifact != null && artifact.exists()) {
            return artifact;
        }
        return null;
    }
}
