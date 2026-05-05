package secureauth.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gestiona configuracion externa desde application.properties.
 *
 * @author Diego-Jimenez1999
 */
public final class ApplicationConfig {

    private final Properties properties;

    private ApplicationConfig(final Properties properties) {
        this.properties = properties;
    }

    public static ApplicationConfig fromProperties(final Properties properties) {
        return new ApplicationConfig(properties);
    }

    public static ApplicationConfig load() {
        final Properties props = new Properties();
        try (InputStream in = ApplicationConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in == null) {
                throw new IllegalStateException("No se encontro application.properties");
            }
            props.load(in);
            final String profile = resolveProfile();
            if (!"dev".equals(profile)) {
                final String profileFile = "application-" + profile + ".properties";
                try (InputStream profileIn = ApplicationConfig.class.getClassLoader().getResourceAsStream(profileFile)) {
                    if (profileIn == null) {
                        throw new IllegalStateException("No se encontro " + profileFile);
                    }
                    props.load(profileIn);
                }
            }
            return new ApplicationConfig(props);
        } catch (IOException ex) {
            throw new IllegalStateException("Error leyendo configuracion", ex);
        }
    }

    public String get(final String key) {
        final String envValue = readEnvOverride(key);
        return envValue != null ? envValue : properties.getProperty(key);
    }

    public int getInt(final String key, final int defaultValue) {
        final String value = get(key);
        return value == null ? defaultValue : Integer.parseInt(value);
    }

    private static String resolveProfile() {
        final String fromSystem = System.getProperty("app.profile");
        if (fromSystem != null && !fromSystem.isBlank()) {
            return fromSystem.trim().toLowerCase();
        }
        final String fromEnv = System.getenv("APP_PROFILE");
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv.trim().toLowerCase();
        }
        return "dev";
    }

    private static String readEnvOverride(final String key) {
        final String envKey = key.toUpperCase().replace('.', '_');
        final String value = System.getenv(envKey);
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
