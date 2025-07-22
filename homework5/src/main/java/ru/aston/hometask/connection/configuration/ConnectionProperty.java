package ru.aston.hometask.connection.configuration;

public class ConnectionProperty {
    private final String url;
    private final String username;
    private final String password;
    private final String driver;

    private ConnectionProperty(String url, String username, String password, String driver) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    public static ConnectionPropertyBuilder builder() {
        return new ConnectionPropertyBuilder();
    }

    public static class ConnectionPropertyBuilder {
        private String url;
        private String username;
        private String password;
        private String driver;

        private ConnectionPropertyBuilder() {}

        public ConnectionPropertyBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ConnectionPropertyBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public ConnectionPropertyBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public ConnectionPropertyBuilder setDriver(String driver) {
            this.driver = driver;
            return this;
        }

        public ConnectionProperty build() {
            return new ConnectionProperty(url, username, password, driver);
        }
    }
}
