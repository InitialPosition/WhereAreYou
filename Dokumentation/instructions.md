# WhereAreYou
<p align="center">
  <img src="http://lh3.googleusercontent.com/gN6iBKP1b2GTXZZoCxhyXiYIAh8QJ_8xzlhEK6csyDadA4GdkEdIEy9Bc8s5jozt1g=w300" width=150 height="auto"><br>
  A social media app for taking pictures and adding visual metadata to them</p>
<hr>

## App overview
This app allows the user to take an image on their phone. The app then collects some data based on the user's location and allows the user to add the data to the image taken. The final image can then be saved to the phone and/or shared via social media.

## Installation
1. Clone the repository using<br>
`git clone https://github.com/Syrapt0r/WhereAreYou`
1. Open the app in Android Studio
### OR
1. Download the apk for smartphones from<br>
https://drive.google.com/open?id=1J3ejbJY6mgNU0b2QWe1qVdoSfSXnV3Gi
1. Install the app on your android phone

## How To Use
The app has a simple premise. Take a picture and decide which information should be added to the picture.
1. In the start menu, select "Take Picture".
1. Take a photo with your camera.
1. Open the template drawer by swiping from the left border of the screen to the center of the display.
1. Choose one of the templates and select it.
1. Save your image by clicking on the check mark in the upper right corner of the menu bar.<br>

### Extra
- Use the gallery to look at the pictures you already took. To do this, select "Gallery" in the main menu.
- Share your pictures by selecting them in the gallery and then pressing the share icon in the top right corner of the menu bar.
- View your achievements by touching the trophy in the top right corner of the main menu.
- Learn all about the developers and contributors in the credits. To do so, click on Credits in the lower left corner.

## The app workflow in a few sentences:
1. First we create a Camera-Intent, then we let the user take a photo and save the location of it.
1. We then display different layout elements above the image. To save the image, the file path of the image is sent to the gallery and the layout elements are saved in a class.
1. To share it later with other people and platforms, we take a screenshot of the image in the gallery and send it to other systems.

<p align="center">App developed by Jonathan M. and Felix K.</p>
