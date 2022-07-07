package org.tryhard.teluevent.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.FragmentProfileBinding
import org.tryhard.teluevent.ui.auth.AuthActivity
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {


    companion object{
        const val REQUEST_CAMERA = 100
    }


    private lateinit var  imageUri: Uri
    private lateinit var binding:FragmentProfileBinding
    private lateinit var fAuth:FirebaseAuth
    private lateinit var fStore:FirebaseFirestore
    private lateinit var dRef:DocumentReference
    private lateinit var fStorage:FirebaseStorage
    var userId:String = ""


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_profile,container
        ,false)
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        fStore = FirebaseFirestore.getInstance()
        fStorage = FirebaseStorage.getInstance()


        userId = fAuth.currentUser?.uid.toString()
        val emailUser = FirebaseAuth.getInstance().currentUser?.email

        dRef = fStore.collection("Users").document(userId)


        dRef.addSnapshotListener(EventListener { value, error ->
            if (value != null) {
                tvProfileName.text = value.getString("Fullname")
                tvProfileEmail.text = emailUser
            }
        })







        val sectionPagerAdapter = SectionPagerAdapter(
            childFragmentManager
        )
        viewPager.adapter = sectionPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivAddProfile.setOnClickListener{
            intentCamera()
        }
        fAuth = FirebaseAuth.getInstance()
        val user:FirebaseUser? = fAuth.currentUser

        if(user != null){
            if(user.photoUrl != null){
                Picasso.get().load(user.photoUrl).into(ivProfileImage)
            }else
                Picasso.get().load(R.drawable.default_profile).into(ivProfileImage)


        }



        logoutBtn.setOnClickListener {
            fAuth.signOut()
            val intent = Intent(context,AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

    }

    private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }

    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid
        }")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val image = baos.toByteArray()


        ref.putBytes(image)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener{
                        it.result?.let{
                            imageUri = it
                            ivProfileImage.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }


}