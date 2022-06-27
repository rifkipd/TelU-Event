package org.tryhard.teluevent.ui.auth.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.tryhard.teluevent.MainActivity
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.FragmentSignInBinding
import org.tryhard.teluevent.ui.auth.admin.AdminActivity

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var nav: NavController
    private lateinit var auth:FirebaseAuth
    private lateinit var fStore:FirebaseFirestore
    private lateinit var df:DocumentReference
    private lateinit var user:FirebaseUser

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
        fStore = FirebaseFirestore.getInstance()




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

// biar langsung login pas buka aplikasi
//    override fun onStart() {
//        super.onStart()
//        if(FirebaseAuth.getInstance().currentUser != null){
//            val intent = Intent(activity,MainActivity::class.java)
//            startActivity(intent)
//            activity?.finish()
//        }
//    }


    private fun LoginFirebase(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                Toast.makeText(context, "$email Berhasil Sign In", Toast.LENGTH_SHORT).show()
                checkUserAccessLevel(it.user!!.uid)
            }.addOnFailureListener {
                Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
            }





    }

    private fun checkUserAccessLevel(uid:String) {
       val df =  fStore.collection("Users").document(uid)

        //extract data from document
       df.get().addOnSuccessListener { documentSnapshot ->
           Log.d("TAG","onSuccess:" + documentSnapshot.data)
           if(documentSnapshot.getString("isAdmin") != null){
               val adminActivity = Intent(activity,AdminActivity::class.java)
               startActivity(adminActivity)
               activity?.finish()
           }else{
               val userActivity = Intent(activity,MainActivity::class.java)
               startActivity(userActivity)
               activity?.finish()
           }
       }

    }


}