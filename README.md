MASON Examples
==============

This project contains a few examples for the [MASON][mason] platform.

MASON Stands for Multi-Agent Simulator Of Neighborhoods... or Networks... or something... or Neighborhoods and Networks... but that would be NaN! Oh well...

Running the examples
--------------------

Simply copy&paste the cloned sources to your MASON project. See how to set up a MASON project on [NetBeans][netBeansMason] or [Eclipse][eclipseMason].

mason-jung-example
------------------

This is a demo model introducing the usage of [JUNG][jung] (Java Universal Network/Graph Framework) with MASON. There is an official [contributed example][jungExtension] but I found it a bit overcomplicated.

MASON offers several ways to work with networks, like the built-in mechanism (e.g., NetworkPortrayal2D) or the SocialNets extension. Unfortunately, both requires a 2D world to operate with, so one have to implement the layouting strategy for instance. MASON alos supporst using external libraries, like JUNG to support using networks.

  [mason]: http://cs.gmu.edu/~eclab/projects/mason/
  [netBeansMason]: http://cs.gmu.edu/~eclab/projects/mason/extensions/netbeans/
  [eclipseMason]: http://cs.gmu.edu/~eclab/projects/mason/extensions/eclipse/
  [jungExtension]: http://cs.gmu.edu/~eclab/projects/mason/extensions/jung/
  [jung]: http://jung.sourceforge.net/

