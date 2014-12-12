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
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptOpCodes;
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

    private Wallet.SendResult sendResult;
    private KeyParameter aesKey;

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

        try {
            Transaction dataTx = new Transaction(Main.params);
            dataTx.addOutput(Coin.valueOf(1000), new ScriptBuilder().data("MESSAGE".getBytes()).op(ScriptOpCodes.OP_DROP).data("c1418dabf7c39683492176845cc276b2fe232450".getBytes()).op(ScriptOpCodes.OP_CHECKSIG).build());
            Wallet.SendRequest req = Wallet.SendRequest.forTx(dataTx);
            sendResult = Main.bitcoin.wallet().sendCoins(req);
            Futures.addCallback(sendResult.broadcastComplete, new FutureCallback<Transaction>() {
                @Override
                public void onSuccess(Transaction result) {
                    checkGuiThread();
                    overlayUI.done();
                }

                @Override
                public void onFailure(Throwable t) {
                    // We died trying to empty the wallet.
                    crashAlert(t);
                }
            });
            sendResult.tx.getConfidence().addEventListener((tx, reason) -> {
                if (reason == TransactionConfidence.Listener.ChangeReason.SEEN_PEERS) {
                    final int peers = sendResult.tx.getConfidence().numBroadcastPeers();
                    System.out.println(String.format("Broadcasting ... seen by %d peers", peers));
                }
            });
        } catch (InsufficientMoneyException e) {
            informationalAlert("Could not empty the wallet",
                    "You may have too little money left in the wallet to make a transaction.");
            overlayUI.done();
        } catch (ECKey.KeyIsEncryptedException e) {
//            askForPasswordAndRetry();
        }
    }

    private void askForPasswordAndRetry() {
        Main.OverlayUI<WalletPasswordController> pwd = Main.instance.overlayUI("wallet_password.fxml");
//        final String addressStr = address.getText();
        pwd.controller.aesKeyProperty().addListener((observable, old, cur) -> {
            // We only get here if the user found the right password. If they don't or they cancel, we end up back on
            // the main UI screen. By now the send money screen is history so we must recreate it.
            checkGuiThread();
            Main.OverlayUI<PublishNodeController> screen = Main.instance.overlayUI("publish_node.fxml");
            screen.controller.aesKey = cur;
//            screen.controller.address.setText(addressStr);
            screen.controller.publishNode(null);
        });
    }

//    private void updateTitleForBroadcast() {
//        final int peers = sendResult.tx.getConfidence().numBroadcastPeers();
//        titleLabel.setText(String.format("Broadcasting ... seen by %d peers", peers));
//    }
}
