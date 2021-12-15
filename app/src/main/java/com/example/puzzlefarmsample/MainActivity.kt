package com.example.puzzlefarmsample

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

//    lateinit var db: AppDatabase
    var disposable: Disposable? = null
    lateinit var sfx_player: MediaPlayer
    lateinit var music_player: MediaPlayer
//    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        sfx_player = Sfx.getSfxPlayer(this)!!
//        music_player = Music.getMusicPlayer(this)!!
//        db = AppDatabase.getAppDatabase(this)!!
        val name = intent.extras?.get("username")
//        refreshUser(name.toString())
    }

    override fun onRestart() {
        super.onRestart()
//        refreshUser(user?.username!!)
    }

    fun onClick(v: View) {
//        if (Settings.getValue("sounds").equals("on"))
//        {
//            sfx_player.start()
//        }

        val intent : Intent
        when(v) {
            findViewById<TextView>(R.id.lblChoosePuzzle) -> {
                intent = Intent(this, ChoosePuzzleActivity::class.java)
                startActivity(intent)

            }
            findViewById<TextView>(R.id.lblExit) -> {
                moveTaskToBack(true);
                exitProcess(0)
            }
        }
    }

//    fun refreshUser(username: String) {
//        disposable = db.getUserDao().getByUsername(username)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if (it != null)
//                {
//                    user = it }
//            }, {})
//    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}