# cVendServiceSample

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cVendServiceSample 提供了 Ethernet Tethering、BCR GPIO Pin、OneWire External 等功能的测试 demo，并提供了对应 api 的具体使用方法。

## cVendService

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*cVendService 是一个用于控制 Ethernet Tethering、BCR GPIO Pin、OneWire External 于一体的 service，对外暴露了广播接口，其收到对应的 action 就会去处理相应的动作，并且返回结果。*

#### EthernetTethering

- *接口说明*

```java
// action on ethernet tethering
private static final String ACTION_ETHTETHERING_ON = "com.advantech.aim75.ETHTETHERING_ON";

// action off ethernet tethering
private static final String ACTION_ETHTETHERING_OFF = "com.advantech.aim75.ETHTETHERING_OFF";

// action retutn ethernet tethering status
private static final String ACTION_ETHTETHERING_STATUS = "com.advantech.aim75.ETHTETHERING_STATUS";

// action retutn ethernet tethering status key
private static final String IS_ETHTETHERING_ON = "is_ethtethering_on";
```

- *使用说明*

```java
// 使用 sendBroadcast 发送广播，进而打开、关闭 Ethernet Tethering

// on
sendBroadcast("com.advantech.aim75.ETHTETHERING_ON");

// off
sendBroadcast("com.advantech.aim75.ETHTETHERING_OFF");
```

- *返回值说明*

```java
// 要获取返回值，需要注册广播

IntentFilter filter = new IntentFilter();
filter.addAction("com.advantech.aim75.ETHTETHERING_STATUS");
registerReceiver(broadcastReceiver, filter);

// 创建广播接收器
private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("com.advantech.aim75.ETHTETHERING_STATUS".equals(action)) {
            // return status
            boolean status = intent.getBooleanExtra("is_ethtethering_on", false);
            Log.d(TAG, "status : " + status);
        }
    }
};
```

#### BCRGPIOPin

- *接口说明*

```java
// deactive
private static final String ACTION_BCRPIN_HIGH = "com.advantech.aim75.BCRPIN_HIGH";

// active
private static final String ACTION_BCRPIN_LOW = "com.advantech.aim75.BCRPIN_LOW";
```

- *使用说明*

```java
// // sendBroadcast deactive action
sendBroadcast("com.advantech.aim75.BCRPIN_HIGH");

// sendBroadcast active action
sendBroadcast("com.advantech.aim75.BCRPIN_LOW");
```

- *返回值说明*

None

#### OnewireExternal

