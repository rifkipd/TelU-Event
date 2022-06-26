package org.tryhard.teluevent.ui.auth.signup


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.model.ObjectValue
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import org.tryhard.teluevent.MainActivity
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.FragmentSignUpBinding
import java.util.*
import kotlin.collections.HashMap


class SignUpFragment : Fragment() {
    private lateinit var nav:NavController
    private lateinit var binding:FragmentSignUpBinding
    lateinit var auth:FirebaseAuth
    lateinit var  fStore:FirebaseFirestore
    lateinit var df:DocumentReference
    private lateinit var user:FirebaseUser







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSignUpBinding.inflate(layoutInflater,container,false)




        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()


        binding.btnSignUpRegister.setOnClickListener { register() }
    }

    private fun register(){



        val email = binding.usernameInp.text.toString()
        val password = binding.passwordInp.text.toString()


        //validasi email
        if(email.isEmpty()){
            binding.usernameInp.error = getString(R.string.email_empty)
            binding.usernameInp.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.usernameInp.error = getString(R.string.email_invalid)
            binding.usernameInp.requestFocus()
            return
        }


        //validasi password
        if(password.isEmpty()){
            binding.passwordInp.error = getString(R.string.password_empty)
            binding.passwordInp.requestFocus()
            return
        }

        //validasi password length

        if (password.length < 6){
            binding.passwordInp.error = getString(R.string.password_less)
            binding.passwordInp.requestFocus()
            return
        }

        RegisterFirebase(email,password)
    }

    private fun RegisterFirebase(email: String, password: String) {


        auth.createUserWithEmailAndPassword(email , password)
            .addOnCompleteListener { (this)

                if (it.isSuccessful){

                    user = auth.currentUser!!
                    Toast.makeText(getContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                    df = fStore.collection("Users").document(user.uid)
                    val userInfo:MutableMap<String,Any> = HashMap()
                    userInfo["Fullname"] = fullname_inp.text.toString()
                    userInfo["isUser"] = "1"
                    df.set(userInfo)


                    //specify if the user is Admin

                    val intent = Intent(activity,MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()

                }else{
                    Toast.makeText(getContext(), "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }





}



