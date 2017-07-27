package com.example.user.chat2;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    String a = null;
    Socket s = null;
    DataInputStream in;
    DataOutputStream out;
   // Scanner scanner;


    Button bt1, bt2;
    TextView tv,tv2;
    EditText et, et2;
    Spinner name;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   // private GoogleApiClient client;
   /* Message msg;*/
    Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        et = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);
        name = (Spinner) findViewById(R.id.spinner);
        bt1 = (Button) findViewById(R.id.button);
        bt2 = (Button) findViewById(R.id.button2);

        ClientThread i = new ClientThread();
        i.start();
        Log.e(a, "1");
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    out.writeUTF(String.valueOf( et2.getText()+":"+et.getText()));
                    tv.setText(et.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            out.writeUTF(String.valueOf(et2.getText()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();

            }
        });
        //scanner = new Scanner(String.valueOf(et.getText()));


       /* while (true) {
           try {
              out.writeUTF(String.valueOf(et2.getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        Bundle bundle=msg.getData();
                        String showtext=bundle.getString("showtext","錯誤");
                        tv.setText(tv.getText()+"\n"+showtext);
                        break;
                }

            }



        };

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
  /*  public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }*/

    class ClientThread extends Thread {


        public void run() {
            String ip = "192.168.43.180";
            try {

                s = new Socket(ip, 2011);
                in = new DataInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());


            } catch (IOException e) {
                System.out.println("無法連接 ");
                System.out.println(e.getStackTrace());
                System.exit(0);
            }
            super.run();
            while (true) {


                try {
                    read();
                    //tv.setText(in.readUTF());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public String read() throws IOException {
        String abc = in.readUTF();
        // System.out.println(str);
        Log.e("str",abc);
        Message message=new Message();
        message.what=(0);
        Bundle bundle=new Bundle();
        bundle.putString("showtext",abc);
        message.setData(bundle);
        mHandler.sendMessage(message);
        return abc;
    }
   /*public  write(String temp) throws IOException{
       .wri outteUTF(temp);
        a=temp;
		/*switch(a){
		case"person":
			Server D=new Server();
			System.out.println(D.list.get(10));
		}
}*/

}
