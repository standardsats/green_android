package com.blockstream.common.gdk.params

import com.blockstream.common.gdk.GdkJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressParams constructor(
    @SerialName("address") val address: String,
    @SerialName("is_greedy") var isGreedy: Boolean,
    @SerialName("asset_id") var assetId: String? = null,
    @SerialName("satoshi") var satoshi: Long,
) : GdkJson<AddressParams>() {
    override fun encodeDefaultsValues() = false

    override fun kSerializer() = serializer()
}