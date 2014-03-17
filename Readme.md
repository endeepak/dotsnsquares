![Logo](./res/drawable-hdpi/ic_launcher.png "Dots & Squares") [![Android app on Google Play](https://developer.android.com/images/brand/en_app_rgb_wo_60.png "Android app on Google Play")](https://play.google.com/store/apps/details?id=com.endeepak.dotsnsquares)

Dots & Squares
==============
A simple fun game with two simple rules

* Two players take turn to connect the dots.
* Complete a square to score a point and play again.

Map for dots, lines and squares position
----------------------------------------
Example 2x2 board
<pre>
(0,0)--l0--(0,1)--l1--(0,2)
  |          |          |
 l2    S0   l3    S1   l4
  |          |          |
(1,0)--l5--(1,1)--l6--(1,2)
  |          |          |
 l5    S2   l6    S3   l7
  |          |          |
(2,0)--l5--(2,1)--l6--(2,2)

(x,y) -> Dot position
ln    -> Line n
Sn    -> Square n
</pre>

Thanks to
---------
* @attenzione for https://github.com/attenzione/android-ColorPickerPreference
* Ashwini, Kiran & Sunita for testing and providing feedback on alpha versions of the app