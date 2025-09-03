package com.android.module_compose2.loading

abstract class Defaults {

    abstract class Target<T : Defaults>(defaults: T) {

        private var _instance: T = defaults
        val instance: T get() = _instance

        fun set(defaults: T) {
            _instance = defaults
        }
    }
}
