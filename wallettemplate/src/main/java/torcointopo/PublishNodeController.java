package torcointopo;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.bitcoinj.core.*;
import org.spongycastle.crypto.params.KeyParameter;
import torcointopo.controls.BitcoinAddressValidator;
import torcointopo.utils.BitcoinUIModel;
import torcointopo.utils.TextFieldValidator;
import torcointopo.utils.WTUtils;

import static com.google.common.base.Preconditions.checkState;
import static torcointopo.utils.GuiUtils.*;

public class PublishNodeController {
    public TextField hostnameEdit;
    public TextField ipAddressEdit;
    public TextField bandwidthEdit;
    public ToggleButton isExitNode;

    public Main.OverlayUI overlayUI;

    // Called by FXMLLoader
    public void initialize() {
        // TODO
    }

    public void cancel(ActionEvent event) {
        overlayUI.done();
    }

    public void publishNode(ActionEvent event) {
        BitcoinUIModel.instance.getOnionRelays().add(new OnionRelay(
                hostnameEdit.getText(),
                ipAddressEdit.getText(),
                bandwidthEdit.getText(),
                (isExitNode.selectedProperty().getValue() ? "exit" : "relay"),
                "0"
        ));
        checkGuiThread();
        overlayUI.done();
    }
}
