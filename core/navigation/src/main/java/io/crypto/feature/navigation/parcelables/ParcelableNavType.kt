package io.crypto.feature.navigation.parcelables

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import java.io.Serializable

class ParcelableNavType<D : Parcelable>(type: Class<D>) : NavType<D>(isNullableAllowed = false) {
    private val type: Class<D>

    public override val name: String
        get() = type.name

    public override fun put(bundle: Bundle, key: String, value: D) {
        type.cast(value)
        bundle.putParcelable(key, value as Parcelable?)
    }

    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    public override fun get(bundle: Bundle, key: String): D? {
        return bundle[key] as D?
    }

    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    override fun parseValue(value: String): D {
        return ParcelableNavTypeSerializer(type).fromRouteString(value) as D
    }

    public override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as ParcelableNavType<*>
        return type == that.type
    }

    init {
        require(
            Parcelable::class.java.isAssignableFrom(type) ||
                    Serializable::class.java.isAssignableFrom(type)
        ) { "$type does not implement Parcelable or Serializable." }
        this.type = type
    }
}