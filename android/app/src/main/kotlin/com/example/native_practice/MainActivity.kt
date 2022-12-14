package com.example.native_practice
// 다음으로 배터리 레벨을 가져오기 위해 안드로이드 배터리 API 를 사용하는 안드로이드 Kotlin 코드를 추가합니다.
// 해당 코드는 네이티브 안드로이드 앱에서 사용하던 것과 완전히 같습니다

// 첫번째로 필요한 import 들을 파일 상단에 추가해주세요
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import io.flutter.embedding.android.FlutterActivity

class MainActivity: FlutterActivity() {
  private val CHANNEL = "samples.flutter.dev/battery"

  override fun onCreate(savedInstanceState: Bundle?) {
    // onCreate 메서드 안에서 MethodChannel 을 만들고 setMethodCallHandler 를 호출하세요
    // Flutter 클라이언트 쪽에서 사용한 것과 같은 채널 이름을 사용했는지 확인해주세요.
    super.onCreate(savedInstanceState)

    GeneratedPluginRegistrant.registerWith(this)
    MethodChannel(flutterView, CHANNEL).setMethodCallHandler { call, result -> //
      // Note: this method is invoked on the main thread.
      if (call.method == "getBatteryLevel") {
        val batteryLevel = getBatteryLevel()

        if (batteryLevel != -1) {
          result.success(batteryLevel)
        } else {
          result.error("UNAVAILABLE", "Battery level not available.", null)
        }
      } else {
        result.notImplemented()
      }
    }
  }


  // 아래 코드를 액티비티 클래스의 onCreate() 메서드 아래에 새로운 메서드로 작성해주세요.
  private int getBatteryLevel() {
  int batteryLevel = -1;
  if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
    BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
    batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
  } else {
    Intent intent = new ContextWrapper(getApplicationContext()).
        registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    batteryLevel = (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100) /
        intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
  }

  return batteryLevel;
}

 // 마지막으로 먼저 추가한 onMethodCall() 메서드를 완성합니다.
 // 사용할 플랫폼 메서드는 getBatteryLevel() 이며, 이는 call 인자 안에서 가져와 테스트해볼 수 있습니다.
 // 이 플랫폼 메서드는 단순히 이전 단계에서 작성한 Android 코드를 호출합니다.
 // 그리고 response 매개변수를 통해 성고오가 에러를 응답으로 돌려줍니다
 // 이 플랫폼 메서드는 전 단계에서 작성한 Android 코드를 호출하며, resposne 인자를 통해 성공과 에러를 응답으로 돌려줍니다.
 // 만약 알 수 없는 메서드가 호출된다면, 예외처리가 필요합니다.



}
