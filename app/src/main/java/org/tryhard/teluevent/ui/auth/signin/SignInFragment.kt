package org.tryhard.teluevent.ui.auth.signin

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.FragmentSignInBinding
import org.tryhard.teluevent.ui.home.HomeActivity

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var nav: NavController
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSignInBinding.inflate(layoutInflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        nav = Navigation.findNavController(view)
        binding.btnSignup2.setOnClickListener {
            nav.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.btnSignn.setOnClickListener { login() }
    }


    private fun login(){
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


        LoginFirebase(email,password)
    }


    private fun LoginFirebase(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { (this)
                if (it.isSuccessful){
                    Toast.makeText(getContext(), "$email Berhasil Sign In", Toast.LENGTH_SHORT).show()
                    val intent = Intent(getContext(),HomeActivity::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(getContext(), "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


}