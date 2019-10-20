package com.app.langta.niemphat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.PlaybackParams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    public Button btnPlay;
    public Button btnNext;
    public Button btnPrev;
    public Button btnspeed, btntimer;
    public TextView textTimeCurrent;
    public TextView textTimeDuran;
    public MediaPlayer mp;
    public ArrayList listSound;
    public Sound currentSound;
    public ListView listSoundView;
    private Handler myHandler = new Handler();
    private double startTime = 0;
    private double finalTime = 0;
    public SeekBar seekbar;
    public int index = 0;
    public float speed = 1;
    private AdView mAdView;
    private ImageView img_menu;
    RelativeLayout rl_menu;
    LinearLayout ln_animation;

    class Sound {
        public int sound;
        public String name;


        public Sound(int song1, String name) {
            this.sound = song1;
            this.name = name;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_menu = findViewById(R.id.img_menu);
        btnPlay = (Button) this.findViewById(R.id.btnPlay);
        btnNext = (Button) this.findViewById(R.id.btnNext);
        btnPrev = (Button) this.findViewById(R.id.btnPrev);
        btnspeed = (Button) this.findViewById(R.id.btnspeed);
        btntimer = findViewById(R.id.btntimer);
        textTimeDuran = (TextView) this.findViewById(R.id.textTimeDuran);
        textTimeCurrent = (TextView) this.findViewById(R.id.textTimeCurrent);
        listSoundView = (ListView) this.findViewById(R.id.listSoundView);
        seekbar = (SeekBar) this.findViewById(R.id.seekBar2);


        listSound = new ArrayList();
        listSound.add(new Sound(R.raw.niemphatduocsu, "NIỆM PHẬT DƯỢC SƯ"));
        listSound.add(new Sound(R.raw.song1, "QUAN ÂM BỒ TÁT"));
        listSound.add(new Sound(R.raw.song2, "A DI ĐÀ PHẬT"));


        ArrayList a = new ArrayList();
        for (int i = 0; i < listSound.size(); i++) {
            Sound tenp = (Sound) listSound.get(i);
            a.add(tenp.name);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, a);
        listSoundView.setAdapter(arrayAdapter);


        myHandler.postDelayed(UpdateSongTime, 100);


        currentSound = (Sound) listSound.get(index);
        mp = MediaPlayer.create(this, currentSound.sound);
//        mp.seekTo(290000);


        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.seekTo(13000);
                mp.start();
            }
        });


        Sound();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        rl_menu = findViewById(R.id.rl_menu);
        ln_animation = findViewById(R.id.ln_animation);
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_menu.setVisibility(View.VISIBLE);
                ln_animation.setVisibility(View.VISIBLE);
                ln_animation.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.left_to_right));
            }
        });
        View view_menu = findViewById(R.id.view_menu);
        view_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ln_animation.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.right_to_left));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rl_menu.setVisibility(View.GONE);
                        ln_animation.setVisibility(View.GONE);
                    }
                }, 300);
            }
        });

        TextView tv_tuthien = findViewById(R.id.tv_tuthien);

        tv_tuthien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ln_animation.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.right_to_left));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rl_menu.setVisibility(View.GONE);
                        show2("Chủ TK Vũ Thị Thu Hà\n" +
                                "Số TK: 018100358179\n" +
                                "Ngân hàng Vietcombank\n" +
                                "Chi nhánh Sài Gòn");
                    }
                }, 300);
            }
        });

        TextView tv_antong = findViewById(R.id.tv_antong);
        tv_antong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ln_animation.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.right_to_left));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rl_menu.setVisibility(View.GONE);
                        show2("Chủ TK Vũ Thị Thu Hà\n" +
                                "Số TK: 1459937\n" +
                                "Ngân hàng Á Châu ACB\n" +
                                "PGD Phước Bình\n" +
                                "Lưu ý: khi gửi online chọn ACB (HCM)");
                    }
                }, 300);
            }
        });

        TextView tv_khuyenhoc = findViewById(R.id.tv_khuyenhoc);
        tv_khuyenhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ln_animation.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.right_to_left));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rl_menu.setVisibility(View.GONE);
                        show2("Chủ TK Vũ Thị Thu Hà\n" +
                                "Số TK: 0071001281072\n" +
                                "Ngân hàng Vietcombank\n" +
                                "Chi nhánh Hồ Chí Minh");
                    }
                }, 300);
            }
        });

    }

    private void show2(String text){
        final Context context = this;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Thông Tin");

        // set dialog message
        alertDialogBuilder
                .setMessage(text)
                .setNegativeButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };

    public String sec2time(int sec) {
        int min = (int) TimeUnit.MILLISECONDS.toMinutes((long) sec);
        int se = (int) ((int) TimeUnit.MILLISECONDS.toSeconds((long) sec) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes((long) sec)
        ));
        return String.format("%d:%d", min, se);
    }


    public void updateUi() {
        if (mp.isPlaying()) {
            btnPlay.setBackgroundResource(R.drawable.pause);
        } else {
            btnPlay.setBackgroundResource(R.drawable.play);
        }

        this.textTimeDuran.setText(sec2time(mp.getDuration()));

        seekbar.setMax((int) mp.getDuration());

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mp.isPlaying()) {
                    mp.seekTo(seekBar.getProgress());
                }
            }
        });

    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            textTimeCurrent.setText(sec2time(mp.getCurrentPosition()));
            seekbar.setProgress((int) mp.getCurrentPosition());
            myHandler.postDelayed(this, 100);
        }
    };

    public void nextIndex(boolean play) {
        if (index < listSound.size() - 1) {
            index += 1;
        } else {
            index = 0;
        }

        loadMp(play);
    }

    public void prevIndex(boolean play) {
        if (index > 0) {
            index -= 1;
        } else {
            index = listSound.size() - 1;
        }
        loadMp(play);
    }

    public void loadMp(boolean play) {
        mp.release();
        currentSound = (Sound) listSound.get(index);
        Toast.makeText(getApplicationContext(), currentSound.name, Toast.LENGTH_LONG).show();
        mp = MediaPlayer.create(getApplicationContext(), currentSound.sound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.seekTo(13000);
                mp.start();
            }
        });
        mp.setPlaybackParams(new PlaybackParams().setSpeed(speed));
        if (play) {
            mp.start();
        }
        updateUi();

    }

    public void Sound() {
        this.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playsound(view);
            }
        });

        listSoundView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sound soundTemp = (Sound) listSound.get(i);
                index = i;
                if (mp.isPlaying()) {
                    mp.pause();
                    loadMp(true);
                } else {
                    loadMp(false);
                }
                Toast.makeText(getApplicationContext(), soundTemp.name, Toast.LENGTH_LONG).show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    nextIndex(true);
                } else {
                    nextIndex(false);
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    prevIndex(true);
                } else {
                    prevIndex(false);
                }
            }
        });

        btnspeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (speed < 3) {
                    speed += 0.25;
                } else {
                    speed = 1;
                }
                if (mp.isPlaying()) {
                    mp.setPlaybackParams(new PlaybackParams().setSpeed(speed));
                }
                btnspeed.setText(String.format("x%.2f", speed));
            }
        });

        btntimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Hẹn Giờ");
                alert.setMessage("Nhập giờ cần tắt(ví dụ: muốn tắt sau 1 giờ nhập 60)");
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            stopService(new Intent(MainActivity.this, BroadcastService.class));
                            registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
                            BroadcastService.timer = Integer.parseInt(input.getText().toString()) * 10000;
                            startService(new Intent(MainActivity.this, BroadcastService.class));
                        } catch (Exception illegalAccessError) {
                            Toast.makeText(MainActivity.this, "Nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });
    }

    public void playsound(View v) {
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.setPlaybackParams(new PlaybackParams().setSpeed(speed));
            mp.start();
        }

        updateUi();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, BroadcastService.class));
        Log.i("TAG", "Stopped service");
        super.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            btntimer.setText(millisUntilFinished / 1000 + " s");
            if (mp.isPlaying() && (millisUntilFinished / 1000) == 1) {
                mp.pause();
                btnPlay.setBackgroundResource(R.drawable.play);
                stopService(new Intent(this, BroadcastService.class));
                btntimer.setText("Hẹn giờ");
            }
        }
    }

}
