package com.zafar.mediaplayerdemo

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.song_ticket.view.*

class MainActivity : AppCompatActivity() {

    var offlineSongList = ArrayList<SongInfo>()
    var onlineSongList = ArrayList<SongInfo>()
    var adapter: MySongAdapter? = null
    var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadOnlineURL()
        CheckUserPermsions()

        val mySongTrack = mySongTrack()
        mySongTrack.start()

        textOfflineSongs.setOnClickListener {
            loadSong()
        }

        textOnlineSongs.setOnClickListener {
            playOnlineSongs()
        }

    }

    private fun playOnlineSongs() {
        adapter = MySongAdapter(onlineSongList)
        lsListSongs.adapter = adapter
        adapter!!.notifyDataSetInvalidated()
        adapter!!.notifyDataSetChanged()
    }

    private fun loadOnlineURL() {
        onlineSongList.add(SongInfo("Pehli Baar", "Dhadak", "https://m.bestwap2.in/load/MP3_Songs/2018%20Bollywood/Dhadak%20Mp3%20Songs%20(2018)/Pehli%20Baar%20Mp3%20Song%20-%20Dhadak/Pehli%20Baar%20-%20Dhadak%2064%20Kbps.mp3"))
//        onlineSongList.add(SongInfo("001", "Ahmed", "https://server6.mp3quran.net/thubti/001.mp3"))
//        onlineSongList.add(SongInfo("003", "Alex", "https://server6.mp3quran.net/thubti/003.mp3"))
//        onlineSongList.add(SongInfo("004", "Ahmed", "https://server6.mp3quran.net/thubti/004.mp3"))
//        onlineSongList.add(SongInfo("005", "Alex", "https://server6.mp3quran.net/thubti/005.mp3"))
        onlineSongList.add(SongInfo("Mulaqaat", "Cheat India", "https://m.bestwap2.in/load/MP3_Songs/2019%20Bollywood/Cheat%20India%20Mp3%20Songs%20(2019)/Phir%20Mulaaqat%20Mp3%20Song%20-%20Cheat%20India/Phir%20Mulaaqat%20-%20Cheat%20India%20128%20Kbps.mp3"))
        onlineSongList.add(SongInfo("Dekhte dekhte", "Batti Gul Meter Chalu ", "https://m.bestwap2.in/load/MP3_Songs/2018%20Bollywood/Batti%20Gul%20Meter%20Chalu%20Mp3%20Songs%20(2018)/Dekhte%20Dekhte%20Rahat%20Version%20-%20Batti%20Gul%20Meter%20Chalu/Dekhte%20Dekhte%20Version%202%20-%20Batti%20Gul%20Meter%20Chalu%2064%20Kbps.mp3"))
        onlineSongList.add(SongInfo("Taiyari", "Cheat India", "https://m.bestwap2.in/load/MP3_Songs/2019%20Bollywood/Cheat%20India%20Mp3%20Songs%20(2019)/Taiyaari%20Mp3%20Song%20-%20Cheat%20India/Taiyaari%20-%20Cheat%20India%20128%20Kbps.mp3"))
        onlineSongList.add(SongInfo("Dil me ho tum", "Cheat India", "https://m.bestwap2.in/load/MP3_Songs/2019%20Bollywood/Cheat%20India%20Mp3%20Songs%20(2019)/Dil%20Mein%20Ho%20Tum%20Mp3%20Song%20-%20Cheat%20India/Dil%20Mein%20Ho%20Tum%20-%20128%20Kbps%20Cheat%20India.mp3"))
        onlineSongList.add(SongInfo("Title track", "Kalank", "https://m.bestwap2.in/load/MP3_Songs/2019%20Bollywood/Kalank%20Mp3%20Songs%20(2019)/Title%20Track%20Mp3%20Song%20-%20Kalank/Kalank%20-%20Title%20Song%20128%20Kbps.mp3"))
        onlineSongList.add(SongInfo("Tabaah ho gye", "Kalank", "https://m.bestwap2.in/load/MP3_Songs/2019%20Bollywood/Kalank%20Mp3%20Songs%20(2019)/Tabaah%20Ho%20Gaye%20Mp3%20Song%20-%20Kalank/Tabaah%20Ho%20Gaye%20-%20Kalank%20128%20Kbps.mp3"))
        onlineSongList.add(SongInfo("Apna Time aayega", "Gully boy", "https://m.bestwap2.in/load/MP3_Songs/2019%20Bollywood/Gully%20Boy%20Mp3%20Songs%20(2019)/Apna%20Time%20Aayega%20Mp3%20Song%20-%20Gully%20Boy/Apna%20Time%20Aayega%20-%20Gully%20Boy%20128%20Kbps.mp3"))
        onlineSongList.add(SongInfo("Azaadi", "Gully boy", "https://m.bestwap2.in/load/MP3_Songs/2019%20Bollywood/Gully%20Boy%20Mp3%20Songs%20(2019)/Azadi%20Mp3%20Song%20-%20Gully%20Boy/Azadi%20-%20Gully%20Boy%20128%20Kbps.mp3"))
        onlineSongList.add(SongInfo("Ashiq banaya aapne", "Hate Story4", "https://m.bestwap2.in/load/MP3_Songs/2018%20Bollywood/Hate%20Story%204%20Mp3/128%20Kbps%20Medium%20Quality/Aashiq%20Banaya%20Aapne%20-%20Hate%20Story%204%20128%20Kbps.mp3"))
        onlineSongList.add(SongInfo("Name hai mera", "Hate Story4", "https://m.bestwap2.in/load/MP3_Songs/2018%20Bollywood/Hate%20Story%204%20Mp3/128%20Kbps%20Medium%20Quality/Naam%20Hai%20Mera%20(Hate%20Story%20IV)%20128%20Kbps.mp3"))
    }

    inner class MySongAdapter : BaseAdapter {
        var myListSong = ArrayList<SongInfo>()

        constructor(myListSong: ArrayList<SongInfo>) : super() {
            this.myListSong = myListSong
        }


        @SuppressLint("ViewHolder")
        override fun getView(postion: Int, p1: View?, p2: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.song_ticket, null)
            val Song = this.myListSong[postion]
            myView.tvSongName.text = Song.Title
            myView.tvAuthor.text = Song.AuthorName

            myView.buPlay.setOnClickListener {
                //TODO: play song

                if (myView.buPlay.text == "Stop") {
                    mp!!.stop()
                    myView.buPlay.text = "Start"
                } else {
                    mp = MediaPlayer()
                    mp!!.isLooping
                    //mp!!.stop()
                   if( mp!!.isLooping){
                       mp!!.release()
//                        mp!!.stop()
                    }
                    try {
                        mp!!.setDataSource(Song.SongURL)
                        mp!!.prepare()
                        mp!!.start()
                        myView.buPlay.text = "Stop"
                        sbProgress.max = mp!!.duration
                        /*if(sbProgress.max == mp!!.duration){
                            myView.buPlay.text = "Start"
                        }*/
                    } catch (ex: Exception) {
                    }
                }
            }

            return myView

        }

        override fun getItem(item: Int): Any {
            return this.myListSong[item]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return this.myListSong.size
        }

    }

    inner class mySongTrack : Thread() {
        override fun run() {
            while (true) {
                try {
                    sleep(1000)
                } catch (ex: Exception) {
                }
                runOnUiThread {
                    if (mp != null) {
                        sbProgress.progress = mp!!.currentPosition
                    }
                }
            }
        }
    }

    fun CheckUserPermsions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_ASK_PERMISSIONS
                )
                return
            }
        }
        loadSong()

    }

    //get acces to location permsion
    private val REQUEST_CODE_ASK_PERMISSIONS = 123


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadSong()
            } else {
                // Permission Denied
                Toast.makeText(this, "denail", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun loadSong() {
        val allSongsURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val cursor = contentResolver.query(allSongsURI, null, selection, null, null)
        if (cursor != null) {
            if (cursor!!.moveToFirst()) {
                do {
                    val songURL =
                        cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val SongAuthor =
                        cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val SongName =
                        cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    offlineSongList.add(SongInfo(SongName, SongAuthor, songURL))
                } while (cursor!!.moveToNext())


            }
            cursor!!.close()

            adapter = MySongAdapter(offlineSongList)
            lsListSongs.adapter = adapter
            adapter!!.notifyDataSetChanged()
        }
    }

}
