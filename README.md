WirelessRedstoneLogSim
======================

Wireless Redstone logfile simulator



Setting up with Eclipse
=======================

1. Create new Java project.
2. Check out this git repos to your project's root directory.
3. Right click project -> properties.
4. Go to "Java Build Path"; Source tab.
5. "Add Folder...", check the WirelessRedstoneLogSim directory, ok.
6. Download Slick2D from - http://slick.cokeandcode.com/ - "Download Full Distribution"
7. Unpack the slick.zip to %programfiles%/Java, or where ever you want it.
8. Download LWJGL from - http://www.lwjgl.org/download.php
9. Unpack the lwjgl-x.x.x.zip to %programfiles%/Java, or where ever you want it.
10. Copy the "native" directory to your project's root directory.
11. Right click project -> properties.
12. Go to "Java Build Path"; Libraries tab.
13. "Add External JARs...", browse to your Slick directory, pick "slick.jar" from the lib directory.
14. "Add External JARs...", browse to your LWJGL directory, pick "lwjgl.jar" from the jar directory.



Key Bindings
============

ENTER: Go to next timestep. Resets if at end

A: Toggle automatic timestepping.

O: Cycle orientation. XZ, XY, YZ

1,2,3,4,5,6,7,8,9,0: Set the automatic timestepping timeout. 1=100ms ... 0=1000ms=1s