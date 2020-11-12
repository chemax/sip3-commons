/*
 * Copyright 2018-2020 SIP3.IO, Inc.
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

class Codec {

    var name: String = "UNDEFINED"

    @JsonProperty("payload_types")
    var payloadTypes: List<Int> = listOf(-1)

    @JsonProperty("clock_rate")
    var clockRate: Int = 8000

    var ie: Float = 5.0F
    var bpl: Float = 10.0F
}