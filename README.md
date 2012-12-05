WirelessRedstoneLogSim
======================

Wireless Redstone logfile simulator



Setting up with Eclipse:
========================

1. Create new Java project.
2. Check out this git repos to your project's root directory.
3.1. Right click project -> properties.
3.2. Go to "Java Build Path"; Source tab.
3.3. "Add Folder...", check the WirelessRedstoneLogSim directory, ok.
4.1. Download Slick2D from - http://slick.cokeandcode.com/ - "Download Full Distribution"
4.2. Unpack the slick.zip to %programfiles%/Java, or where ever you want it.
5.1. Download LWJGL from - http://www.lwjgl.org/download.php
5.2. Unpack the lwjgl-x.x.x.zip to %programfiles%/Java, or where ever you want it.
5.3. Copy the "native" directory to your project's root directory.
6.1. Right click project -> properties.
6.2. Go to "Java Build Path"; Libraries tab.
6.3. "Add External JARs...", browse to your Slick directory, pick "slick.jar" from the lib directory.
6.4. "Add External JARs...", browse to your LWJGL directory, pick "lwjgl.jar" from the jar directory.