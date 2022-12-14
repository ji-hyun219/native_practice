# native_practice

네이티브 개념 공부 및 연습 레포지토리

## AOS - BatteryManager, iOS - device.batteryLevel (getBatteryLevel)

### 1단계 플랫폼 채널

채널 메서드를 호출할 때, `메서드를 플랫폼의 메인 스레드에서 호출해야 합니다...!!!!!!`
(AOS 경우, MainActivity.kt 위쪽을 말하는 듯. 그니까 setMethodCallHandler)
클라이언트 단에서는 MethodChannel 이 메시지를 그에 상응하는 메서드로 보낼 수 있도록 해줍니다. (invokeMethod)
플랫폼 단에서는 `Android` 는 `MethodChannel(MainActivity)`, `iOS` 는 `FlutterMethodChannel` 들이 메시지를 받는 것과 응답을 가능하게 합니다.
이 클래스들은 아주 적은 코드만으로도 플랫폼 플러그인을 개발할 수 있게 해줍니다.

### 2단계 Flutter 플랫폼 클라이언트 생성

앱의 State 클래스가 현재 앱 상태를 저장합니다. 현재 배터리 상태를 저장하려면 이를 상속해야 합니다.
먼저 채널을 생성합니다. 배터리 레벨을 반환하는 `플랫폼 메서드를 가지고 있는` MethodChannel 을 사용할 것입니다.
그러면 MainActivity 파일의 메서드를 불러올 수 있어야 하므로 MethodChannel 이 동일해야 할 것 같다.

### 3단계 Kotlin 을 사용해서 Android 플랫폼 구현 추가

MainActivty.kt 파일 수정해주세요.  
onCreate 메서드 안에서 MethodChannel 을 만들고 setMethodCallHandler 를 호출하세요.  
`Flutter 클라이언트 쪽에서 사용한 것과 같은 채널 이름`을 사용했는지 확인해주세요. (2단계에서 예측했듯 동일해야 함)

### Kotlin 인수 전달 방법

```dart
_channel.invokeMethod('showToast', {'text': 'hello world'});
```

```kotlin
override fun onMethodCall(call: MethodCall, result: Result) {
  when (call.method) {
    "showToast" -> {
      val text = call.argument<String>("text") // hello world
      showToast(text)
    }
  }
}
```
