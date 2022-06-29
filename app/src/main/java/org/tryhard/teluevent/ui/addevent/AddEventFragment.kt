package org.tryhard.teluevent.ui.addevent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.tryhard.teluevent.databinding.FragmentAddEventBinding
import org.tryhard.teluevent.model.event.DAOEvent
import org.tryhard.teluevent.model.event.Event



class AddEventFragment : Fragment() {



    private lateinit var binding:FragmentAddEventBinding
    private lateinit var event:Event
    private lateinit var dao:DAOEvent



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
        binding.addEventBtn.setOnClickListener {
            insertEvent()
//            updateEvent()
//            removeEvent()
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