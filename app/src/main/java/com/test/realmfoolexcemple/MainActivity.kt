package com.test.realmfoolexcemple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.test.realmfoolexcemple.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.query
import io.realm.query.RealmQuery

open class DataModel : RealmObject {
    var id = 0
    var name: String? = null
    var email: String? = null

}

class MainActivity : AppCompatActivity() {

    var realm: Realm? = null
    val dataModel = DataModel()

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

       // realm = Realm.getDefaultInstance()
        val configuration = RealmConfiguration.with(schema = setOf(DataModel::class))
        realm = Realm.open(configuration)


        binding.btnInsertdata.setOnClickListener{addData()}
        binding.btnReaddata.setOnClickListener { readData() }
        binding.btnUpdatedata.setOnClickListener { updateData() }
        binding.btnDeletedata.setOnClickListener { deleteData() }
    }




 fun deleteData() {

        try {
           /** val id: Long = binding.layoutInclude.edtId.text.toString().toLong()
            val dataModel =
                realm!!.where(DataModel::class).equalTo("id", id).findFirst()
            realm!!.executeTransaction {
                dataModel?.deleteFromRealm()
            }
            clearFields()
**/
            realm!!.writeBlocking {
                val query : RealmQuery<DataModel> = this.query()
                delete(query)
            }
            Log.d("Status","Data deleted !!!")

        }catch (e:Exception){
            Log.d("Status","Something went Wrong !!!")
        }
    }

    fun updateData() {

        try {

            val id = binding.layoutInclude.edtId.text.toString().toInt()
            val _name = binding.layoutInclude.edtName.text.toString()
            val _email = binding.layoutInclude.edtEmail.text.toString()


             realm!!.query<DataModel>("id = $0",id).first().find()
                    ?.also { dataModel ->
                            realm!!.writeBlocking {
                                findLatest(dataModel)?.apply {
                                    name = _name
                                    email = _email
                                }
                            }

                    }



            Log.d("Status","Data Fetched !!!")
        }catch (e:Exception){
            Log.d("Status","Something went Wrong !!!")
        }
    }

    fun addData() {

        try {
            val data = DataModel().apply{
                id = binding.layoutInclude.edtId.text.toString().toInt()
                name = binding.layoutInclude.edtName.text.toString()
                email = binding.layoutInclude.edtEmail.text.toString()
            }


            realm!!.writeBlocking { copyToRealm(data) }

            clearFields()

            Log.d("Status","Data Inserted !!!")

        }catch (e:Exception){
            Log.d("Status","Something went Wrong !!!")
        }
    }

    fun readData() {

        try {

            val dataModels: List<DataModel> =
                realm!!.query(DataModel::class).find()

            for (i in dataModels.indices) {
                binding.layoutInclude.edtId?.setText("" + dataModels[i].id)
                binding.layoutInclude.edtName?.setText(dataModels[i].name)
                binding.layoutInclude.edtEmail?.setText(dataModels[i].email)
            }

            Log.d("Status","Data Fetched !!!")

        } catch (e: Exception) {
            Log.d("Status","Something went Wrong !!!")
        }
    }

    fun clearFields(){

        binding.layoutInclude.edtId.setText("")
        binding.layoutInclude.edtName.setText("")
        binding.layoutInclude.edtEmail.setText("")
    }
}