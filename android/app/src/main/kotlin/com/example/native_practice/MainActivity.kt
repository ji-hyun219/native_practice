package com.example.native_practice

import io.flutter.embedding.android.FlutterActivity

class MainActivity: FlutterActivity() {
  private val CHANNEL = "samples.flutter.dev/battery"

  override fun onCreate(savedInstanceState: Bundle?) {
    // onCreate 메서드 안에서 MethodChannel 을 만들고 setMethodCallHandler 를 호출하세요
    // Flutter 클라이언트 쪽에서 사용한 것과 같은 채널 이름을 사용했는지 확인해주세요.
    super.onCreate(savedInstanceState)

    GeneratedPluginRegistrant.registerWith(this)
    MethodChannel(flutterView, CHANNEL).setMethodCallHandler { call, result ->
      // Note: this method is invoked on the main thread.
      // TODO
    }

  }
}
