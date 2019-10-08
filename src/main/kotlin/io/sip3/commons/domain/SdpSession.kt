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

import com.fasterxml.jackson.annotation.JsonProperty

class SdpSession {

    var id: Long = 0
    var timestamp: Long = 0

    @JsonProperty("payload_type")
    var payloadType: Byte = 0
    @JsonProperty("clock_rate")
    var clockRate = 0
    @JsonProperty("codec_ie")
    var codecIe = 0F
    @JsonProperty("codec_bpl")
    var codecBpl = 0F

    @JsonProperty("call_id")
    lateinit var callId: String
}