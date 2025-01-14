/**
 * bdk-jvm 1.0.0-beta.6
 *
 * Query an Esplora server for the total balance of a wallet using descriptors.
 */

@file:DependsOn("org.bitcoindevkit:bdk-jvm:1.0.0-beta.6")

import org.bitcoindevkit.Descriptor
import org.bitcoindevkit.Wallet
import org.bitcoindevkit.EsploraClient
import org.bitcoindevkit.Connection
import org.bitcoindevkit.FullScanRequest
import org.rustbitcoin.bitcoin.Network

val SIGNET_ESPLORA_URL = "http://signet.bitcoindevkit.net"
val descriptor = Descriptor("wpkh(tprv8ZgxMBicQKsPf2qfrEygW6fdYseJDDrVnDv26PH5BHdvSuG6ecCbHqLVof9yZcMoM31z9ur3tTYbSnr1WBqbGX97CbXcmp5H6qeMpyvx35B/84h/1h/0h/0/*)", Network.SIGNET)
val changeDescriptor = Descriptor("wpkh(tprv8ZgxMBicQKsPf2qfrEygW6fdYseJDDrVnDv26PH5BHdvSuG6ecCbHqLVof9yZcMoM31z9ur3tTYbSnr1WBqbGX97CbXcmp5H6qeMpyvx35B/84h/1h/0h/1/*)", Network.SIGNET)

var connection: Connection = Connection.newInMemory()
val wallet: Wallet = Wallet(descriptor, changeDescriptor, Network.SIGNET, connection)
val esploraClient: EsploraClient = EsploraClient(SIGNET_ESPLORA_URL)
val fullScanRequest: FullScanRequest = wallet.startFullScan().build()
val update = esploraClient.fullScan(fullScanRequest, 10uL, 1uL)

wallet.applyUpdate(update)
val satBalance = wallet.balance().total.toSat()

println("Balance: $satBalance satoshi")
