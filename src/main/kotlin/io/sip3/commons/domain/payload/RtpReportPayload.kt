/*
 * Copyright 2018-2019 SIP3.IO, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.sip3.commons.domain.payload

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.sip3.commons.util.writeTlv
import java.nio.charset.Charset

class RtpReportPayload : Payload {

    companion object {

        const val BASE_PAYLOAD_LENGTH = 103

        const val SOURCE_RTP = 0.toByte()
        const val SOURCE_RTCP = 1.toByte()

        const val TAG_SOURCE = 1
        const val TAG_PAYLOAD_TYPE = 2
        const val TAG_SSRC = 3
        const val TAG_CALL_ID = 4

        const val TAG_EXPECTED_PACKET_COUNT = 11
        const val TAG_RECEIVED_PACKET_COUNT = 12
        const val TAG_LOST_PACKET_COUNT = 13
        const val TAG_REJECTED_PACKET_COUNT = 14

        const val TAG_DURATION = 15

        const val TAG_LAST_JITTER = 16
        const val TAG_AVG_JITTER = 17
        const val TAG_MIN_JITTER = 18
        const val TAG_MAX_JITTER = 19

        const val TAG_R_FACTOR = 20
        const val TAG_MOS = 21
        const val TAG_FRACTION_LOST = 22
    }

    var source: Byte = -1

    var payloadType: Byte = 0
    var ssrc: Long = 0
    var callId: String? = null

    var expectedPacketCount: Int = 0
    var receivedPacketCount: Int = 0
    var lostPacketCount: Int = 0
    var rejectedPacketCount: Int = 0

    var duration: Int = 0

    var lastJitter: Float = 0F
    var avgJitter: Float = 0F
    var minJitter: Float = 10000F
    var maxJitter: Float = 0F

    var rFactor: Float = 0F
    var mos: Float = 1.0F
    var fractionLost: Float = 0F

    override fun encode(): ByteBuf {
        var bufferSize = BASE_PAYLOAD_LENGTH
        callId?.let { bufferSize += it.length + 3 }

        return Unpooled.buffer(bufferSize).apply {
            writeTlv(TAG_SOURCE, source)

            writeTlv(TAG_PAYLOAD_TYPE, payloadType)
            writeTlv(TAG_SSRC, ssrc)
            callId?.let { writeTlv(TAG_CALL_ID, it) }

            writeTlv(TAG_EXPECTED_PACKET_COUNT, expectedPacketCount)
            writeTlv(TAG_RECEIVED_PACKET_COUNT, receivedPacketCount)
            writeTlv(TAG_LOST_PACKET_COUNT, lostPacketCount)
            writeTlv(TAG_REJECTED_PACKET_COUNT, rejectedPacketCount)

            writeTlv(TAG_DURATION, duration)

            writeTlv(TAG_LAST_JITTER, lastJitter)
            writeTlv(TAG_AVG_JITTER, avgJitter)
            writeTlv(TAG_MIN_JITTER, minJitter)
            writeTlv(TAG_MAX_JITTER, maxJitter)

            writeTlv(TAG_R_FACTOR, rFactor)
            writeTlv(TAG_MOS, mos)
            writeTlv(TAG_FRACTION_LOST, fractionLost)
        }
    }

    override fun decode(byteBuf: ByteBuf) {
        var offset = 0
        while (offset < byteBuf.capacity()) {
            // Type
            val type = byteBuf.getByte(offset)
            // Length
            offset += 1
            val length = byteBuf.getShort(offset) - 3
            // Value
            offset += 2
            when (type.toInt()) {
                1 -> source = byteBuf.getByte(offset)
                2 -> payloadType = byteBuf.getByte(offset)
                3 -> ssrc = byteBuf.getLong(offset)
                4 -> callId = byteBuf.getCharSequence(offset, length, Charset.defaultCharset()).toString()
                11 -> expectedPacketCount = byteBuf.getInt(offset)
                12 -> receivedPacketCount = byteBuf.getInt(offset)
                13 -> lostPacketCount = byteBuf.getInt(offset)
                14 -> rejectedPacketCount = byteBuf.getInt(offset)
                15 -> duration = byteBuf.getInt(offset)
                16 -> lastJitter = byteBuf.getFloat(offset)
                17 -> avgJitter = byteBuf.getFloat(offset)
                18 -> minJitter = byteBuf.getFloat(offset)
                19 -> maxJitter = byteBuf.getFloat(offset)
                20 -> rFactor = byteBuf.getFloat(offset)
                21 -> mos = byteBuf.getFloat(offset)
                22 -> fractionLost = byteBuf.getFloat(offset)
            }
            offset += length
        }
    }
}
