package com.flatcode.simpleadvancedapps.MainApp

class MainInfo {
    var title: String? = null
    var s: Boolean? = null
    var s2: Boolean? = null
    var s3: Boolean? = null
    var s4: Boolean? = null
    var s5: Boolean? = null
    var s6: Boolean? = null

    constructor(
        title: String?, s: Boolean?, s2: Boolean?, s3: Boolean?,
        s4: Boolean?, s5: Boolean?, s6: Boolean?,
    ) {
        this.title = title
        this.s = s
        this.s2 = s2
        this.s3 = s3
        this.s4 = s4
        this.s5 = s5
        this.s6 = s6
    }

    constructor(title: String?) {
        this.title = title
    }

    constructor()
}