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

package io.sip3.commons.domain

import io.vertx.core.json.JsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SdpSessionTest {

    @Test
    fun `Serialization to JSON`() {
        val sdpSession = SdpSession().apply {
            id = 1000L
            timestamp = System.currentTimeMillis()

            clockRate = 8000
            codecIe = 0F
            codecBpl = 4.3F
            payloadType = 0

            callId = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6@foo.bar.com"
        }

        JsonObject.mapFrom(sdpSession).apply {
            assertEquals(7, size())
            assertEquals(sdpSession.id, getLong("id"))
            assertEquals(sdpSession.timestamp, getLong("timestamp"))

            assertEquals(sdpSession.clockRate, getInteger("clock_rate"))
            assertEquals(sdpSession.codecIe, getFloat("codec_ie"))
            assertEquals(sdpSession.codecBpl, getFloat("codec_bpl"))
            assertEquals(sdpSession.payloadType, getInteger("payload_type").toByte())

            assertEquals(sdpSession.callId, getString("call_id"))
        }
    }

    @Test
    fun `Deserialization from JSON`() {
        val jsonObject = JsonObject().apply {
            put("id", 1000L)
            put("timestamp", System.currentTimeMillis())

            put("clock_rate", 8000)
            put("codec_ie", 0F)
            put("codec_bpl", 4.3F)
            put("payload_type", 0)

            put("call_id", "f81d4fae-7dec-11d0-a765-00a0c91e6bf6@foo.bar.com")
        }

        jsonObject.mapTo(SdpSession::class.java).apply {
            assertEquals(jsonObject.getLong("id"), id)
            assertEquals(jsonObject.getLong("timestamp"), timestamp)

            assertEquals(jsonObject.getInteger("clock_rate"), clockRate)
            assertEquals(jsonObject.getFloat("codec_ie"), codecIe)
            assertEquals(jsonObject.getFloat("codec_bpl"), codecBpl)
            assertEquals(jsonObject.getInteger("payload_type").toByte(), payloadType)

            assertEquals(jsonObject.getString("call_id"), callId)
        }
    }
}