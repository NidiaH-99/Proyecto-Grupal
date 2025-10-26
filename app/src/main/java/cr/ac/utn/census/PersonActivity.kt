package cr.ac.utn.census

import Entity.Person
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.census.R.id.TxtEmail_Person

class PersonActivity : AppCompatActivity() {
    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtSLastName: EditText
    private lateinit var txtBirthdate: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtProvince: EditText
    private lateinit var txtState: EditText
    private lateinit var txtDistrict: EditText
    private lateinit var txtAddress: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_person)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
            txtId= findViewById<EditText>(R.id.TxtID_Person)
            txtName= findViewById<EditText>(R.id.TxtName_Person)
            txtLastName= findViewById<EditText>(R.id.TxtLastName_Person)
            txtSLastName= findViewById<EditText>(R.id.TxtSLastName_Person)
            txtBirthdate= findViewById<EditText>(R.id.TxtBirthdate_Person)
            txtPhone= findViewById<EditText>(R.id.TxtPhone_Person)
            txtEmail= findViewById<EditText>(R.id.TxtEmail_Person)
            txtProvince= findViewById<EditText>(R.id.TxtProvince_Person)
            txtState= findViewById<EditText>(R.id.TxtState_Person)
            txtDistrict= findViewById<EditText>(R.id.TxtDistrict_Person)
            txtAddress= findViewById<EditText>(R.id.TxtAddress_Person)

        val btnSave = findViewById<Button>(R.id.btnSave_person)
        btnSave.setOnClickListener(View.OnClickListener { view ->
            savePerson()
        })


    }

    fun validationData(person): Boolean{
        return true
    }

    fun savePerson(){
        try {
            val person = Person()
            person ID = txt.text.toString()
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
