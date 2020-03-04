package rizky.f.sabtugas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_frd.view.*


class MainActivity : AppCompatActivity() {

    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = FirebaseDatabase.getInstance()

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        getData()
    }

    fun getData(){
        val myRef = database.getReference("users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    val list = ArrayList<Users>()
                    for (h in dataSnapshot.children){
                        val user = h.getValue(Users::class.java!!)
                        list!!.add(user!!)
                    }
                    Log.e("list", list.toString())
                    val adapter = FrdAdapter(list, R.layout.list_data)
                    val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                    list_data.layoutManager = linearLayoutManager
                    list_data.hasFixedSize()
                    list_data.adapter = adapter
                    adapter.onItemClick = { it1 ->
                        editData(it1)
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    fun editData(it: Users) {
        val dialogs = LayoutInflater.from(this).inflate(R.layout.dialog_frd, null)
        var alertbox = AlertDialog.Builder(this)
        dialogs.edt_username.setText(it.name)
        dialogs.edt_nrp.setText(it.nrp)
        dialogs.edt_jurusan.setText(it.jurusan)
        alertbox.run {
            setView(dialogs)
            setPositiveButton("UPDATE") { dialog, which ->

                val name = dialogs.edt_username.text.toString()
                val nrp = dialogs.edt_nrp.text.toString()
                val jurusan = dialogs.edt_jurusan.text.toString()
                var users = Users(it.id, name, nrp, jurusan)
                updateData(users)
                dialog.dismiss()

            }
            setNegativeButton("CANCEL") { dialog, which ->
                dialog.dismiss()
            }
            setNeutralButton("DELETE") { dialog, which ->
                deleteData(it)
                dialog.dismiss()
            }
            show()
        }
    }
    fun updateData(users: Users){
        database.getReference("users").child(users.id).child("name").setValue(users.name)
        database.getReference("users").child(users.id).child("nrp").setValue(users.nrp)
        database.getReference("users").child(users.id).child("jurusan").setValue(users.jurusan)
    }

    fun deleteData(users: Users){
        database.getReference("users").child(users.id).removeValue()
    }

}
