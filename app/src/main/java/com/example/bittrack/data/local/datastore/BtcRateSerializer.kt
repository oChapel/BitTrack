package com.example.bittrack.data.local.datastore

import androidx.datastore.core.Serializer
import com.example.bittrack.proto.BtcRate
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object BtcRateSerializer : Serializer<BtcRate> {

    override val defaultValue: BtcRate = BtcRate.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): BtcRate {
        try {
            return BtcRate.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw exception
        }
    }

    override suspend fun writeTo(t: BtcRate, output: OutputStream) = t.writeTo(output)
}
