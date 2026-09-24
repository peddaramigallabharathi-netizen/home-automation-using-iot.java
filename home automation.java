#include <WiFi.h>
#include <WebServer.h>

const char* ssid = "YOUR_WIFI_NAME";
const char* password = "YOUR_WIFI_PASSWORD";

WebServer server(80);

const int LIGHT1 = 25;
const int LIGHT2 = 26;
const int FAN = 27;
const int SOCKET = 14;

void setDevice(int pin, bool state) {
  digitalWrite(pin, state ? LOW : HIGH);
}

void setup() {
  Serial.begin(115200);

  pinMode(LIGHT1, OUTPUT);
  pinMode(LIGHT2, OUTPUT);
  pinMode(FAN, OUTPUT);
  pinMode(SOCKET, OUTPUT);

  setDevice(LIGHT1, false);
  setDevice(LIGHT2, false);
  setDevice(FAN, false);
  setDevice(SOCKET, false);

  WiFi.begin(ssid, password);

  Serial.print("Connecting to WiFi");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println();
  Serial.println("Connected!");
  Serial.print("ESP32 IP Address: ");
  Serial.println(WiFi.localIP());

  server.on("/light1/on", []() {
    setDevice(LIGHT1, true);
    server.send(200, "text/plain", "Light 1 ON");
  });

  server.on("/light1/off", []() {
    setDevice(LIGHT1, false);
    server.send(200, "text/plain", "Light 1 OFF");
  });

  server.on("/light2/on", []() {
    setDevice(LIGHT2, true);
    server.send(200, "text/plain", "Light 2 ON");
  });

  server.on("/light2/off", []() {
    setDevice(LIGHT2, false);
    server.send(200, "text/plain", "Light 2 OFF");
  });

  server.on("/fan/on", []() {
    setDevice(FAN, true);
    server.send(200, "text/plain", "Fan ON");
  });

  server.on("/fan/off", []() {
    setDevice(FAN, false);
    server.send(200, "text/plain", "Fan OFF");
  });

  server.on("/socket/on", []() {
    setDevice(SOCKET, true);
    server.send(200, "text/plain", "Socket ON");
  });

  server.on("/socket/off", []() {
    setDevice(SOCKET, false);
    server.send(200, "text/plain", "Socket OFF");
  });

  server.begin();
}

void loop() {
  server.handleClient();
}
