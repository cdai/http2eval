#!/bin/bash

keytool -genkeypair -alias mycert -keyalg RSA -sigalg MD5withRSA -keystore server/src/main/resources/key.jks -storepass secret -keypass secret -validity 9999

