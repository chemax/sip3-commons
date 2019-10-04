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

class RtpHeaderPayload : Encodable, Decodable {

    var payloadType: Byte = 0
    var sequenceNumber: Int = 0
    var timestamp: Long = 0
    var ssrc: Long = 0

    override fun encode(): ByteBuf {
        return Unpooled.buffer(21).apply {
            writeByte(payloadType.toInt())
            writeInt(sequenceNumber)
            writeLong(timestamp)
            writeLong(ssrc)
        }
    }

    override fun decode(buffer: ByteBuf) {
        payloadType = buffer.readByte()
        sequenceNumber = buffer.readInt()
        timestamp = buffer.readLong()
        ssrc = buffer.readLong()
    }
}