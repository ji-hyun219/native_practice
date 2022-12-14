import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  // 먼저 채널을 생성합니다
  // 배터리 레벨을 반환하는 플랫폼 메서드를 가지고 있는 MethodChannel 을 사용할 것입니다.

  // 채널의 클라이언트와 호스트는 채널 생성자를 통해 전달된 채널 이름으로 연결됩니다.
  // 하나의 앱에서 사용하는 모든 채널 이름은 유일해야 하기 때문에, 유일한 도메인 접두사를 사용해서 채널 이름 앞에 추가하세요.
  // 예) samples.flutter.io/battery

  static const platform = MethodChannel('samples.flutter.dev/battery');

  // 배터리 레벨을 가져옵니다
  String _batteryLevel = 'Unknown battery level.';

  Future<void> _getBatteryLevel() async {
    String batteryLevel;
    try {
      final int result = await platform.invokeMethod('getBatteryLevel');
      batteryLevel = 'Battery level at $result % .';
    } on PlatformException catch (e) {
      batteryLevel = "Failed to get battery level: '${e.message}'.";
    }

    setState(() {
      _batteryLevel = batteryLevel;
    });
  }

  // 다음으로 getBatteryLevel 구분자를 통해 특정 메서드를 지정해서 메서드 채널에서 메서드를 실행합니다.
  // 호출은 실패할 수도 있습니다.
  // invokeMethod 호출을 try-catch 문으로 감싸주세요

  @override
  Widget build(BuildContext context) {
    return Material(
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            ElevatedButton(
              onPressed: _getBatteryLevel,
              child: Text('Get Battery Level'),
            ),
            Text(_batteryLevel),
          ],
        ),
      ),
    );
  }
}
