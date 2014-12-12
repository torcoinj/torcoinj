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
        super.setTorBlock(this);
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1e03ffff);
        dumpedPrivateKeyHeader = 128;
        addressHeader = 80;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        port = 5530;
//        packetMagic = 0xf9beb4d9L;
        packetMagic = 0xb1c54f2cL;
        bip32HeaderPub = 0x0488B21E; //The 4 byte header that serializes in base58 to "xpub".
        bip32HeaderPriv = 0x0488ADE4; //The 4 byte header that serializes in base58 to "xprv"

        // TorCoin specifics
        alertSigningKey = TORCOIN_KEY;

        // Genesis block information
        /*
        0000010497296153a3a1802e5b8363d2a115a3442bad7d92a50942b1e4f41f92
        9f16973b8f172c5338c9b12b94ace162bcef730ae098d4ac04cdfefb44f5e44e
        1e03ffff
        CBlock(hash=0000010497296153a3a1802e5b8363d2a115a3442bad7d92a50942b1e4f41f92, ver=1, hashPrevBlock=0000000000000000000000000000000000000000000000000000000000000000, hashMerkleRoot=9f16973b8f172c5338c9b12b94ace162bcef730ae098d4ac04cdfefb44f5e44e,
                nTime=1417976555, nBits=1e03ffff, nNonce=2508269, vtx=1)
          CTransaction(hash=9f16973b8f, ver=1, vin.size=1, vout.size=1, nLockTime=0)
            CTxIn(COutPoint(0000000000, 4294967295), coinbase 04ffff001d0104194d61792074686520666f726365206265207769746820796f75)
            CTxOut(nValue=50.00000000, scriptPubKey=04678afdb0fe5548271967f1a67130)
          vMerkleTree: 9f16973b8f172c5338c9b12b94ace162bcef730ae098d4ac04cdfefb44f5e44e
         */

        genesisBlock.setDifficultyTarget(0x1e03ffff);
        genesisBlock.setTime(1417976555);
        genesisBlock.setNonce(2508269);

        id = ID_TORMAINNET;
        subsidyDecreaseBlockCount = 210000;
        spendableCoinbaseDepth = 100;
        String genesisHash = genesisBlock.getHashAsString();
        System.out.println(genesisBlock.toString());
        System.out.println(genesisHash);
        System.out.println("0000010497296153a3a1802e5b8363d2a115a3442bad7d92a50942b1e4f41f92");
        checkState(genesisHash.equals("0000010497296153a3a1802e5b8363d2a115a3442bad7d92a50942b1e4f41f92"), genesisHash);

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
