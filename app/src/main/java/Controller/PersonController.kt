package Controller

import Data.IDataManager
import Data.MemoryDataManager
import Entity.Person
import android.content.Context
import cr.ac.utn.census.R

//va a llamar todos los metos de updates, remove,

class PersonController {

    private var dataManager: IDataManager = MemoryDataManager
    private lateinit var contex: Context

    constructor(contex: Context){
        this.contex=contex
    }

    fun addPerson(person: Person){
        try {
            dataManager.add(person)
        }catch (e: Exception){
            throw Exception(contex.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updatePerson(person: Person){
        try {
            dataManager.add(person)
        }catch (e: Exception){
            throw Exception(contex.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): Person?{
        try {
            return dataManager.getById(id)
        }catch (e: Exception){
            throw Exception(contex.getString(R.string.ErrorMsgGetById))
        }
    }

    fun getByFullName(fullname: String): Person?{
        try {
            return  dataManager.getByFullName(fullname)
        }catch (e: Exception){
            throw Exception(contex.getString(R.string.ErrorMsgGetById))
        }
    }

    fun removePerson(id: String) {
        try {
            dataManager.remove(id)

        } catch (e: Exception) {
            throw Exception(
                contex
                    .getString(R.string.ErrorMsgRemove)
            )
        }

    }

}