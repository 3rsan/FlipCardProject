package flipcard.n11.com.flipcardproject


import android.content.Intent
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_register.*


class Register : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_register, container, false)

        val context = activity as AppCompatActivity
        val btnSave: Button = v.findViewById(R.id.btnSave)
        val pickImage: ImageView = v.findViewById(R.id.pickImage)

        btnSave.setOnClickListener {
            context.replaceFragment(Login())


        }


        pickImage.setOnClickListener( object : View.OnClickListener{

            override fun onClick(v: View?) {
                pickImageFromGallery()
            }

        })
        return v
    }

    fun AppCompatActivity.replaceFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            pickImage.setImageURI(data?.data)
        }


    }
}
