package torcointopo;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by bchrobot on 12/12/14.
 */
public class OnionRelay {
    private final SimpleStringProperty hostname;
    private final SimpleStringProperty ipAddress;
    private final SimpleStringProperty bandwidth;
    private final SimpleStringProperty relayType;
    private final SimpleStringProperty trustRating;

    public OnionRelay(String hostname, String ipAddress, String bandwidth, String relayType, String trustRating) {
        this.hostname = new SimpleStringProperty(hostname);
        this.ipAddress = new SimpleStringProperty(ipAddress);
        this.bandwidth = new SimpleStringProperty(bandwidth);
        this.relayType = new SimpleStringProperty(relayType);
        this.trustRating = new SimpleStringProperty(trustRating);
    }

    public String getHostname() {
        return hostname.get();
    }

    public SimpleStringProperty hostnameProperty() {
        return hostname;
    }

    public String getIpAddress() {
        return ipAddress.get();
    }

    public SimpleStringProperty ipAddressProperty() {
        return ipAddress;
    }

    public String getBandwidth() {
        return bandwidth.get();
    }

    public SimpleStringProperty bandwidthProperty() {
        return bandwidth;
    }

    public String getRelayType() {
        return relayType.get();
    }

    public SimpleStringProperty relayTypeProperty() {
        return relayType;
    }

    public String getTrustRating() {
        return trustRating.get();
    }

    public SimpleStringProperty trustRatingProperty() {
        return trustRating;
    }
}
