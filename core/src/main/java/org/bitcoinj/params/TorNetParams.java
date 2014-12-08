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
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the TorNet, an altcoin network designed for usage with the Tor network.
 */
public class TorNetParams extends NetworkParameters {
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

        genesisBlock.setDifficultyTarget(0x1e0fffffL);
        genesisBlock.setTime(1417976555L);
        genesisBlock.setNonce(808716);
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 210000;
        spendableCoinbaseDepth = 100;
        String genesisHash = genesisBlock.getHashAsString();
        System.out.println(genesisBlock.toString());
        System.out.println(genesisHash);
        checkState(genesisHash.equals("0x00000b4c01503b3b9ff5b495f64a98c24904f5adeec4e9968a7df7e83a32e054"),
                genesisHash);

        dnsSeeds = new String[] {
                "benjaminchrobot.com"          // Benjamin Chrobot
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
