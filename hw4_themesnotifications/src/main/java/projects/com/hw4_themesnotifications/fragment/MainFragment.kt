package projects.com.hw4_themesnotifications.fragment

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import projects.com.hw4_themesnotifications.MainActivity
import projects.com.hw4_themesnotifications.R
import projects.com.hw4_themesnotifications.adapter.ThemeAdapter
import projects.com.hw4_themesnotifications.databinding.FragmentMainBinding
import projects.com.hw4_themesnotifications.model.NotificationChannelData
import projects.com.hw4_themesnotifications.model.Theme
import projects.com.hw4_themesnotifications.repository.NotificationChannels
import projects.com.hw4_themesnotifications.repository.ThemeRepository

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var viewBinding: FragmentMainBinding
    private var redFlag = true
    private lateinit var dataList : List<Theme>


    private var thAdapter: ThemeAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding = FragmentMainBinding.bind(view)
        showHideRecycler()
        initRecyclerView()
        createSpinner()
        initNotification()
        cancelTheme()
        uploadImage()
        resetImage()
    }

    private fun showHideRecycler(){
        with(viewBinding){
            imageButton.setOnClickListener{
                if (redFlag) {
                    HorizontalRecycler.visibility = View.VISIBLE
                    redFlag = false
                    imageButton.setImageResource(R.drawable.ic_arrow_up_grey)
                } else{
                    HorizontalRecycler.visibility = View.GONE
                    redFlag = true
                    imageButton.setImageResource(R.drawable.ic_arrow_down_grey)
                }
            }
        }
    }
    private fun initRecyclerView(){
        dataList = ThemeRepository().themesList.map {
            it.copy(color = ContextCompat.getColor(requireContext(), it.color))
        }
        Log.d("MyLog", "Data list size: ${dataList.size}")
        thAdapter = ThemeAdapter(
            items = dataList,
            onItemClick = { position ->
                changeTheme(position)
            }
        )
        viewBinding.apply {
            HorizontalRecycler.adapter = thAdapter
            HorizontalRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
    private fun changeTheme(position: Int) {
        Log.d("MyLog","changeTheme, position = " + position)
        val id = dataList[position].id
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra(EXTRA_THEME,id)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun createSpinner(){
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            NotificationChannels().notificationsChannels.map { channel -> channel.name }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        viewBinding.spinner.adapter = adapter

    }

    private fun initNotification(){
        with(viewBinding){
            showNotification.setOnClickListener {
                Log.d("MyLog","showNotification")
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra(EXTRA_TOAST, true)
                val pendingIntent = PendingIntent.getActivity(
                    requireContext(),
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                val title = textTitle.editText?.text.toString()
                val description = textDescription.editText?.text.toString()
                val selectedChannel = spinner.selectedItem.toString()
                if (title.isEmpty()){
                    Toast.makeText(requireContext(), TITLE_EMPTY,Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (description.isEmpty()){
                    Toast.makeText(requireContext(), DESCRIPT_EMPTY,Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (selectedChannel.isEmpty()){
                    Toast.makeText(requireContext(), CHANNEL_EMPTY,Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                NotificationChannels().notificationsChannels.forEach{ channel ->

                    if (selectedChannel.equals(channel.name)){
                        Log.d("MyLog",selectedChannel)
                        val notification = NotificationCompat.Builder(requireContext(), channel.channelId)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle(title)
                            .setContentText(description)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .build()
                        val notificationManager = requireActivity().getSystemService(
                            NotificationManager::class.java)
                        val notificationId = 1
                        notificationManager.notify(notificationId, notification)
                    }
                }

            }
        }
    }

    private fun cancelTheme(){
        with(viewBinding){
            resetColor.setOnClickListener {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra(EXTRA_THEME,0)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }


    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            with(viewBinding) {
                avatar.setImageURI(uri)
                closeButton.visibility = View.VISIBLE
            }
        } ?: run {
            Toast.makeText(requireContext(), NOT_CHOOSE, Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage(){
        with(viewBinding) {
            avatar.setOnClickListener {
                if (avatar.drawable != null){
                    Toast.makeText(requireContext(), ALREADY_UPLOAD, Toast.LENGTH_SHORT).show()
                } else {
                    openGallery()
                }
            }
        }
    }

    private fun openGallery() {
        pickImageLauncher.launch(INPUT_GALLERY)
    }

    private fun resetImage(){
        with(viewBinding){
            closeButton.setOnClickListener {
                avatar.setImageURI(null)
                closeButton.visibility = View.GONE
            }
        }
    }

    companion object {
        const val EXTRA_THEME = "theme"
        const val NOT_CHOOSE = "Изображение не выбрано"
        const val INPUT_GALLERY = "image/*"
        const val ALREADY_UPLOAD = "Изображение уже загружено"
        const val EXTRA_TOAST = "show_toast"
        const val TITLE_EMPTY = "Заголовок пустой"
        const val DESCRIPT_EMPTY = "Текст пустой"
        const val CHANNEL_EMPTY = "Канал пустой"
    }
}