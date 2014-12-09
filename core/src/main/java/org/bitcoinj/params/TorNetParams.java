/*
 * Copyright 2013 Google Inc.
<<<<<<< HEAD
=======
 * Copyright 2014 Andreas Schildbach
>>>>>>> a26d7791be58c1c2205f899e177679359910228f
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bitcoinj.params;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the TorNet, an altcoin network designed for usage with the Tor network.
 */
public class TorNetParams extends NetworkParameters {

    // Our own EDCSA public key
    public static final byte[] TORCOIN_KEY = Utils.HEX.decode("04061fc65ec341842224ef2057fe82967bd2869099065c4aa5cff59de2f579fb1b0d8456031f72a69047695c482c392b7a5a25ff3f1141f02e0a666936644ac95c");

    public TorNetParams()  {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1e0fffffL);
        dumpedPrivateKeyHeader = 128;
        addressHeader = 80;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        port = 6530;
        packetMagic = 0xf9beb4d9L;
        bip32HeaderPub = 0x0488B21E; //The 4 byte header that serializes in base58 to "xpub".
        bip32HeaderPriv = 0x0488ADE4; //The 4 byte header that serializes in base58 to "xprv"

        // TorCoin specifics
        alertSigningKey = TORCOIN_KEY;

        // Genesis block information
        // calculated from https://github.com/lhartikk/GenesisH0
        // bytes: 04ffff001d01044c5d4e657720596f726b2054696d65732032382f4e6f762f3230313420452e552e205061726c69616d656e7420506173736573204d65617375726520746f20427265616b20557020476f6f676c6520696e2053796d626f6c696320566f7465
        // algorithm: SHA256
        // merkle hash: 7d384db54a917d6e8ff696fe415656d22623771cb1aad0c7a61e4b56eb48ccfd
        // pszTimestamp: New York Times 28/Nov/2014 E.U. Parliament Passes Measure to Break Up Google in Symbolic Vote
        // pubkey: 04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f
        // time: 1418142972
        // bits: 0x1d00ffff
        // nonce: ??
        // hash: ??

        genesisBlock.setDifficultyTarget(0x1d00ffff);
        genesisBlock.setTime(1418142972);
        genesisBlock.setNonce(808716);
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 210000;
        spendableCoinbaseDepth = 100;
        String genesisHash = genesisBlock.getHashAsString();
        System.out.println(genesisBlock.toString());
        System.out.println(genesisHash);
        checkState(genesisHash.equals("0x00000b4c01503b3b9ff5b495f64a98c24904f5adeec4e9968a7df7e83a32e054"),
                genesisHash);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        // checkpoints.put(91722, new Sha256Hash("00000000000271a2dc26e7667f8419f2e15416dc6955e5a6c6cdf3f2574dd08e"));

        dnsSeeds = new String[] {
                "torcoin.benjaminchrobot.com"          // Benjamin Chrobot
        };
    }

    private static TorNetParams instance;
    public static synchronized TorNetParams get() {
        if (instance == null) {
            instance = new TorNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
