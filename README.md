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

### Kotlin

https://developer.android.com/kotlin/common-patterns?hl=ko

#### 상속

```kotlin
class LoginFragment : Fragment()
```

: 를 사용하여 상속을 표시한다.  
이 클래스 선언에서 LoginFragment는 슈퍼클래스 Fragment의 생성자 호출을 담당힌다.

#### null 허용 여부 및 초기화

Kotlin에서는 객체를 선언할 때 객체의 속성을 초기화해야 합니다. 그러면 클래스의 인스턴스를 가져올 때 액세스 가능한 속성을 즉시 참조할 수 있습니다  
그러나 Fragment의 View 객체는 Fragment#onCreateView를 호출하기 전까지는 확장 준비가 되지 않으므로 View의 속성 초기화를 연기할 방법이 필요합니다.

lateinit으로 속성 초기화를 연기할 수 있습니다. lateinit을 사용할 때는 최대한 빨리 속성을 초기화해야 합니다.

```kotlin
class LoginFragment : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var statusTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameEditText = view.findViewById(R.id.username_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        loginButton = view.findViewById(R.id.login_button)
        statusTextView = view.findViewById(R.id.status_text_view)
    }

    ...
}
```
