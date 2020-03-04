package rizky.f.sabtugas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.edt_username


class AddActivity : AppCompatActivity() {
    var mDatabase = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        title = "Add Info"

        btn_save.setOnClickListener {
            when {
                edt_username.text.isEmpty() -> {
                    edt_username.error = "Jangan Kosong"
                }
                edt_nrp.text.isEmpty() -> {
                    edt_nrp.error = "Jangan Kosong"
                }
                edt_jurusan.text.isEmpty() ->{
                    edt_jurusan.error = "Jangan Kosong"
                }
                else -> {
                    val name = edt_username.text.toString()
                    val nrp = edt_nrp.text.toString()
                    val jurusan = edt_jurusan.text.toString()
                    insertData(name, nrp, jurusan)
                }
            }
        }
    }

    fun insertData(name: String, email: String, jurusan: String) {
        val userId = mDatabase.push().key
        val users = Users(userId!!, name, email, jurusan)
        mDatabase.child(userId!!).setValue(users)
        onBackPressed()


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
