package com.example.mostafa.ronixtechtask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTMessageActivity extends AppCompatActivity {

    String MQTTHOST = "tcp://m13.cloudmqtt.com:11853";

    String USERNAME = "qumrwmme";

    String PASSWORD = "oJHjXS7Xi0F9";

    String topicStr = "ronix/network";

    MqttAndroidClient client;

    MqttConnectOptions options;

    TextView subtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        subtext = findViewById(R.id.receivedMessage);

        String clientId = MqttClient.generateClientId();

        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);

        options = new MqttConnectOptions();

        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        //options.setCleanSession(true);
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MQTTMessageActivity.this, "Connected", Toast.LENGTH_LONG).show();
                    setSubscribtion();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MQTTMessageActivity.this, "Connected Failed", Toast.LENGTH_LONG).show();
                }
            });

        } catch (MqttException e) {

            e.printStackTrace();

        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                subtext.setText(new String(message.getPayload()));

                Log.d("message", String.valueOf(message));


            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    private void setSubscribtion() {

        try {

            client.subscribe(topicStr,0);

        } catch (MqttException e) {

            e.printStackTrace();

        }

    }

}
