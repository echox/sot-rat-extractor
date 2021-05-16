# SoT-RAT-Extractor

The webpage of the game Sea of Thieves exposes a nice REST-API for gathering stats about your profile, for example collected gold or sailed miles.
Interaction with it is only possible by using a remote access token (RAT).
The RAT is a JWT token obtained after a login to Microsoft Live during an OAuth code flow. It is stored in your browser as a cookie and should be valid for 14 days.

If you want to collect the stats in a local influx db you need that token.
Exporting cookies in your browser every 14 days is no fun and the dependency on javascript during the login process requires a "real" browser.

This extractor can be run as a docker container and output the RAT.
It uses selenium to interact with a headless firefox browser to obtain the token.

## WARNING

Supplying your Microsoft Live Account credentials to a docker container or application you didn't build yourself and fully understand the source code is dangerous.
Even if you do, credentials shouldn't be kept in cleartext as application arguments.
The point of using OAuth tokens is, you don't have to use credentials in web scraping scripts. Unfortunately Rare doesn't provide any functionality to obtain one.

Use this as an example how you could extract the Sea of Thieves RAT for yourself.
If you still want to use this code, at least build it yourself.

Binary builds are for the authors own use and shouldn't be trusted (https://hub.docker.com/r/echox/sot-rat-extractor)

The RAT gives full access to your account on seaofthieves.com until it expires.

## Content

Dockerfile based on Alpine Linux with OpenJDK, Firefox and the Gecko Webdriver.
Java executable with Selenium to interact with the webdriver.

## Build

```
$ mvn install
$ docker build . -t "sot-rat-extractor"
```

## Running

```
docker run --rm -e LOGIN=example@account -e PASSWORD="yourPassword" sot-rat-extractor
```

Example Output:

```
*** You are running in headless mode.
Crash Annotation GraphicsCriticalError: |[0][GFX1-]: glxtest: libpci missing (t=0.313902) [GFX1-]: glxtest: libpci missing
Crash Annotation GraphicsCriticalError: |[0][GFX1-]: glxtest: libpci missing (t=0.313902) |[1][GFX1-]: glxtest: libEGL missing (t=0.313942) [GFX1-]: glxtest: libEGL missing
Crash Annotation GraphicsCriticalError: |[0][GFX1-]: glxtest: libpci missing (t=0.313902) |[1][GFX1-]: glxtest: libEGL missing (t=0.313942) |[2][GFX1-]: glxtest: libGL.so.1 missing (t=0.313955) [GFX1-]: glxtest: libGL.so.1 missing
Crash Annotation GraphicsCriticalError: |[0][GFX1-]: glxtest: libpci missing (t=0.313902) |[1][GFX1-]: glxtest: libEGL missing (t=0.313942) |[2][GFX1-]: glxtest: libGL.so.1 missing (t=0.313955) |[3][GFX1-]: glxtest: libEGL missing (t=0.313971) [GFX1-]: glxtest: libEGL missing
Crash Annotation GraphicsCriticalError: |[0][GFX1-]: glxtest: libpci missing (t=0.313902) |[1][GFX1-]: glxtest: libEGL missing (t=0.313942) |[2][GFX1-]: glxtest: libGL.so.1 missing (t=0.313955) |[3][GFX1-]: glxtest: libEGL missing (t=0.313971) |[4][GFX1-]: No GPUs detected via PCI (t=0.313988) [GFX1-]: No GPUs detected via PCI
Sandbox: seccomp sandbox violation: pid 83, tid 83, syscall 16, args 2 21523 140734890437192 0 1 0.

(/usr/lib/firefox/firefox:83): GLib-GObject-CRITICAL **: 20:42:52.302: g_object_set: assertion 'G_IS_OBJECT (object)' failed
Sandbox: seccomp sandbox violation: pid 107, tid 107, syscall 16, args 2 21523 140733039172520 0 1 0.

(/usr/lib/firefox/firefox:107): GLib-GObject-CRITICAL **: 20:42:52.400: g_object_set: assertion 'G_IS_OBJECT (object)' failed
console.warn: SearchSettings: "get: No settings file exists, new profile?" (new Error("", "(unknown module)"))
May 16, 2021 8:42:53 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
Sandbox: seccomp sandbox violation: pid 173, tid 173, syscall 16, args 2 21523 140723375851432 0 1 0.

(/usr/lib/firefox/firefox:173): GLib-GObject-CRITICAL **: 20:42:54.458: g_object_set: assertion 'G_IS_OBJECT (object)' failed
visiting https://www.seaofthieves.com/login...
(this should redirect us to Microsoft Live Login)
-> Sign in to your Microsoft account
entering credentials...
login finished, should redirect to SoT...
-> Sea of Thieves - Welcome to Sea of Thieves on Xbox One, Windows 10 and Steam
searching for RAT...
closing driver...
driver closed
your RAT expiry date: Sun May 30 20:42:58 GMT 2021
your RAT: RAT_COOOKIE_STRING_REPLACED
```