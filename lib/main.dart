import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() => runApp(MaterialApp(
      home: MaterialApp(
        home: MyHomePage(),
      ),
    ));

class MyHomePage extends StatefulWidget {
  MyHomePage();

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Esys Share Plugin Sample'),
        ),
        body: Container(
            padding: const EdgeInsets.all(20.0),
            child: ListView(
              children: <Widget>[
                MaterialButton(
                  child: Text('Share text'),
                  onPressed: () async => await _shareText(),
                ),
                MaterialButton(
                  child: Text('Share image'),
                  onPressed: () async => await _shareImage(),
                ),
                MaterialButton(
                  child: Text('Get weather report'),
                  onPressed: () async => await _getWeather(),
                ),
              ],
            )));
  }

  Future _shareText() async {
    try {
      await EsysFlutterShare.shareText(
          'This is my text to share with other applications.', 'my text title');
    } catch (e) {
      print('error: $e');
    }
  }

  Future _shareImage() async {
    try {
      final ByteData bytes = await rootBundle.load('assets/graphic.png');
      await EsysFlutterShare.shareImage(
          'myImageTest.png', bytes, 'my image title');
    } catch (e) {
      print('error: $e');
    }
  }

  Future _getWeather() async {
    try {
      http
          .get(
              'http://api.wunderground.com/api/90185e79ff6e655a/conditions/q/CA/84604.json')
          .then((resp) {
        print(jsonDecode(resp.body)['current_observation']['weather']);
      });
    } catch (e) {
      print('error: $e');
    }
  }
}
