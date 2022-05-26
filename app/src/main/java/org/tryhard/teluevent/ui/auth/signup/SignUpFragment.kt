package org.tryhard.teluevent.ui.auth.signup


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.FragmentSignUpBinding
import org.tryhard.teluevent.ui.auth.signin.SignInFragment


class SignUpFragment : Fragment() {
    private lateinit var nav:NavController
    private lateinit var binding:FragmentSignUpBinding
    lateinit var auth:FirebaseAuth


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
                    Toast.makeText(getContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(getContext(),SignInFragment::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(getContext(), "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


}