package org.tryhard.teluevent.ui.addevent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.FragmentAddEventBinding
import org.tryhard.teluevent.model.event.DAOEvent
import org.tryhard.teluevent.model.event.Event


private const val REQUEST_CODE = 72
class AddEventFragment : Fragment() {



    private lateinit var binding:FragmentAddEventBinding
    private lateinit var event:Event
    private lateinit var dao:DAOEvent
    private var imageUri: Uri? = null
    private val storageReference = FirebaseStorage.getInstance().getReference("uploads")
    private lateinit var imageTitle:String



    companion object{
        val EDIT = "EDIT"
    }
    private val eventPass = Intent.getIntent("EDIT").getSerializableExtra("EDIT") as? Event


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)





        binding = FragmentAddEventBinding.inflate(layoutInflater,container,false)





        if(eventPass != null){
            binding.addEventBtn.text = "UPDATE"
            binding.titleEventInp.setText(event.title)
            binding.eventPlaceInp.setText(event.place)
            binding.eventDateInp.setText(event.date)
            binding.descriptionInp.setText(event.desc)
        }else{
            binding.addEventBtn.text = "Submit"
        }

        return  binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setImageViewHome()
        initAction()

        binding.addEventBtn.setOnClickListener {
            uploadImagetoFirebase()
            insertEvent()
//            updateEvent()
//            removeEvent()
        }
    }

    // Fungsi untuk mengambil uri gambar yang telah di pilih

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            data?.data?.let {
                binding.imageViewHome.load(imageUri){
                    imageUri = it
                    crossfade(true)
                    crossfade(500)
                    transformations(RoundedCornersTransformation(15f))
                }
            }
        }
    }







    private fun insertEvent(){

        val title = binding.titleEventInp.text.toString()
        val place = binding.eventPlaceInp.text.toString()
        val date = binding.eventDateInp.text.toString()
        val desc = binding.descriptionInp.text.toString()

        Log.d("EventFragment","$title,$place,$date,$desc")

        if (eventPass == null){
            val dao = DAOEvent()

            val data = Event(title,place,date,desc)

            dao.add(data).addOnSuccessListener {
                Toast.makeText(context,"Event has been created",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
            }
        }else {
            val dao = DAOEvent()

            val hashMap:MutableMap<String,Any> = HashMap()
            hashMap["title"] = title
            hashMap["place"] = place

            event.key?.let {
                dao.update(it,hashMap).addOnSuccessListener {
                    Toast.makeText(context,"Event has been updated",Toast.LENGTH_SHORT).show()

                }.addOnFailureListener{
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        }






    }


    private fun initAction(){
        this.binding.buttonSelectImage.setOnClickListener{
            Intent(Intent.ACTION_GET_CONTENT).also{
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE)

            }
        }


    }

    private fun uploadImagetoFirebase(){
        imageTitle = binding.editTextTitle.text.toString().trim()
        if(imageUri != null){
            if(imageTitle.isBlank() || imageTitle.isEmpty()){
                binding.inputTextTitle.error = "*Required"
            }else{
//                binding.progressBarLoadingIndicator.isIndeterminate = false
//                binding.progressBarLoadingIndicator.visibility = View.VISIBLE
//                binding.textViewIndicatorLoading.visibility = View.VISIBLE
                binding.inputTextTitle.error = null
                uploadImage(imageTitle)
            }
        }else{
            Toast.makeText(context,"Select Image First",Toast.LENGTH_SHORT).show()

        }
    }




    //mengatuhr backgroud pada image view

    private fun setImageViewHome(){
        binding.imageViewHome.load(context?.let { ContextCompat.getDrawable(it, R.drawable.shape) }){
            crossfade(true)
            crossfade(500)
            transformations(RoundedCornersTransformation(15f))
        }
    }


    //fungsi upload image

    private fun uploadImage(title:String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            imageUri?.let { uri ->
                storageReference.child(title).putFile(uri)
                    .addOnProgressListener {
//                        val progress: Int = ((100 * it.bytesTransferred)/ it.totalByteCount).toInt()
//                        binding.progressBarLoadingIndicator.progress = progress
//                        val indicatorText = "Loading..$progress%"
//                        binding.textViewIndicatorLoading.text = indicatorText
                    }.await()
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Success Upload",Toast.LENGTH_SHORT).show()
                }
            }

        }catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateEvent(){
        val title = binding.titleEventInp.text.toString()
        val place = binding.eventPlaceInp.text.toString()
        val dao = DAOEvent()

        val hashMap:MutableMap<String,Any> = HashMap()
        hashMap["title"] = title
        hashMap["place"] = place

        dao.update("-N5_nJYWixBdNl0ic4hB",hashMap).addOnSuccessListener {
            Toast.makeText(context,"Event has been updated",Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
            Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
        }


    }


    private fun removeEvent(){
        val key = "-N5_nJYWixBdNl0ic4hB"
        val dao = DAOEvent()
        dao.remove(key).addOnSuccessListener {
            Toast.makeText(context,"Event has been removed",Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
            Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
        }
    }




}