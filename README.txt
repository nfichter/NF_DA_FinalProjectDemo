==========INSTRUCTIONS==========
=====Scaling=====
In your terminal, type:
javac Driver.java
java Driver imageToBeScaled.extension s pixelsInXDirection pixelsInYDirection outputName outputExtension
example:
java Driver test.jpg s 200 200 testScale jpg
will scale the image test.jpg to 200 wide by 200 tall and place it in a new image called testScale.jpg, leaving the original test.jpg untouched
=====Borders=====
In your terminal, type:
javac Driver.java
java Driver imageToBeBordered.extension b Border.extension outputName outputExtension
example:
java Driver test.jpg b border.jpg testBorder jpg
will add the border from border.jpg to test.jpg and place it in a new image called testBorder.jpg, leaving the original test.jpg untouched
=====Moods=====
In your terminal, type:
javac Driver.java
java Driver imageToApplyMoodTo.extension m mood outputName outputExtension
possible replacements for mood:
-happy (red)
-sad (blue)
-mad (green)
example:
java Driver test.jpg m happy testMood jpg
will make the image "happier" by making it more red overall

==========FEATURES==========
* Automatically scale images if needed
* Manually scale images
* Apply black borders
* Add different moods to images

==========TO BE ADDED==========
* Change the color of the border
* Choose from predetermined borders instead of downloading from the internet
* Apply transparency
* Make images black and white
* Invert colors