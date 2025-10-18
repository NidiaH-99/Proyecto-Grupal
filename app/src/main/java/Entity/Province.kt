package Entity

import androidx.constraintlayout.widget.StateSet

class Province {
    private var name: String=""
    private lateinit var stateSet: MutableList<String>   //  lateninit hace que no se inicialice de una vez, sino hasta despues, normalmente se inicializa en el constructor

    constructor()

    var Name: String
        get() = this.name
        set(value) {this.name=value}

    var States: MutableList<String>
        get() = this.States
        set(value) {this.States=value}
}