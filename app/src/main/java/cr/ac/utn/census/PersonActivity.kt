package cr.ac.utn.census

import Controller.PersonController
import Entity.Person
import Entity.Province
import android.app.DatePickerDialog
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Global.getString
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.util.Calendar

class PersonActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtFLastName: EditText
    private lateinit var txtSLastName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPhone: EditText
    private lateinit var lbBirthdate: TextView
    private lateinit var txtProvince: EditText
    private lateinit var txtState: EditText
    private lateinit var txtDistrict: EditText
    private lateinit var txtAddress: EditText
    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0

    private var IsEditMode: Boolean= false

    private lateinit var personController: PersonController

    private lateinit var menuItemDelete: MenuItem




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_person)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        personController = PersonController(this)
        txtId = findViewById<EditText>(R.id.TxtId_Person)
        txtName = findViewById<EditText>(R.id.TxtName_Person)
        txtFLastName = findViewById<EditText>(R.id.TxtLastName_Person)
        txtSLastName = findViewById<EditText>(R.id.TxtSLastName_Person)
        txtEmail = findViewById<EditText>(R.id.TxtEmail_Person)
        txtPhone = findViewById<EditText>(R.id.TxtPhone_Person)
        txtProvince = findViewById<EditText>(R.id.TxtProvince_Person)
        lbBirthdate = findViewById<TextView>(R.id.lbBirthday_person)
        txtState = findViewById<EditText>(R.id.TxtState_Person)
        txtDistrict = findViewById<EditText>(R.id.TxtDistrict_Person)
        txtAddress = findViewById<EditText>(R.id.TxtAddress_Person)

        ResetDate()

        val btnSearch = findViewById<ImageButton>(R.id.btnSearchId_person)
        btnSearch.setOnClickListener(View.OnClickListener { view ->
            searchPerson(txtId.text.trim().toString())
        })

        val btnSelectDate = findViewById<ImageButton>(R.id.btnSelectDate_person)
        btnSelectDate.setOnClickListener(View.OnClickListener { view ->
            showDatePickerDialog()
        })
    }

    private fun getDateString(dayValue: Int, monthValue: Int, yearValue: Int): String {
        return "${if (dayValue < 10) "0" else ""}$dayValue/${if (dayValue < 10) "0" else ""}$dayValue"
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        lbBirthdate.text= getDateString(dayOfMonth, month+1, year)

    }

    private fun ResetDate() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this, this, year, month, day)
        datePickerDialog.show()
    }

    private fun searchPerson(id: String) {

        try {
            val person = personController.getById(txtId.text.trim().toString())

            if (person != null){
                IsEditMode=true
                txtId.isEnabled= false
                txtName.setText(person.Name)
                txtFLastName.setText(person.FLastName)
                txtSLastName.setText(person.SLastName)
                txtEmail.setText(person.Email)
                txtPhone.setText(person.Phone)
                txtAddress.setText(person.Address)
                txtProvince.setText(person.Province.Name)
                txtState.setText(person.State)
                txtDistrict.setText(person.District)
                lbBirthdate.setText(getDateString(person.Birthday.dayOfMonth, person.Birthday.month.value, person.Birthday.year))

                day= person.Birthday.dayOfMonth
                month= person.Birthday.month.value
                year= person.Birthday.dayOfYear

            }else{
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun cleanScreen(){
        ResetDate()
        IsEditMode=true
        txtId.isEnabled= false
        txtName.setText("")
        txtFLastName.setText("")
        txtSLastName.setText("")
        txtEmail.setText("")
        txtPhone.setText("")
        txtAddress.setText("")
        txtProvince.setText("")
        txtState.setText("")
        txtDistrict.setText("")
        lbBirthdate.setText("")
        invalidateOptionsMenu()
    }

    fun isValidationData(): Boolean{
        val dateparse = Util.Util.parseStringToDateModern(lbBirthdate.text.toString(), "dd/MM/yyyy")
        return txtId.text.trim().isNotEmpty() && txtName.text.trim().isNotEmpty()
                && txtFLastName.text.trim().isNotEmpty() && txtSLastName.text.trim().isNotEmpty()
                && txtEmail.text.trim().isNotEmpty() && lbBirthdate.text.trim().isNotEmpty()
                && txtProvince.text.trim().isNotEmpty() && txtState.text.trim().isNotEmpty()
                && txtDistrict.text.trim().isNotEmpty() && txtAddress.text.trim().isNotEmpty()
                && (txtPhone.text.trim().isNotEmpty() && txtPhone.text.trim().length >= 8
                && txtPhone.text.toString()?.toInt()!! != null && txtPhone.text.toString()?.toInt()!! != 0)
                && dateparse != null
    }


    fun savePerson(){
        try {
            if (isValidationData()){
                if (personController.getById(txtId.text.toString().trim()) != null
                    && !IsEditMode){
                    Toast.makeText(this, getString(R.string.MsgDuplicateDate)
                        , Toast.LENGTH_LONG).show()
                }else{
                    val person = Person()
                    person.ID = txtId.text.toString()
                    person.Name = txtName.text.toString()
                    person.FLastName = txtFLastName.text.toString()
                    person.SLastName = txtSLastName.text.toString()
                    person.Email = txtEmail.text.toString()
                    person.Phone = txtPhone.text.toString().toInt()
                    val bDateParse = Util.Util.parseStringToDateModern(lbBirthdate.text.toString(),
                        "dd/MM/yyyy")
                    person.Birthday = LocalDate.of(bDateParse?.year!!, bDateParse.month.value
                        , bDateParse?.dayOfMonth!!)
                    val province = Province()
                    province.Name= txtProvince.text.toString()
                    person.Province = province
                    person.State = txtState.text.toString()
                    person.District = txtDistrict.text.toString()
                    person.Address= txtAddress.text.toString()

                    if (!IsEditMode)
                        personController.addPerson(person)
                    else
                        personController.updatePerson(person)

                    cleanScreen()

                    Toast.makeText(this, getString(R.string.MsgSaveSucess)
                        , Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Datos incompletos"
                    , Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete= menu!!.findItem(R.id.mnuDelete)
        menuItemDelete.isVisible = IsEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mnuSaved ->{
                if (IsEditMode){
                    Util.Util.showDialogCondition(this
                        , getString(R.string.TextSaveQuestion)
                        , { savePerson() })
                }else{
                    savePerson()
                }
                return true
            }
            R.id.mnuDelete ->{
                Util.Util.showDialogCondition(this
                    , getString(R.string.TextDeleteQuestion)
                    , { deletePerson() })
                return true
            }
            R.id.mnuCancel ->{
                cleanScreen()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun deletePerson(){
        try {
            personController.removePerson(txtId.text.trim().toString())
            cleanScreen()
            Toast.makeText(this, getString(R.string.MsgDeleteSucess), Toast.LENGTH_LONG).show()
        }catch (e: Exception) {
            Toast.makeText(
                this, e.message.toString(), Toast.LENGTH_LONG
            ).show()
        }

    }





}
