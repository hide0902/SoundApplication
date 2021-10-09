package com.example.soundapplication

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.soundapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var soundPool: SoundPool
    private lateinit var player: MediaPlayer
    private var soundID = 0
    private var button2on = false
    inner class CountDo(mill: Long, countD: Long): CountDownTimer(mill, countD) {
        var run = false
        override fun onTick(millisUntilFinished: Long) {
            val min = millisUntilFinished / 1000L / 60L
            val sec = millisUntilFinished / 1000L % 60L
            binding.textView.text = "%1d:%2$02d".format(min, sec)
        }

        override fun onFinish() {
            binding.textView.text = "0:00"
            binding.textView2.text = "時間です。"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = "0:10"
        var tim = CountDo(10 * 1000, 100)
        binding.button1.setOnClickListener {
            tim.run = if (tim.run) {
                tim.cancel()
                binding.button1.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                binding.textView2.text = "中断しました。"
                false
            } else {
                tim.start()
                binding.button1.setImageResource(R.drawable.ic_baseline_stop_24)
                binding.textView2.text = "開始しました。"
                true
            }
        }

        binding.button2.setOnClickListener {
            button2on = if (button2on) {
//                soundPool.stop(soundID)
                player.pause()
                binding.button2.setImageResource(R.drawable.ic_baseline_star_24)
                false
            } else {
//                soundPool.play(soundID, 1.0f, 100f, 0, 0, 1.0f)
                player.start()
                binding.button2.setImageResource(R.drawable.ic_baseline_stop_circle_24)
                true
            }
        }
    }

    override fun onResume() {
        super.onResume()

        soundPool = SoundPool.Builder().run {
            val audioAttributes = AudioAttributes.Builder().run {
                setUsage(AudioAttributes.USAGE_MEDIA)
                build()
            }
            setMaxStreams(1)
            setAudioAttributes(audioAttributes)
            build()
        }
        soundID = soundPool.load(this, R.raw.getdown, 1)
        player = MediaPlayer.create(this, R.raw.getdown)

    }

    override fun onPause() {
        super.onPause()
        soundPool.release()
        player.pause()
    }
}
